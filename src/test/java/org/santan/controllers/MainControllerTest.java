package org.santan.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.santan.services.InfoService;
import org.santan.services.TimerService;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.mockito.Mockito.*;

class MainControllerTest {

  InfoService infoService = Mockito.mock(InfoService.class);
  TimerService timerService = Mockito.mock(TimerService.class);
  Update update = Mockito.mock(Update.class);
  Message message = Mockito.mock(Message.class);

  User user = Mockito.mock(User.class);
  Long chatId = 1L;
  String chatIdS = chatId.toString();
  Long userId = 123L;

  MainController mainController = new MainController(infoService, timerService);

  @BeforeEach
  void init() {
    when(update.getMessage()).thenReturn(message);
    when(message.getChatId()).thenReturn(chatId);
    when(message.getFrom()).thenReturn(user);
    when(user.getId()).thenReturn(userId);
    when(user.getLanguageCode()).thenReturn(null);
  }

  @Test
  void doUpdate_Start() {
    when(message.getText()).thenReturn("/start");
    mainController.doUpdate(update);
    verify(update, times(1)).getMessage();
    verify(message, times(1)).getText();
    verify(message, times(1)).getChatId();
    verify(message, times(1)).getFrom();
    verify(user, times(1)).getId();
    verify(infoService, times(1)).sendStartResponse(eq(chatIdS), eq(null));
  }

  @Test
  void doUpdate_Help() {
    when(message.getText()).thenReturn("/help");
    mainController.doUpdate(update);
    verify(update, times(1)).getMessage();
    verify(message, times(1)).getText();
    verify(message, times(1)).getChatId();
    verify(message, times(1)).getFrom();
    verify(user, times(1)).getId();
    verify(infoService, times(1)).sendHelpResponse(eq(chatIdS), eq(null));
  }

  @Test
  void doUpdate_Go() {
    when(message.getText()).thenReturn("/go");
    mainController.doUpdate(update);
    verify(update, times(1)).getMessage();
    verify(message, times(1)).getText();
    verify(message, times(1)).getChatId();
    verify(message, times(1)).getFrom();
    verify(user, times(1)).getId();
    verify(timerService, times(1)).startTimer(eq(chatIdS), eq(userId), eq(null));
  }

  @Test
  void doUpdate_Pause() {
    when(message.getText()).thenReturn("/pause");
    mainController.doUpdate(update);
    verify(update, times(1)).getMessage();
    verify(message, times(1)).getText();
    verify(message, times(1)).getChatId();
    verify(message, times(1)).getFrom();
    verify(user, times(1)).getId();
    verify(timerService, times(1)).pauseTimer(eq(chatIdS), eq(userId), eq(null));
  }

  @Test
  void doUpdate_Reset() {
    when(message.getText()).thenReturn("/reset");
    mainController.doUpdate(update);
    verify(update, times(1)).getMessage();
    verify(message, times(1)).getText();
    verify(message, times(1)).getChatId();
    verify(message, times(1)).getFrom();
    verify(user, times(1)).getId();
    verify(timerService, times(1)).resetTimer(eq(chatIdS), eq(userId), eq(null));
  }

  @Test
  void doUpdate_Default() {
    when(message.getText()).thenReturn("/13");
    mainController.doUpdate(update);
    verify(update, times(1)).getMessage();
    verify(message, times(1)).getText();
    verify(message, times(1)).getChatId();
    verify(message, times(1)).getFrom();
    verify(user, times(1)).getId();
    verifyNoInteractions(infoService);
    verifyNoInteractions(timerService);
  }
}
