package org.example.bot.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.bot.config.Bot;
import org.example.bot.domain.UserEntity;
import org.example.bot.jms.MessagePublisher;
import org.example.bot.repository.UserRepository;
import org.example.bot.util.Sender;
import org.example.common.util.jms.VideoDownloadReply;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Instant;

@Component
@Log4j2
@AllArgsConstructor
public class DestinationController {

    private final UserRepository userRepository;
    private final MessagePublisher messagePublisher;



    public void handle(final Update update) {

        final String text = update.getMessage().getText();
        log.info("Start handle method");
        if (isLink(text)) {
             messagePublisher.publish(new VideoDownloadReply(update.getMessage().getChatId(), text, Instant.now()));
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