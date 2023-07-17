package org.santan;

import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.santan.services.InfoService;
import org.santan.services.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
@RequiredArgsConstructor
public class UpdateReceivedController {

  @Autowired private InfoService infoService;
  @Autowired private TimerService timerService;

  public void doUpdate(Update update, Consumer<SendMessage> send) {

    Message message = update.getMessage();
    String userMessage = message.getText();
    switch (userMessage) {
      case "/start":
        {
          infoService.sendStartResponse(send, String.valueOf(message.getChatId()));
          break;
        }
      case "/go":
        {
          timerService.startTimer(
              send, String.valueOf(message.getChatId()), message.getFrom().getId());
          break;
        }
      case "/pause":
        {
          timerService.pauseTimer(
              send, String.valueOf(message.getChatId()), message.getFrom().getId());
          break;
        }
      case "/reset":
        {
          timerService.resetTimer(
              send, String.valueOf(message.getChatId()), message.getFrom().getId());
          break;
        }
      case "/help":
        {
          infoService.sendHelpResponse(send, String.valueOf(message.getChatId()));
          break;
        }
      default:
        break;
    }
  }
}
