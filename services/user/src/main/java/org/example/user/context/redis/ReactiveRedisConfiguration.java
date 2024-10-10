package org.example.user.context.redis;

import org.example.common.model.UserResponse;
import org.example.user.model.SignUpRedisModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.UUID;

@Configuration
public class ReactiveRedisConfiguration {

    @Bean
    public ReactiveRedisTemplate<String, SignUpRedisModel> reactiveRedisTemplate(
            ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<SignUpRedisModel> valueSerializer = new Jackson2JsonRedisSerializer<>(SignUpRedisModel.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, SignUpRedisModel> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, SignUpRedisModel> context =
                builder.value(valueSerializer).build();
        return new ReactiveRedisTemplate<>(factory, context);
    }

    @Bean
    public ReactiveRedisTemplate<String, UserResponse> reactiveUserRedisTemplate(
            ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<UserResponse> valueSerializer = new Jackson2JsonRedisSerializer<>(UserResponse.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, UserResponse> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, UserResponse> context =
                builder.value(valueSerializer).build();
        return new ReactiveRedisTemplate<>(factory, context);
    }


}
