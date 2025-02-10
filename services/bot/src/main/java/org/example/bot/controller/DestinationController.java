package org.example.bot.controller;

import org.example.bot.config.Bot;
import org.example.bot.domain.UserEntity;
import org.example.bot.repository.UserRepository;
import org.example.bot.util.Sender;
import org.example.common.util.jms.VideoDownloadReply;
import org.example.jms.JmsPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
public class DestinationController {

    private final UserRepository userRepository;
    private final JmsPublisher jmsPublisher;

    public DestinationController(final UserRepository userRepository, final JmsPublisher jmsPublisher) {
        this.userRepository = userRepository;
        this.jmsPublisher = jmsPublisher;
    }

    public Mono<Void> handle(final Update update) {
        final String text = update.getMessage().getText();

        if (isLink(text)) {
            return jmsPublisher.publish(new VideoDownloadReply(update.getMessage().getChatId(), text, Instant.now()));
        } else {
            // TODO: Music search logic
            return Mono.empty();
        }
    }


    private boolean isLink(final String text) {
        return text.startsWith("https://");
    }

    public Mono<Void> createNewUser(final Update update, final Bot bot) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(update.getMessage().getFrom().getFirstName());
        userEntity.setLastName(update.getMessage().getFrom().getLastName());
        userEntity.setTelegramId(update.getMessage().getChatId());
        Sender.sendMsg(userEntity.getTelegramId(), "Hello", bot);
        return userRepository.save(userEntity).then();
    }
}