//package org.example.notification.jms.consume.notworked;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import jakarta.annotation.PostConstruct;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.example.notification.jms.MessageConsumer;
//import org.example.notification.jms.RabbitProperties;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.nio.charset.StandardCharsets;
//
//public abstract class BaseConsumer {
//
//    private static final Logger LOGGER = LogManager.getLogger();
//
//    @Autowired
//    private ConnectionFactory connectionFactory;
//
//    @Autowired
//    private RabbitProperties rabbitProperties;
//
//    @Autowired
//    private AmqpAdmin amqpAdmin;
//
//    @Value("${spring.application.name}")
//    private String applicationName;
//
//    public String queueName;
//
//    protected abstract void consume(String message) throws JsonProcessingException;
//
//    private void createListenerContainer(String queueName) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(queueName);
//        container.setMessageListener(
//                message -> {
//                    try {
//                        String messageBody =
//                                new String(
//                                        message.getBody(),
//                                        StandardCharsets.UTF_8); // SerializationUtils.deserialize(message.getBody());
//                        consume(messageBody);
//                    } catch (JsonProcessingException e) {
//                        LOGGER.error("Failed to consume JMS message. Error:", e);
//                    }
//                });
//        container.start();
//    }
//
//    @PostConstruct
//    private void init() {
//        final String targetType = this.getClass().getAnnotation(MessageConsumer.class).targetType();
//        queueName = String.format("%s.%s", applicationName, targetType);
//
//        final Exchange exchange =
//                ExchangeBuilder.directExchange(rabbitProperties.exchange()).durable(true).build();
//
//        amqpAdmin.declareExchange(exchange);
//
//        final Queue queue = QueueBuilder.durable(queueName).build();
//        amqpAdmin.declareQueue(queue);
//
//        final Binding binding = BindingBuilder.bind(queue).to(exchange).with(targetType).noargs();
//        amqpAdmin.declareBinding(binding);
//
//        LOGGER.info("Consumer for queue: [{}] initialized", queueName);
//
//        createListenerContainer(queueName);
//    }
//}
