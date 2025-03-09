package org.example.bot.domain.bot.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

@Component
@Log4j2
public class BotInitializer {
    @Lazy
    @Autowired
    private Bot bot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        try (TelegramBotsLongPollingApplication telegramBotsApi =
                     new TelegramBotsLongPollingApplication();
        ) {
            telegramBotsApi.registerBot(bot.getBotToken(), bot);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}