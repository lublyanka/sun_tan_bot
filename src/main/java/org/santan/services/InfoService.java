package org.santan.services;

import static org.santan.entities.Messages.getHelpMessageWithLocale;
import static org.santan.entities.Messages.getStartMessageWithLocale;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfoService {

  private final MessageService messageService;

  public void sendHelpResponse(String chatId, String language) {
    String returnMessageText = getHelpMessageWithLocale(language);
    messageService.sendMessage(chatId, returnMessageText);
  }

  public void sendStartResponse(String chatId, String language) {
    String returnMessageText = getStartMessageWithLocale(language);
    messageService.sendMessage(chatId, returnMessageText);
  }
}