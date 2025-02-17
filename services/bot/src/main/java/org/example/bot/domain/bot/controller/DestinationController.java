package org.example.bot.domain.bot.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.bot.domain.bot.config.Bot;
import org.example.bot.domain.bot.util.Sender;
import org.example.common.model.jms.SendInstagramVideoReply;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Log4j2
@AllArgsConstructor
public class DestinationController {
    private final Bot bot;
    private final MessageController messageController;


    public void handle(final Update update) {
        messageController.handle(update);
    }


    public void createNewUser(final Update update, final Bot bot) {
        messageController.createNewUser(update, bot);
    }

    public void sendVideo(final SendInstagramVideoReply reply) {
        SendVideo video = new SendVideo();
        video.setChatId(reply.telegramId());
        video.setVideo(new InputFile(reply.url()));
        video.setThumbnail(new InputFile(reply.thumb()));
        video.setCaption(reply.hash());
        Sender.send(video, bot);
    }
}