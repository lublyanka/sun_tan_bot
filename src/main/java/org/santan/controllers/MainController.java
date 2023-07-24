package org.santan.controllers;

import lombok.RequiredArgsConstructor;
import org.santan.services.InfoService;
import org.santan.services.TimerService;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Controller
@RequiredArgsConstructor
public class MainController {

  private final InfoService infoService;
  private final TimerService timerService;

  public void doUpdate(Update update) {

    Message message = update.getMessage();
    User  user = message.getFrom();
    String userMessage = message.getText();
    String chatId = String.valueOf(message.getChatId());
    Long userId = user.getId();
    String languageCode = user.getLanguageCode();
    switch (userMessage) {
      case "/start" -> {
        infoService.sendStartResponse(chatId, languageCode);
      }
      case "/go" -> {
        timerService.startTimer(chatId, userId, languageCode);
      }
      case "/pause" -> {
        timerService.pauseTimer(chatId, userId, languageCode);
      }
      case "/reset" -> {
        timerService.resetTimer(chatId, userId, languageCode);
      }
      case "/help" -> {
        infoService.sendHelpResponse(chatId, languageCode);
      }
      default -> {
      }
    }
  }
}
