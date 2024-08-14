package org.example.notification.jms.consume.notworked;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Delivery;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.notification.service.EmailService;
import org.example.jms.BaseConsumer;
import org.example.jms.JmsConsumer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Arrays;

@Log4j2
@JmsConsumer(targetType = "SendEmailReply")
@Component
@RequiredArgsConstructor
public class SendEmailConsumer extends BaseConsumer {

    private final ObjectMapper objectMapper;
    private final EmailService emailService;


    @Override
    protected Mono<Void> consume(final Delivery message) throws IOException {
        System.out.println("jniimjimijmijmjimjimij");
        log.debug(Arrays.toString(message.getBody()));

        return Mono.just("").then();
    }
}
