package org.santan;

import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.santan.services.InfoService;
import org.santan.services.TimerService;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
@RequiredArgsConstructor
public class UpdateReceivedController {

  private final InfoService infoService;
  private final TimerService timerService;

  public void doUpdate(Update update, Consumer<SendMessage> send) {

    Message message = update.getMessage();
    String userMessage = message.getText();
    String chatId = String.valueOf(message.getChatId());
    Long userId = message.getFrom().getId();
    switch (userMessage) {
      case "/start":
        {
          infoService.sendStartResponse(chatId);
          break;
        }
      case "/go":
        {
          timerService.startTimer(chatId, userId);
          break;
        }
      case "/pause":
        {
          timerService.pauseTimer(chatId, userId);
          break;
        }
      case "/reset":
        {
          timerService.resetTimer(chatId, userId);
          break;
        }
      case "/help":
        {
          infoService.sendHelpResponse(chatId);
          break;
        }
      default:
        break;
    }
  }
}
