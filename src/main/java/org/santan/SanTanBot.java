package org.santan;

import java.util.List;
import java.util.Timer;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class SanTanBot extends TelegramLongPollingBot {

    private final UpdateReceivedController controller;


    public SanTanBot(UpdateReceivedController controller) {
        this.controller = controller;
    }


    @Override
    public String getBotUsername() {
        return "Sun tan bot";
    }

    @Override
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
            //UpdateReceivedController updateReceivedController = new UpdateReceivedController();
            //updateReceivedController.doUpdate(update, this::executeSafe);
            controller.doUpdate(update, sendMessage -> executeSafe(sendMessage));
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    public void executeSafe(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
