package org.example.exam.context.redis;

import org.example.common.model.UserResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.*;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class ReactiveRedisConfiguration {

    //    @Bean
//    public ReactiveRedisTemplate<String, UserResponse> reactiveRedisTemplate(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
//        Jackson2JsonRedisSerializer<UserResponse> serializer = new Jackson2JsonRedisSerializer<>(UserResponse.class);
//        RedisSerializationContext.RedisSerializationContextBuilder<String, UserResponse> builder =
//                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
//
//        RedisSerializationContext<String, UserResponse> context = builder.value(serializer).hashValue(serializer)
//                .hashKey(new StringRedisSerializer()).build();
//
//        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, context);
//    }
    @Bean
    public ReactiveRedisTemplate<String, UserResponse> reactiveRedisTemplate(
            ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<UserResponse> valueSerializer = new Jackson2JsonRedisSerializer<>(UserResponse.class);
//                new Jackson2JsonRedisSerializer<>(UserResponse.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, UserResponse> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, UserResponse> context =
                builder.value(valueSerializer).build();
        return new ReactiveRedisTemplate<>(factory, context);
    }

//    @Bean
//    ReactiveRedisTemplate<String, UserResponse> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
//        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
//        StringRedisSerializer stringRedisSerializer = StringRedisSerializer.UTF_8;
//        GenericToStringSerializer<UserResponse> longToStringSerializer = new GenericToStringSerializer<>(UserResponse.class);
//        return new ReactiveRedisTemplate<>(factory,
//                RedisSerializationContext.<String, UserResponse>newSerializationContext(jdkSerializationRedisSerializer)
//                        .key(stringRedisSerializer).value(longToStringSerializer).build());
//    }
}
