package org.santan.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

class MessageServiceTest {
  String botToken = "123asd";

  DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
  MessageService messageService = new MessageService(defaultBotOptions, botToken);
  @Test
  void createSendMessage() {
    // given
    String chatId = "123";
    String message = "test text";
    SendMessage returnMessage = new SendMessage();
    returnMessage.setText(message);
    returnMessage.setChatId(chatId);

    // when
    SendMessage result = messageService.createSendMessage(chatId, message);

    // then
    assertEquals(returnMessage, result);
  }
}
