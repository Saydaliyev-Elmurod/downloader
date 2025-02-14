package org.example.bot.config;

import lombok.extern.log4j.Log4j2;
import org.example.bot.controller.DestinationController;
import org.example.bot.domain.UserEntity;
import org.example.bot.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class Bot extends TelegramLongPollingBot {
    @Lazy
    private final BotConfig config;
    @Lazy
    private final DestinationController destinationController;
    private final UserRepository userRepository;

    public Bot(BotConfig config, DestinationController destinationController, final UserRepository userRepository) {
        this.config = config;
        this.destinationController = destinationController;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "get a welcome message"));
        listOfCommands.add(new BotCommand("/help", "info how to use this bot"));
        listOfCommands.add(new BotCommand("/settings", "set your preferences"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(String.valueOf(e.getCause()));
        }
        this.userRepository = userRepository;
    }

    @Override
    public String getBotUsername() {
        return config.getBotUserName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Received update from chat [{}]", update.getMessage().getChatId());

        final UserEntity user = userRepository.findByTelegramId(update.getMessage().getChatId());
        if (user == null) {
            destinationController.createNewUser(update, this);
        } else {
            destinationController.handle(update);
        }
    }


}
