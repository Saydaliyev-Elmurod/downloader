//package org.example.user.jms;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.example.user.exception.JsonParsingException;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.StandardCharsets;
//
//@Log4j2
//@Component
//@RequiredArgsConstructor
//public class ReactiveMessagePublisher {
//
//  private final ObjectMapper objectMapper;
//  private final RabbitProperties rabbitProperties;
//  private final RabbitTemplate rabbitTemplate;
//
//  public <T> Mono<Void> publish(final T message) {
//    return Mono.fromCallable(() -> {
//      final String routingKey = message.getClass().getSimpleName();
//      final String json;
//      try {
//        json = objectMapper.writeValueAsString(message);
//      } catch (final JsonProcessingException e) {
//        log.error("Failed to convert object to JSON string. Error:", e);
//        throw new JsonParsingException("Failed to write into string");
//      }
//
//      final byte[] messageBody = json.getBytes(StandardCharsets.UTF_8);
//
//      rabbitTemplate.convertAndSend(rabbitProperties.exchange(), routingKey, messageBody);
//      log.debug("Published message to [{}] {}", routingKey, json);
//      return null;
//    });
//  }
//}
