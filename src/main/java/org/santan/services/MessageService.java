package org.santan.services;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MessageService extends DefaultAbsSender {

  public MessageService(DefaultBotOptions options, String botToken) {
    super(options, botToken);
  }

  public SendMessage createSendMessage(String chatId, String returnMessageText) {
    SendMessage returnMessage = new SendMessage();
    returnMessage.setText(returnMessageText);
    returnMessage.setChatId(String.valueOf(chatId));
    return returnMessage;
  }

  public void sendMessage(String chatId, String returnMessageText) {
    SendMessage returnMessage = createSendMessage(chatId, returnMessageText);
    executeSafe(returnMessage);
  }

  private void executeSafe(SendMessage sendMessage) {
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }
}
