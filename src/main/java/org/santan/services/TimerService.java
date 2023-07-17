package org.santan.services;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import org.santan.TimerTask;
import org.santan.entities.Level;
import org.santan.entities.Position;
import org.santan.entities.Session;
import org.santan.entities.User;
import org.santan.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class TimerService {
  @Autowired private LevelService levelService;
  @Autowired private SessionService sessionService;
  @Autowired private UserService userService;

  private Consumer<SendMessage> send;
  private SendMessage message;

  public void startTimer(Consumer<SendMessage> send, String chatId, Long telegramUserId) {
    this.send = send;
    //this.message = new SendMessage();
    //this.message.setChatId(chatId);
    Optional<User> userOptional = userService.getUserById(telegramUserId);
    String returnMessageText;
    Level level;
    Position position;
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      Session session = sessionService.getSessionByUser(user);
      if (session != null) { // old User old Session
        if (session.isActive()) {
          returnMessageText = "You are already in a session!";
        } else {
          if (session
              .getFinishTimerDate()
              .before(Timestamp.valueOf(LocalDate.now().atStartOfDay()))) {
            // then we start currentLevel - 2

            level = levelService.getLevelById(session.getCurrentLevel().getId());
            if (level.getId() == 1 || level.getId() == 2) {
              level = levelService.getFirstLevel();
            } else {
              level = levelService.getLevelById(session.getCurrentLevel().getId() - 2);
            }

            position = Position.FRONT;
            session.setCurrentLevel(level);
            session.setCurrentPosition(position);
            returnMessageText = getString(level, position);
            activateTimer(session);

          } else // we are inside the same session of the same day, so we go from where we are;
          {
            returnMessageText = getString(session.getCurrentLevel(), session.getCurrentPosition());
            activateTimer(session);
          }
        }
      } else { // old User new Session
        level = user.getCurrentLevel();
        position = user.getCurrentPosition();

        session = new Session(level, position, user, chatId, true);
        activateTimer(session);

        returnMessageText = getString(level, position);
      }
    } else { // new User new Session
      level = levelService.getFirstLevel();

      User userToSave = new User(telegramUserId, level, Position.FRONT);
      userService.saveUser(userToSave);

      Session session = new Session(level, Position.FRONT, userToSave, chatId, true);
      activateTimer(session);

      returnMessageText = getString(level, Position.FRONT);
    }
    sendMessage(send, chatId, returnMessageText);
  }

  private void activateTimer(Session session) {
    Timestamp sessionDelay = new TimerTask(this, session).scheduleTimer(getDelayMS(session));

    session.setFinishTimerDate(sessionDelay);
    sessionService.saveSession(session);
  }

  private String getString(Level level, Position position) {
    String returnMessageText;
    returnMessageText =
        String.format(
            "Turn to the %s side for %3.1f minutes (level %d)",
            position, level.getPositionTime(position), level.getId());
    return returnMessageText;
  }

  private void sendMessage(Consumer<SendMessage> send, String chatId, String returnMessageText) {
    SendMessage returnMessage = new SendMessage();
    returnMessage.setText(returnMessageText);
    returnMessage.setChatId(String.valueOf(chatId));
    send.accept(returnMessage);
  }

  public void runTimer(TimerTask timerTask) {
    Session session = timerTask.getSession();
    if (session.isActive()) {
      Level level = levelService.next(session.getCurrentLevel(), session.getCurrentPosition());
      String returnMessageText;

      if (session.getCurrentLevel().getId() == 12 && level.getId() == 1) {
        User user = session.getUser();
        userService.resetUserLevelToFirstOne(user);
        sessionService.deleteSession(session);
        returnMessageText =
            "You finished you session completely! Congratulations! \n"
                + "If you want you can start a new one by typing /go";
      } else {
        Position position = Position.next(session.getCurrentPosition());
        session.setCurrentLevel(level);
        session.setCurrentPosition(position);
        activateTimer(session);
        returnMessageText =
            String.format(
                "Turn to the %s side for %3.1f minutes (level %d)",
                position, level.getPositionTime(position), level.getId());
      }
      sendMessage(send, session.getChatId(), returnMessageText);
      //message.setText(returnMessageText);
      //send.accept(message);
    }
  }

  /* Delay before the timer starts (in milliseconds)
   */
  private long getDelayMS(Session session) {

    Level level = session.getCurrentLevel();
    Position position = session.getCurrentPosition();
    return (long) (level.getPositionTime(position) * 60 * 1000);
  }

  public void pauseTimer(Consumer<SendMessage> send, String chatId, Long telegramUserId) {
    Optional<User> userOptional = userService.getUserById(telegramUserId);
    String returnMessageText = "Session is not active";
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      Session session = sessionService.getSessionByUser(user);
      if (session != null) {
        returnMessageText = "Timer paused";
        session.setActive(false);
        sessionService.saveSession(session);
        user.setCurrentPosition(session.getCurrentPosition());
        user.setCurrentLevel(session.getCurrentLevel());
        userService.saveUser(user);
      }
    }
    sendMessage(send, chatId, returnMessageText);
  }

  public void resetTimer(Consumer<SendMessage> send, String chatId, Long telegramUserId) {
    Optional<User> userOptional = userService.getUserById(telegramUserId);

    String returnMessageText = "Session is not active";
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      Session session = sessionService.getSessionByUser(user);

      returnMessageText = "Reset completed. You are now on the first level";
      userService.resetUserLevelToFirstOne(user);
      if (session != null) {
        sessionService.deleteSession(session);
      }
    }
    sendMessage(send, chatId, returnMessageText);
  }

  public void onStart(Consumer<SendMessage> send) {
    List<Session> activeSessions = sessionService.getAllActiveSessions();
    String returnMessageText = "Some error happened, active timer relaunched";
    for (Session session : activeSessions) {
      sendMessage(send, session.getChatId(), returnMessageText);
      activateTimer(session);
    }
  }
}
