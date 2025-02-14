package org.example.bot.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.bot.config.RabbitProperties;
import org.example.common.exception.exp.JsonParsingException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Log4j2
@Component
@RequiredArgsConstructor
public class MessagePublisher {

    private static final Logger LOGGER = LogManager.getLogger();

    private final ObjectMapper objectMapper;
    private final RabbitProperties rabbitProperties;
    private final RabbitTemplate rabbitTemplate;

    public <T> void publish(final T message) {
        final String routingKey = message.getClass().getSimpleName();
        final String json;
        try {
            json = objectMapper.writeValueAsString(message);
        } catch (final JsonProcessingException e) {
            LOGGER.error("Failed to convert object to JSON string. Error:", e);
            throw new
                    JsonParsingException("Failed to write into string", e);
        }

        final byte[] messageBody =
                json.getBytes(StandardCharsets.UTF_8); // SerializationUtils.serialize(json);

        rabbitTemplate.convertAndSend(rabbitProperties.exchange(), routingKey, messageBody);
        log.debug("Published message to [{}] [{}]", routingKey, json);
    }
}
