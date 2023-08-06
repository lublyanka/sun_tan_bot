package org.santan.services;

import static org.santan.entities.Messages.*;
import static org.santan.entities.Position.FRONT;
import static org.santan.services.LevelService.MAX_LEVEL;
import static org.santan.services.LevelService.MIN_LEVEL;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.santan.entities.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log
public class TimerService {

  private final LevelService levelService;
  private final SessionService sessionService;
  private final UserService userService;
  private final MessageService messageService;

  private static String getFormatStringWithLocale(String language) {
    return switch (language == null ? "en" : language) {
      case "es" -> "Pone al lado %s por %3.1f minuto(s) (Nivel %d)";
      case "ru" -> "Лягте на %s сторону на %3.1fм (Уровень %d)";
      default -> "Get to the %s side for %3.1f minute(s) (level %d)";
    };
  }

  public void onStart() {
    log.info("TimerService onStart. Restart all active sessions");
    List<Session> activeSessions = sessionService.getAllActiveSessions();
    for (Session session : activeSessions) {
      messageService.sendMessage(session.getChatId(), getSomeErrorMessageWIthLocale(session));
      createTimerAndSaveSession(session);
    }
  }

  public void pauseTimer(String chatId, Long telegramUserId, String language) {
    log.info("Pausing timer for chatId: " + chatId);
    log.info("Language: " + language);
    Optional<User> userOptional = userService.getUserById(telegramUserId);
    String returnMessageText = getSessionIsNotActiveMessageWithLocale(language);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      Session session = sessionService.getSessionByUser(user);
      if (session != null && session.isActive()) {
        returnMessageText = getTimerPausedMessageWithLocale(language);
        user.setCurrentLevel(session.getCurrentLevel());
        user.setCurrentPosition(session.getCurrentPosition());
        userService.saveUser(user);
        deactivateAndSaveSession(session);
      }
    }
    messageService.sendMessage(chatId, returnMessageText);
  }

  public void startTimer(String chatId, Long telegramUserId, String language) {
    log.info("Starting timer for chatId: " + chatId);
    log.info("Language: " + language);
    Optional<User> userOptional = userService.getUserById(telegramUserId);
    if (userOptional.isPresent()) { // we have a User let's search for a session
      User user = userOptional.get();
      Session session = sessionService.getSessionByUser(user);
      if (session != null) { // old User old Session
        startTimerForSession(session, language);
      } else { // old User new Session
        createTimerSessionForUser(chatId, user, language);
      }
    } else { // new User new Session
      createUserAndTimerSession(chatId, telegramUserId, language);
    }
  }

  public void resetTimer(String chatId, Long telegramUserId, String language) {
    log.info("Resetting timer for chatId: " + chatId);
    log.info("Language: " + language);
    Optional<User> userOptional = userService.getUserById(telegramUserId);
    String returnMessageText;
    returnMessageText = getSessionIsNotActiveMessageWithLocale(language);

    if (userOptional.isPresent()) {
      User user = userOptional.get();
      Session session = sessionService.getSessionByUser(user);
      returnMessageText = getResetCompletedMessageWithLocale(language);
      userService.resetUserLevelToFirstOne(user);
      if (session != null) {
        sessionService.deleteSession(session);
      }
    }
    messageService.sendMessage(chatId, returnMessageText);
  }

  public void runTimer(TimerTask timerTask) {
    Session session = sessionService.getSessionByUser(timerTask.getSession().getUser());
    if (session != null && session.isActive()) {
      Level level = levelService.next(session.getCurrentLevel(), session.getCurrentPosition());
      String chatId = session.getChatId();
      if (session.getCurrentLevel().getId() == MAX_LEVEL && level.getId() == MIN_LEVEL) {
        messageService.sendMessage(chatId, getFinishMessageWithLocale(session.getLang()));
        userService.resetUserLevelToFirstOne(session.getUser());
        sessionService.deleteSession(session);
        timerTask.cancel();
      } else {
        session.setCurrentLevel(level);
        session.setCurrentPosition(Position.next(session.getCurrentPosition()));
        activateSessionTimer(chatId, session, session.getLang());
      }
    }
  }

  private void startTimerForSession(Session session, String language) {
    String chatId = session.getChatId();
    if (session.isActive()) { // do nothing
      messageService.sendMessage(chatId, getAlreadyInSessionMessageWithLocale(language));
    } else { // resume session
      if ((sessionService.isSessionOverdue(session))) { // resume with currentLevel - 2
        activateSessionTimer(chatId, levelService.getSessionWithLevelReduction(session), language);
      } else { // resume with currenLevel
        activateSessionTimer(chatId, session, language);
      }
    }
  }

  private void activateSessionTimer(String chatId, Session session, String language) {
    String returnMessageText =
        getReturnMessage(session.getCurrentLevel(), session.getCurrentPosition(), language);
    session.setActive(true);
    createTimerAndSaveSession(session);
    messageService.sendMessage(chatId, returnMessageText);
  }

  private void createTimerSessionForUser(String chatId, User user, String language) {
    Level level = user.getCurrentLevel();
    Position position = user.getCurrentPosition();
    Session session = new Session(level, position, user, chatId);
    session.setLang(language);
    activateSessionTimer(chatId, session, language);
  }

  private void createUserAndTimerSession(String chatId, Long telegramUserId, String language) {
    Level level = levelService.getFirstLevel();
    User userToSave = new User(telegramUserId, level, FRONT);
    userService.saveUser(userToSave);
    Session session = new Session(level, FRONT, userToSave, chatId);
    session.setLang(language);
    activateSessionTimer(chatId, session, language);
  }

  private void createTimerAndSaveSession(Session session) {
    Timestamp sessionDelay =
        new TimerTask(session).scheduleTimer(sessionService.getDelayDurationMS(session));
    session.setFinishTimerDate(sessionDelay);
    sessionService.saveSession(session);
  }

  private void deactivateAndSaveSession(Session session) {
    session.setActive(false);
    sessionService.saveSession(session);
  }

  private String getReturnMessage(Level level, Position position, String language) {
    String formatString = getFormatStringWithLocale(language);
    return String.format(
        formatString, position, level.getPositionTimeInMinutes(position), level.getId());
  }

  private class TimerTask extends java.util.TimerTask {

    @Getter public final Session session;
    private final Timer timer = new Timer();

    public TimerTask(Session session) {
      this.session = session;
    }

    @Override
    public void run() {
      runTimer(this);
    }

    public Timestamp scheduleTimer(Duration delay) {
      Timestamp finishTimerDate = new Timestamp(System.currentTimeMillis() + delay.toMillis());
      // Schedule the timer task
      timer.schedule(this, delay.toMillis());
      return finishTimerDate;
    }
  }
}
