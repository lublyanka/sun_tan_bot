package org.santan.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.santan.entities.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.santan.entities.Messages.YOU_ARE_ALREADY_IN_A_SESSION_EN;

class TimerServiceTest {

  LevelService levelService = Mockito.mock(LevelService.class);
  SessionService sessionService = Mockito.mock(SessionService.class);
  UserService userService = Mockito.mock(UserService.class);
  MessageService messageService = Mockito.mock(MessageService.class);
  TimerService timerService =
      new TimerService(levelService, sessionService, userService, messageService);

  @Test
  void onStart() {
    String chatId = "1";
    List<Session> sessionList = new ArrayList<>();
    Session session = new Session();
    session.setChatId(chatId);
    session.setCurrentPosition(Position.BACK);
    session.setCurrentLevel(new Level(1, 1, 1, 1, 1, 1));
    sessionList.add(session);
    when(sessionService.getAllActiveSessions()).thenReturn(sessionList);
    doNothing().when(messageService).sendMessage(any(), any(String.class));
    doNothing().when(sessionService).saveSession(any(Session.class));
    timerService.onStart();
    verify(sessionService).saveSession(any(Session.class));
    verify(sessionService).getDelayDurationMS(eq(session));
    verify(messageService, atLeastOnce()).sendMessage(any(), any(String.class));
  }

  @Test
  void resetTimer_SessionIsNotNull() {
    Long telegramUserId = 1L;
    String chatId = "123";
    User user = new User();
    when(userService.getUserById(telegramUserId)).thenReturn(Optional.of(user));

    Session session = new Session();
    when(sessionService.getSessionByUser(user)).thenReturn(session);
    doNothing().when(userService).resetUserLevelToFirstOne(user);
    doNothing().when(sessionService).deleteSession(session);

    timerService.resetTimer(chatId, telegramUserId, null);
    verify(userService, atMostOnce()).getUserById(eq(telegramUserId));
    verify(sessionService).getSessionByUser(eq(user));
    verify(userService).resetUserLevelToFirstOne(eq(user));
    verify(sessionService).deleteSession(eq(session));
    verify(messageService)
        .sendMessage(eq(chatId), eq(Messages.RESET_COMPLETED_YOU_ARE_NOW_ON_THE_FIRST_LEVEL_EN));
  }

  @Test
  void resetTimer_SessionIsNull() {
    Long telegramUserId = 1L;
    String chatId = "123";
    User user = new User();
    when(userService.getUserById(telegramUserId)).thenReturn(Optional.of(user));

    when(sessionService.getSessionByUser(user)).thenReturn(null);
    doNothing().when(userService).resetUserLevelToFirstOne(user);

    timerService.resetTimer(chatId, telegramUserId, null);
    verify(userService, atMostOnce()).getUserById(eq(telegramUserId));
    verify(sessionService).getSessionByUser(eq(user));
    verify(userService).resetUserLevelToFirstOne(eq(user));
    verify(sessionService, Mockito.never()).deleteSession(any());
    verify(messageService)
        .sendMessage(eq(chatId), eq(Messages.RESET_COMPLETED_YOU_ARE_NOW_ON_THE_FIRST_LEVEL_EN));
  }

  @Test
  void resetTimer_NoUser() {
    Long telegramUserId = 1L;
    String chatId = "123";
    when(userService.getUserById(telegramUserId)).thenReturn(Optional.empty());
    timerService.resetTimer(chatId, telegramUserId, null);
    verify(userService, atMostOnce()).getUserById(eq(telegramUserId));
    verify(messageService).sendMessage(eq(chatId), eq(Messages.SESSION_IS_NOT_ACTIVE_EN));
    verify(sessionService, Mockito.never()).getSessionByUser(any());
    verify(userService, Mockito.never()).resetUserLevelToFirstOne(any());
    verify(sessionService, Mockito.never()).deleteSession(any());
  }

