package com.bitsco.vks.category.cache;

import com.bitsco.vks.category.entities.*;
import com.bitsco.vks.common.model.Otp;
import com.bitsco.vks.common.response.Token;
import com.bitsco.vks.common.util.StringCommon;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private int database;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        redisStandaloneConfiguration.setPassword(StringCommon.isNullOrBlank(password) ? RedisPassword.none() : RedisPassword.of(password));
        redisStandaloneConfiguration.setDatabase(database);
        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofSeconds(10));// 10s connection timeout

        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());
        return jedisConFactory;
    }

    @Bean
    RedisTemplate<String, Param> paramRedis() {
        final RedisTemplate<String, Param> paramRedis = new RedisTemplate<String, Param>();
        paramRedis.setConnectionFactory(jedisConnectionFactory());
        paramRedis.setValueSerializer(new Jackson2JsonRedisSerializer<Param>(Param.class));
        return paramRedis;
    }

    @Bean
    RedisTemplate<String, User> userRedis() {
        final RedisTemplate<String, User> userRedis = new RedisTemplate<String, User>();
        userRedis.setConnectionFactory(jedisConnectionFactory());
        userRedis.setValueSerializer(new Jackson2JsonRedisSerializer<User>(User.class));
        return userRedis;
    }


    @Bean
    RedisTemplate<String, Province> provinceRedis() {
        final RedisTemplate<String, Province> provinceRedis = new RedisTemplate<String, Province>();
        provinceRedis.setConnectionFactory(jedisConnectionFactory());
        provinceRedis.setValueSerializer(new Jackson2JsonRedisSerializer<Province>(Province.class));
        return provinceRedis;
    }

    @Bean
    RedisTemplate<String, District> districtRedis() {
        final RedisTemplate<String, District> districtRedis = new RedisTemplate<String, District>();
        districtRedis.setConnectionFactory(jedisConnectionFactory());
        districtRedis.setValueSerializer(new Jackson2JsonRedisSerializer<District>(District.class));
        return districtRedis;
    }

    @Bean
    RedisTemplate<String, Commune> communeRedis() {
        final RedisTemplate<String, Commune> communeRedis = new RedisTemplate<String, Commune>();
        communeRedis.setConnectionFactory(jedisConnectionFactory());
        communeRedis.setValueSerializer(new Jackson2JsonRedisSerializer<Commune>(Commune.class));
        return communeRedis;
    }

    @Bean
    RedisTemplate<String, Token> tokenRedis() {
        final RedisTemplate<String, Token> tokenRedis = new RedisTemplate<String, Token>();
        tokenRedis.setConnectionFactory(jedisConnectionFactory());
        tokenRedis.setValueSerializer(new Jackson2JsonRedisSerializer<Token>(Token.class));
        return tokenRedis;
    }

    @Bean
    RedisTemplate<String, Otp> otpRedis() {
        final RedisTemplate<String, Otp> otpRedis = new RedisTemplate<String, Otp>();
        otpRedis.setConnectionFactory(jedisConnectionFactory());
        otpRedis.setValueSerializer(new Jackson2JsonRedisSerializer<Otp>(Otp.class));
        return otpRedis;
    }

}
