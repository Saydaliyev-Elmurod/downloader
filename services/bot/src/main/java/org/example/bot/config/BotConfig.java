package org.example.bot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("classpath:application.yaml")
public class BotConfig {

    @Value("${telegram.username}")
    String botUserName;

    @Value("${telegram.token}")
    String token;

}