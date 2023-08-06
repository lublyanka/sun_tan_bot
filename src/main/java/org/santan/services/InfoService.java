package org.santan.services;

import static org.santan.entities.Messages.getHelpMessageWithLocale;
import static org.santan.entities.Messages.getStartMessageWithLocale;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log
public class InfoService {

  private final MessageService messageService;

  public void sendHelpResponse(String chatId, String language) {
    log.info("Sending help response to chatId: " + chatId);
    log.info("Language: " + language);
    String returnMessageText = getHelpMessageWithLocale(language);
    messageService.sendMessage(chatId, returnMessageText);
  }

  public void sendStartResponse(String chatId, String language) {
    log.info("Sending start response to chatId: " + chatId);
    log.info("Language: " + language);
    String returnMessageText = getStartMessageWithLocale(language);
    messageService.sendMessage(chatId, returnMessageText);
  }
}