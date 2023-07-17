package org.santan.configs;

import lombok.RequiredArgsConstructor;
import org.santan.SanTanBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RequiredArgsConstructor
public class TelegramInitializer {
  private final TelegramBotsApi botsApi;
  private final SanTanBot bot;

  public void init() {
    try {
      botsApi.registerBot(bot);
      System.out.println("Bot started successfully!");
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }
}
