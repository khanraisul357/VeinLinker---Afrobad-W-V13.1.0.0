package com.afrobad.VeinLinker.config.redisconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.afrobad.VeinLinker.registrationandlogin.cache.drafts.RegistrationDraft;


/*I am telling Spring this class contains instructions for you.
 * what instructions?: which object to manage/ Bean Definitions that should be managed by Spring.
 */
@Configuration
public class RegistrationRedisConfig {
	
	   /*Object returned by this method should be managed by Spring. Meaning object will be stored in IOC Container.
	    * 
	    *RedisConnectionFactory is the component that creates and manages connections between Spring application
	    and the Redis server. 
	    Based on redis properties in application.properties, spring manages object of RedisConnectionFactory
	    and also injects it in (RedisConnectionFactory connectionFactory)
	    */
	   @Bean
	    public RedisTemplate<String, RegistrationDraft> registrationDraftRedisTemplate
	    (RedisConnectionFactory connectionFactory) {

		   //Type of Redis Key is 'String' & type of Redis value 'RegistrationDraft'
	        RedisTemplate<String, RegistrationDraft> redisTemplate =
	                new RedisTemplate<>();

	        //telling RedisTemplate which redis server to connect.
	        redisTemplate.setConnectionFactory(connectionFactory);

	        
	        //redis can only store data in Bytes Format.
	        //Converting type of redis key(String) to Bytes
	        //How?: String --> StringRedisSerializer --> Bytes --> stored in redis
	        redisTemplate.setKeySerializer(new StringRedisSerializer());

	        

	        // Create ObjectMapper with JavaTime support
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.registerModule(new JavaTimeModule());
	        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	        // -------------------------------------------------------------------------
	        // CRITICAL FIX: Activate Polymorphic Type Handling
	        // This forces Jackson to save the actual class type data inside the JSON string
	        // so it won't deserialize back as a LinkedHashMap.
	        // -------------------------------------------------------------------------
	        BasicPolymorphicTypeValidator typeValidator= BasicPolymorphicTypeValidator.builder()
	                .allowIfBaseType(Object.class)
	                .build();
	        
	        objectMapper.activateDefaultTyping(typeValidator, ObjectMapper.DefaultTyping.EVERYTHING);
	        // -------------------------------------------------------------------------
	        
	        // IMPORTANT: use custom mapper
	        GenericJackson2JsonRedisSerializer serializer =
	                new GenericJackson2JsonRedisSerializer(objectMapper);

	        redisTemplate.setValueSerializer(serializer);
	        redisTemplate.setHashValueSerializer(serializer);

	        redisTemplate.afterPropertiesSet();
	        

	        return redisTemplate;
	    }

}
