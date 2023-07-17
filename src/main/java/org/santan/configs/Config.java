package org.santan.configs;

import lombok.SneakyThrows;
import org.santan.SanTanBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class Config {
    @SneakyThrows
    @Bean
    public TelegramBotsApi botsApi() {
        return new TelegramBotsApi(DefaultBotSession.class);
    }

    @Bean(initMethod = "init")
    public TelegramInitializer telegramInitializer(TelegramBotsApi botsApi, SanTanBot bot) {
        return new TelegramInitializer(botsApi, bot);
    }
}
