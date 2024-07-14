package org.example.notification.jms.consume;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.notification.jms.BaseConsumer;
import org.example.notification.jms.MessageConsumer;
import org.example.notification.jms.model.SendEmailReply;
import org.example.notification.service.EmailService;
import org.springframework.stereotype.Component;

@Log4j2
@MessageConsumer(targetType = "SendEmailReply")
@Component
@RequiredArgsConstructor
public class PosterOrderUpdateStatusWithPaidConsumer extends BaseConsumer {

    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    @Override
    protected void consume(String message) {
        try {
            log.debug("Consume message {} from queue [{}]", message, queueName);
            SendEmailReply reply = objectMapper.readValue(message, SendEmailReply.class);
            emailService.sendHtmlMessage(reply.email(), "Hello", "hello");
        } catch (Exception e) {
            log.error("Error in [{}] consume msg {}", queueName, e.getMessage(), e);
        }
    }
}
