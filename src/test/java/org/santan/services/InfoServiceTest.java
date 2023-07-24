package org.santan.services;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.santan.entities.Messages;

class InfoServiceTest {
  private final String chatId = "123456";
  MessageService messageService = Mockito.mock(MessageService.class);
  InfoService infoService = new InfoService(messageService);

  @Test
  void sendHelpResponse_EN() {
    infoService.sendHelpResponse(chatId, "en");
    Mockito.verify(messageService, times(1)).sendMessage(eq(chatId), eq(Messages.HELP_MESSAGE_EN));
  }
  @Test
  void sendHelpResponse_ES() {
    infoService.sendHelpResponse(chatId, "es");
    Mockito.verify(messageService, times(1)).sendMessage(eq(chatId), eq(Messages.HELP_MESSAGE_ES));
  }
  @Test
  void sendHelpResponse_RU() {
    infoService.sendHelpResponse(chatId, "ru");
    Mockito.verify(messageService, times(1)).sendMessage(eq(chatId), eq(Messages.HELP_MESSAGE_RU));
  }

  @Test
  void sendStartResponse_EN() {
    infoService.sendStartResponse(chatId, "en");
    Mockito.verify(messageService, times(1)).sendMessage(eq(chatId), eq(Messages.START_MESSAGE_EN));
  }

  @Test
  void sendStartResponse_ES() {
    infoService.sendStartResponse(chatId, "es");
    Mockito.verify(messageService, times(1)).sendMessage(eq(chatId), eq(Messages.START_MESSAGE_ES));
  }

  @Test
  void sendStartResponse_RU() {
    infoService.sendStartResponse(chatId, "ru");
    Mockito.verify(messageService, times(1)).sendMessage(eq(chatId), eq(Messages.START_MESSAGE_RU));
  }


}