  @Test
  void pauseTimer_NoUser() {
    Long telegramUserId = 1L;
    String chatId = "123";
    when(userService.getUserById(telegramUserId)).thenReturn(Optional.empty());
    timerService.pauseTimer(chatId, telegramUserId, null);
    verify(userService).getUserById(eq(telegramUserId));
    verify(messageService).sendMessage(eq(chatId), eq(Messages.SESSION_IS_NOT_ACTIVE_EN));
    verify(sessionService, Mockito.never()).getSessionByUser(any());
    verify(userService, Mockito.never()).resetUserLevelToFirstOne(any());
    verify(sessionService, Mockito.never()).deleteSession(any());
  }

  @Test
  void pauseTimer_SessionIsNull() {
    Long telegramUserId = 1L;
    String chatId = "123";
    User user = new User();
    when(userService.getUserById(telegramUserId)).thenReturn(Optional.of(user));
    when(sessionService.getSessionByUser(user)).thenReturn(null);

    timerService.pauseTimer(chatId, telegramUserId, null);
    verify(userService).getUserById(eq(telegramUserId));
    verify(sessionService).getSessionByUser(eq(user));
    verify(sessionService, Mockito.never()).saveSession(any());
    verify(userService, Mockito.never()).saveUser(any());
    verify(messageService).sendMessage(eq(chatId), eq(Messages.SESSION_IS_NOT_ACTIVE_EN));
  }

  @Test
  void pauseTimer_SessionIsNotNull() {
    Long telegramUserId = 1L;
    String chatId = "123";
    User user = new User();
    when(userService.getUserById(telegramUserId)).thenReturn(Optional.of(user));

    Session session = new Session();
    when(sessionService.getSessionByUser(user)).thenReturn(session);
    doNothing().when(userService).saveUser(user);
    doNothing().when(sessionService).saveSession(session);

    timerService.pauseTimer(chatId, telegramUserId, null);
    verify(userService, atMostOnce()).getUserById(eq(telegramUserId));
    verify(sessionService).getSessionByUser(eq(user));
    verify(userService).saveUser(eq(user));
    verify(sessionService).saveSession(eq(session));
    verify(messageService).sendMessage(eq(chatId), eq(Messages.TIMER_PAUSED_EN));
  }

  @Test
  void startTimer_NoUser() {
    Long telegramUserId = 1L;
    String chatId = "123";
    when(userService.getUserById(telegramUserId)).thenReturn(Optional.empty());
    when(levelService.getFirstLevel()).thenReturn(new Level(1, 1, 1, 1, 1, 1));
    doNothing().when(userService).saveUser(any());
    String message = "Get to the FRONT side for 1.0 minute(s) (level 1)";
    doNothing().when(messageService).sendMessage(eq(chatId), eq(message));
    doNothing().when(sessionService).saveSession(any(Session.class));
    when(sessionService.getDelayDurationMS(any(Session.class)))
        .thenReturn(Duration.ofSeconds((long) (1.5 * 60L)));

    timerService.startTimer(chatId, telegramUserId, null);

    verify(userService).getUserById(eq(telegramUserId));
    verify(levelService).getFirstLevel();
    verify(userService).saveUser(any());
    verify(messageService).sendMessage(eq(chatId), eq(message));
    verify(sessionService).saveSession(any(Session.class));
  }

  @Test
  void startTimer_UserWithNoSession() {
    Long telegramUserId = 1L;
    String chatId = "123";
    User user = new User();
    Level level = new Level(1, 1, 1, 1, 1, 1);
    user.setCurrentLevel(level);
    user.setCurrentPosition(Position.LEFT);
    when(userService.getUserById(telegramUserId)).thenReturn(Optional.of(user));
    when(sessionService.getSessionByUser(user)).thenReturn(null);
    String message = "Get to the LEFT side for 1.0 minute(s) (level 1)";
    doNothing().when(messageService).sendMessage(eq(chatId), eq(message));
    doNothing().when(sessionService).saveSession(any(Session.class));

    timerService.startTimer(chatId, telegramUserId, null);

    verify(userService).getUserById(eq(telegramUserId));
    verify(sessionService, times(2)).getSessionByUser(user);
    verify(messageService).sendMessage(eq(chatId), eq(message));
    verify(sessionService).saveSession(any(Session.class));
  }

