package org.example.notification.jms.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Delivery;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.common.model.jms.SendEmailReply;
import org.example.jms.BaseConsumer;
import org.example.jms.JmsConsumer;
import org.example.notification.service.EmailService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Log4j2
@JmsConsumer(targetType = "SendEmailReply")
@Component
@RequiredArgsConstructor
public class SendEmailConsumer extends BaseConsumer {

    private final ObjectMapper objectMapper;
    private final EmailService emailService;


    @Override
    protected Mono<Void> consume(final Delivery message) {
        try {
            log.debug("[{}] consume: {}", message, message);

            String body = new String(message.getBody());
            SendEmailReply reply;
            reply = objectMapper.readValue(body, SendEmailReply.class);
            emailService.sendHtmlMessage(reply.email());
            return Mono.empty();
        } catch (JsonProcessingException e) {
            log.error("Send email consume error {} ", e.getMessage(), e.getCause());
            return Mono.empty();
        }
    }
}
