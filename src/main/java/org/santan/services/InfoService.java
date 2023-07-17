package org.santan.services;

import java.util.function.Consumer;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class InfoService {
  public void sendHelpResponse(Consumer<SendMessage> send, String chatId) {
    SendMessage startMessage =
        new SendMessage(
            chatId,
            "Here are the available commands:\n"
                + "/start - Start the bot \n"
                + "/help - Show this help message \n"
                + "/go - Start suntan timer \n"
                + "/pause - Pause the timer\n"
                + "/reset - Reset the timer and start from the first level\n");
    send.accept(startMessage);
  }

  public void sendStartResponse(Consumer<SendMessage> send, String chatId) {
    SendMessage startMessage =
        new SendMessage(
            chatId,
            "Hello! Welcome to San Tan bot. \n"
                + "I am here to help you to get a suntan s fast as possible. \n"
                + "To get started, please type /help to see a list of available commands.");
    send.accept(startMessage);
  }
}
