package org.example.bot.domain.bot.jms.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.bot.domain.bot.controller.DestinationController;
import org.example.bot.domain.bot.jms.config.BaseConsumer;
import org.example.bot.domain.bot.jms.config.MessageConsumer;
import org.example.common.model.jms.SendInstagramVideoReply;
import org.springframework.stereotype.Component;

@Log4j2
@MessageConsumer(targetType = "SendInstagramVideoReply")
@Component
@RequiredArgsConstructor
public class SendInstagramVideoConsumer extends BaseConsumer {
    private final ObjectMapper objectMapper;
    private final DestinationController destinationController;

    @Override
    protected void consume(final String message) throws JsonProcessingException {
        try {
            SendInstagramVideoReply reply = objectMapper.readValue(message, SendInstagramVideoReply.class);
            log.debug("[{}] consume: {}", queueName, reply);

            destinationController.sendVideo(reply);
        } catch (Exception e) {
            log.error("Error in consume order change msg {} ", e.getMessage());
        }
    }
}
