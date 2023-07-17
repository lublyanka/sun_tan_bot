package org.santan.services;

import lombok.RequiredArgsConstructor;
import org.santan.entities.Messages;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfoService {

  private final MessageService messageService;

  public void sendHelpResponse(String chatId) {
    messageService.sendMessage(chatId, Messages.HELP_MESSAGE);
  }

  public void sendStartResponse(String chatId) {
    messageService.sendMessage(chatId, Messages.START_MESSAGE);
  }
}