  @Test
  void startTimer_UserWithActiveSession() {
    Long telegramUserId = 1L;
    String chatId = "123";
    User user = new User();
    Level level = new Level(1, 1, 1, 1, 1, 1);
    user.setCurrentLevel(level);
    user.setCurrentPosition(Position.LEFT);
    when(userService.getUserById(telegramUserId)).thenReturn(Optional.of(user));
    Session session = new Session();
    session.setChatId(chatId);
    session.setActive(true);
    when(sessionService.getSessionByUser(user)).thenReturn(session);
    doNothing().when(messageService).sendMessage(eq(chatId), eq(YOU_ARE_ALREADY_IN_A_SESSION_EN));


    timerService.startTimer(chatId, telegramUserId, null);

    verify(userService).getUserById(eq(telegramUserId));
    verify(sessionService, times(1)).getSessionByUser(user);
    verify(messageService).sendMessage(eq(chatId), eq(YOU_ARE_ALREADY_IN_A_SESSION_EN));
  }

  @Test
  void startTimer_UserWithNotActiveSessionTimerOverdue() {

    Long telegramUserId = 1L;
    String chatId = "123";
    User user = new User();
    Level level = new Level(1, 1, 1, 1, 1, 1);
    user.setCurrentLevel(level);
    user.setCurrentPosition(Position.LEFT);
    when(userService.getUserById(telegramUserId)).thenReturn(Optional.of(user));
    Session session = new Session();
    session.setCurrentLevel(level);
    session.setChatId(chatId);
    session.setActive(false);
    session.setCurrentPosition(Position.FRONT);
    when(sessionService.getSessionByUser(user)).thenReturn(session);
    when(sessionService.isSessionOverdue(session)).thenReturn(true);
    when(levelService.getSessionWithLevelReduction(session)).thenReturn(session);
    String message = "Get to the FRONT side for 1.0 minute(s) (level 1)";
    doNothing().when(messageService).sendMessage(eq(chatId), eq(message));
    doNothing().when(sessionService).saveSession(any(Session.class));

    timerService.startTimer(chatId, telegramUserId, null);

    verify(userService).getUserById(eq(telegramUserId));
    verify(sessionService, times(1)).getSessionByUser(user);
    verify(messageService).sendMessage(eq(chatId), eq(message));
    verify(sessionService).saveSession(any(Session.class));
  }

  @Test
  void startTimer_UserWithNotActiveSessionTimerNotOverdue() {

    Long telegramUserId = 1L;
    String chatId = "123";
    User user = new User();
    Level level = new Level(1, 1, 1, 1, 1, 1);
    user.setCurrentLevel(level);
    user.setCurrentPosition(Position.LEFT);
    when(userService.getUserById(telegramUserId)).thenReturn(Optional.of(user));
    Session session = new Session();
    session.setCurrentLevel(level);
    session.setChatId(chatId);
    session.setActive(false);
    session.setCurrentPosition(Position.LEFT);
    when(sessionService.getSessionByUser(user)).thenReturn(session);
    when(sessionService.isSessionOverdue(session)).thenReturn(false);
    String message = "Get to the LEFT side for 1.0 minute(s) (level 1)";
    doNothing().when(messageService).sendMessage(eq(chatId), eq(message));
    doNothing().when(sessionService).saveSession(any(Session.class));

    timerService.startTimer(chatId, telegramUserId, null);

    verify(userService).getUserById(eq(telegramUserId));
    verify(sessionService, times(1)).getSessionByUser(user);
    verify(messageService).sendMessage(eq(chatId), eq(message));
    verify(sessionService).saveSession(any(Session.class));
  }
}
