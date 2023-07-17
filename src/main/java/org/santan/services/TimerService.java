package org.santan.services;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.santan.TimerTask;
import org.santan.entities.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimerService {

  private final LevelService levelService;
  private final SessionService sessionService;
  private final UserService userService;
  private final MessageService messageService;

  public void startTimer(String chatId, Long telegramUserId) {
    Optional<User> userOptional = userService.getUserById(telegramUserId);
    if (userOptional.isPresent()) { // we have a User let's search for a session
      User user = userOptional.get();
      Session session = sessionService.getSessionByUser(user);
      if (session != null) { // old User old Session
        if (session.isActive()) { // do nothing
          noActionsNeeded(chatId);
        } else { // resume session
          boolean isSessionOverdue =
              session
                  .getFinishTimerDate()
                  .before(Timestamp.valueOf(LocalDate.now().atStartOfDay()));
          if (isSessionOverdue) { // resume with currentLevel - 2
            resumeTimerSessionMinusTwoLevels(chatId, session);
          } else { // resume with currenLevel
            resumeTimerSession(chatId, session);
          }
        }
      } else { // old User new Session
        createTimerSessionForUser(chatId, user);
      }
    } else { // new User new Session
      createUserAndTimerSession(chatId, telegramUserId);
    }
  }

  private void resumeTimerSession(String chatId, Session session) {
    String returnMessageText;
    returnMessageText = getReturnMessage(session.getCurrentLevel(), session.getCurrentPosition());
    session.setActive(true);
    activateTimer(session);
    messageService.sendMessage(chatId, returnMessageText);
  }

  private void resumeTimerSessionMinusTwoLevels(String chatId, Session session) {
    Level level;
    Position position;
    String returnMessageText;
    level = levelService.getLevelById(session.getCurrentLevel().getId());
    if (level.getId() == 1 || level.getId() == 2) {
      level = levelService.getFirstLevel();
    } else {
      level = levelService.getLevelById(session.getCurrentLevel().getId() - 2);
    }

    position = Position.FRONT;
    session.setCurrentLevel(level);
    session.setCurrentPosition(position);
    session.setActive(true);
    returnMessageText = getReturnMessage(level, position);
    activateTimer(session);
    messageService.sendMessage(chatId, returnMessageText);
  }

  private void createTimerSessionForUser(String chatId, User user) {
    Session session;
    Position position;
    Level level;
    String returnMessageText;
    level = user.getCurrentLevel();
    position = user.getCurrentPosition();

    session = new Session(level, position, user, chatId, true);
    activateTimer(session);

    returnMessageText = getReturnMessage(level, position);
    messageService.sendMessage(chatId, returnMessageText);
  }

  private void noActionsNeeded(String chatId) {
    String returnMessageText;
    returnMessageText = Messages.YOU_ARE_ALREADY_IN_A_SESSION;
    messageService.sendMessage(chatId, returnMessageText);
  }

  private void createUserAndTimerSession(String chatId, Long telegramUserId) {
    Level level;
    String returnMessageText;
    level = levelService.getFirstLevel();

    User userToSave = new User(telegramUserId, level, Position.FRONT);
    userService.saveUser(userToSave);

    Session session = new Session(level, Position.FRONT, userToSave, chatId, true);
    activateTimer(session);

    returnMessageText = getReturnMessage(level, Position.FRONT);
    messageService.sendMessage(chatId, returnMessageText);
  }

  public void runTimer(TimerTask timerTask) {
    Optional<Session> sessionOptional =
        sessionService.getSessionById(timerTask.getSession().getId());
    if (sessionOptional.isPresent()) {
      Session session = sessionOptional.get();
      if (session.isActive()) {
        Level level = levelService.next(session.getCurrentLevel(), session.getCurrentPosition());
        String returnMessageText;

        if (session.getCurrentLevel().getId() == 12 && level.getId() == 1) {
          User user = session.getUser();
          userService.resetUserLevelToFirstOne(user);
          sessionService.deleteSession(session);
          returnMessageText = Messages.FINISH_MESSAGE;
        } else {
          Position position = Position.next(session.getCurrentPosition());
          session.setCurrentLevel(level);
          session.setCurrentPosition(position);
          activateTimer(session);
          returnMessageText = getReturnMessage(level, position);
        }
        messageService.sendMessage(session.getChatId(), returnMessageText);
      }
    }
  }

  public void pauseTimer(String chatId, Long telegramUserId) {
    Optional<User> userOptional = userService.getUserById(telegramUserId);
    String returnMessageText = Messages.SESSION_IS_NOT_ACTIVE;
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      Session session = sessionService.getSessionByUser(user);
      if (session != null) {
        returnMessageText = Messages.TIMER_PAUSED;
        session.setActive(false);
        sessionService.saveSession(session);
        user.setCurrentPosition(session.getCurrentPosition());
        user.setCurrentLevel(session.getCurrentLevel());
        userService.saveUser(user);
      }
    }
    messageService.sendMessage(chatId, returnMessageText);
  }

  public void resetTimer(String chatId, Long telegramUserId) {
    Optional<User> userOptional = userService.getUserById(telegramUserId);

    String returnMessageText = Messages.SESSION_IS_NOT_ACTIVE;
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      Session session = sessionService.getSessionByUser(user);

      returnMessageText = Messages.RESET_COMPLETED_YOU_ARE_NOW_ON_THE_FIRST_LEVEL;
      userService.resetUserLevelToFirstOne(user);
      if (session != null) {
        sessionService.deleteSession(session);
      }
    }
    messageService.sendMessage(chatId, returnMessageText);
  }

  /* Delay before the timer starts (in milliseconds)
   */
  private long getDelayMS(Session session) {

    Level level = session.getCurrentLevel();
    Position position = session.getCurrentPosition();
    return (long) (level.getPositionTime(position) * 60 * 1000);
  }

  private void activateTimer(Session session) {
    Timestamp sessionDelay = new TimerTask(this, session).scheduleTimer(getDelayMS(session));

    session.setFinishTimerDate(sessionDelay);
    sessionService.saveSession(session);
  }

  private String getReturnMessage(Level level, Position position) {
    String returnMessageText;
    returnMessageText =
        String.format(
            Messages.TURN_TO_THE_NEXT_SIDE_MESSAGE,
            position,
            level.getPositionTime(position),
            level.getId());
    return returnMessageText;
  }

  public void onStart() {
    List<Session> activeSessions = sessionService.getAllActiveSessions();
    String returnMessageText = Messages.SOME_ERROR_HAPPENED_ACTIVE_TIMER_RELAUNCHED;
    for (Session session : activeSessions) {
      messageService.sendMessage(session.getChatId(), returnMessageText);
      activateTimer(session);
    }
  }
}
