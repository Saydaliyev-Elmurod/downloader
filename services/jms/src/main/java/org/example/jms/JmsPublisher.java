package org.example.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.common.exception.exp.JsonParsingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

import java.nio.charset.StandardCharsets;

@Component
public class JmsPublisher {

    private static final Logger LOGGER = LogManager.getLogger();

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    private final ObjectMapper objectMapper;
    private final Sender rabbitSender;

    public JmsPublisher(final ObjectMapper objectMapper, final Sender rabbitSender) {
        this.objectMapper = objectMapper;
        this.rabbitSender = rabbitSender;
    }

    public <T> Mono<Void> publishSynchronized(final T message) {
        final String routingKey = message.getClass().getSimpleName();
        final String json;

        try {
            json = objectMapper.writeValueAsString(message);
            LOGGER.info("Published msg body: {}", json);
        } catch (final JsonProcessingException e) {
            LOGGER.error("Failed to convert object to JSON string: {}", e.getMessage());
            return Mono.error(new JsonParsingException("Failed to write into string", e));
        }

        final byte[] messageBody = json.getBytes(StandardCharsets.UTF_8);
        LOGGER.debug("Publishing: {} routingKey: {} exchange: {}", message, routingKey, exchange);

        return rabbitSender.send(Mono.just(new OutboundMessage(exchange, routingKey, messageBody)))
                .doOnSuccess(ignored -> LOGGER.info("✅ Message successfully published to RabbitMQ"))
                .doOnError(error -> LOGGER.error("❌ Failed to send message to RabbitMQ: {}", error.getMessage()))
                .doOnTerminate(() -> LOGGER.info("🔥 RabbitMQ send operation completed"))
                .onErrorResume(error -> Mono.empty())
                .log();

    }

    public <T> void publish(final T message) {
        final String routingKey = message.getClass().getSimpleName();
        String json;

        try {
            json = objectMapper.writeValueAsString(message);
            LOGGER.info("Published msg body: [{}]", json);
        } catch (final JsonProcessingException e) {
            LOGGER.error("Failed to convert object to JSON string: [{}]", e.getMessage());
            return;
//            return Mono.error(new JsonParsingException("Failed to write into string", e));
        }

        final byte[] messageBody = json.getBytes(StandardCharsets.UTF_8);
        LOGGER.debug("Publishing: [{}] routingKey: [{}] exchange: [{}]", message, routingKey, exchange);

        rabbitSender.send(Mono.just(new OutboundMessage(exchange, routingKey, messageBody)))
                .doOnSuccess(ignored -> LOGGER.info("✅ Message successfully published to RabbitMQ"))
                .doOnError(error -> LOGGER.error("❌ Failed to send message to RabbitMQ: {}", error.getMessage()))
                .doOnTerminate(() -> LOGGER.info("🔥 RabbitMQ send operation completed"))
                .onErrorResume(error -> Mono.empty())
                .log().block();

    }


}
