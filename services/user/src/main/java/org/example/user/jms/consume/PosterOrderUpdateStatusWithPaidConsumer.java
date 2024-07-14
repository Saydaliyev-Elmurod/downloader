package org.example.user.jms.consume;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.user.jms.BaseConsumer;
import org.example.user.jms.MessageConsumer;
import org.springframework.stereotype.Component;

@Log4j2
@MessageConsumer(targetType = "SendEmailReply")
@Component
@RequiredArgsConstructor
public class PosterOrderUpdateStatusWithPaidConsumer extends BaseConsumer {

  private final ObjectMapper objectMapper;

  @Override
  protected void consume(String message) {
    try {
    } catch (Exception e) {
      log.error("Error in [{}] consume msg {}", queueName, e.getMessage(), e);
    }
  }
}
