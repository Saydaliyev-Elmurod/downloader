package org.example.bot.domain.bot.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.bot.domain.bot.config.Bot;
import org.example.bot.domain.bot.domain.UserEntity;
import org.example.bot.domain.bot.jms.config.MessagePublisher;
import org.example.bot.domain.bot.repository.UserRepository;
import org.example.bot.domain.bot.service.DownloadService;
import org.example.bot.domain.bot.util.Sender;
import org.example.common.util.jms.VideoDownloadReply;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Instant;

@Component
@Log4j2
@AllArgsConstructor
public class MessageController {
    private final Bot bot;

    private final MessagePublisher messagePublisher;
    private final UserRepository userRepository;
    private final DownloadService downloadService;

    public void handle(final Update update) {
        final String text = update.getMessage().getText();
        log.info("Start handle method");
        if (isLink(text)) {
            downloadService.downloadVideo(text,"home/elmurod/videos",update.getMessage().getChatId());
//            if (video != null) {
//                Sender.send();
//            } else {
//                messagePublisher.publish(new VideoDownloadReply(update.getMessage().getChatId(), text, Instant.now()));
//            }

        } else {
            // TODO: Music search logic

        }
    }

    private boolean isLink(final String text) {
        return text.startsWith("https://");
    }

    public void createNewUser(final Update update, final Bot bot) {
        log.info("Start createNewUser method");
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(update.getMessage().getFrom().getFirstName());
        userEntity.setLastName(update.getMessage().getFrom().getLastName());
        userEntity.setTelegramId(update.getMessage().getChatId());
        Sender.sendMsg(userEntity.getTelegramId(), "Hello", bot);
        userRepository.save(userEntity);
    }


}
