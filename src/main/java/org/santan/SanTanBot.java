package org.santan;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.santan.controllers.MainController;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class SanTanBot extends TelegramLongPollingBot {

    private final MainController controller;

    @Override
    public String getBotUsername() {
        return "Sun tan bot";
    }

    @Override
    @SuppressWarnings("deprecation")
    public String getBotToken() {
        return System.getenv("token");
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            controller.doUpdate(update);
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }


}
