package org.example.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

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

  public <T> Mono<Void> publish(final T message) {
    final String routingKey = message.getClass().getSimpleName();
    final String json;
    try {
      json = objectMapper.writeValueAsString(message);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Failed to convert object to JSON string. Error:", e);

      throw new JsonParsingException("Failed to write into string", e);
    }

    final byte[] messageBody = json.getBytes(StandardCharsets.UTF_8);
    LOGGER.debug("Publish:{} routingKey {} exchange {} ",message,routingKey,exchange);
    return rabbitSender.send(Mono.just(new OutboundMessage(exchange, routingKey, messageBody))).log();
  }
}
