package org.example.bot.domain.bot.config;

import lombok.extern.log4j.Log4j2;
import org.example.bot.domain.bot.controller.DestinationController;
import org.example.bot.domain.bot.domain.UserEntity;
import org.example.bot.domain.bot.repository.UserRepository;
import org.example.bot.domain.bot.util.Sender;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class Bot implements LongPollingUpdateConsumer, SpringLongPollingBot {
    @Lazy
    private final BotConfig config;
    private TelegramClient telegramClient = null;
    @Lazy
    private final DestinationController destinationController;
    private final UserRepository userRepository;

    public Bot(BotConfig config, @Lazy DestinationController destinationController, final UserRepository userRepository) {
        this.config = config;
        this.destinationController = destinationController;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "get a welcome message"));
        listOfCommands.add(new BotCommand("/help", "info how to use this bot"));
        listOfCommands.add(new BotCommand("/settings", "set your preferences"));
        this.send(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        this.userRepository = userRepository;
        telegramClient = new OkHttpTelegramClient(this.config.token);
    }



    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    public void onUpdateReceived(Update update) {
        log.info("Received update from chat [{}]", update.getMessage().getChatId());
        final UserEntity user = userRepository.findByTelegramId(update.getMessage().getChatId());
        if (user == null) {
            destinationController.createNewUser(update, this);
        } else {
            destinationController.handle(update);
        }
    }

    @Override
    public void consume(final List<Update> list) {
        list.forEach(this::onUpdateReceived);
    }
    public final String send(Object o) {
        try {
            if (o instanceof SendPhoto) {
                telegramClient.execute((SendPhoto) o);
            } else if (o instanceof SendMessage) {
                telegramClient.execute((SendMessage) o);
            } else if (o instanceof EditMessageText) {
                telegramClient.execute((EditMessageText) o);
            } else if (o instanceof AnswerCallbackQuery) {
                telegramClient.execute((AnswerCallbackQuery) o);
            }
        } catch (Exception e) {
            log.error("Error send message {} {}", e.getCause(), e.getMessage());
            return e.getMessage() + e.getCause();
        }
        return null;
    }
}
