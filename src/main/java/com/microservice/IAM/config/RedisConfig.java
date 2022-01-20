package com.microservice.IAM.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.microservice.IAM.models.TokensInfo;

@Configuration
public class RedisConfig {
	
	@Bean
	JedisConnectionFactory jedisConnectionFactory(){
	    return new JedisConnectionFactory();
	  }

	@Bean
	RedisTemplate<String, TokensInfo> redisTemplate(){
	    RedisTemplate<String,TokensInfo> redisTemplate = new RedisTemplate<String, TokensInfo>();
	    redisTemplate.setConnectionFactory(jedisConnectionFactory());
	    return redisTemplate;
	  }
	
}