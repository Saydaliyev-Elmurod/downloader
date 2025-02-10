package org.example.bot.config;

import lombok.extern.log4j.Log4j2;
import org.example.bot.controller.DestinationController;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class Bot extends TelegramLongPollingBot {
    @Lazy
    private final BotConfig config;
    @Lazy
    private final DestinationController destinationController;

    public Bot(BotConfig config, DestinationController destinationController) {
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
        log.debug(update);
        SendVideo sendVideo = new SendVideo();
        sendVideo.setChatId(update.getMessage().getChatId());
//        sendVideo.setVideo(new InputFile(new File(
//                "/home/elmurod/Downloads/Telegram Desktop/Classical_Music_for_Brain_Power___Mozart,_Beethoven,_Vivaldi_.mp4")));
        sendVideo.setVideo(new InputFile("BAACAgQAAxkBAAIsuGeiSvniCHd6E5NJBaFocSEIWLKAAAIUBwAC-5DlUAlDbhGr6VtSNgQ"));
        try {
            this.execute(sendVideo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error(String.valueOf(e.getCause()));
        }
    }

}
