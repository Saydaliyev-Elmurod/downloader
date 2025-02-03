package org.example.jms;

import com.rabbitmq.client.Delivery;

import java.io.IOException;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.common.exception.exp.JsonParsingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.AcknowledgableDelivery;
import reactor.rabbitmq.Receiver;

public abstract class BaseConsumer {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private Receiver receiver;

    @Value("${spring.application.name}")
    private String applicationName;

    protected abstract Mono<Void> consume(Delivery message) throws IOException;

    @PostConstruct
    private void init() {
        final String targetType = this.getClass().getAnnotation(JmsConsumer.class).targetType();
        final String queueName = String.format("%s.%s", applicationName, targetType);

        LOGGER.info(
                "Consumer for [{}] queue initialized on [{}] application", queueName, applicationName);

        final Flux<AcknowledgableDelivery> delivery = receiver.consumeManualAck(queueName);
        delivery.subscribe(
                message -> {
                    try {
                        consume(message).subscribe(System.out::println);
                        message.ack();
                    } catch (final IOException e) {
                        LOGGER.error("Failed to consume JMS message. Error:", e);
                        throw new JsonParsingException("Failed to consume JMS message", e);
                    }
                });
    }
}
