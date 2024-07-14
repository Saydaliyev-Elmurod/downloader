package org.example.user.jms;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

@ConfigurationProperties("spring.rabbitmq")
public record RabbitProperties(
    String host, Integer port, String username, String password, String exchange) {

  public RabbitProperties {
    Assert.hasText(host, "RabbitMQ host cannot be null or blank");
    Assert.notNull(port, "RabbitMQ port cannot be null");
    Assert.hasText(username, "RabbitMQ username cannot be null or blank");
    Assert.hasText(password, "RabbitMQ password cannot be null or blank");
    Assert.hasText(exchange, "RabbitMQ exchange cannot be null or blank");
  }
}
