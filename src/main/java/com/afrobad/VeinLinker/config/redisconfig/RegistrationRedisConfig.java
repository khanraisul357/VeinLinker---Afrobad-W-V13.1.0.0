package com.afrobad.VeinLinker.registrationandlogin.cache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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

	        ////Converting type of redis value(Registration Draft/object type) of object type to Bytes
	        /// How?: Registration Draft/object type --> JSON --> Bytes --> stored in redis
	        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

	        return redisTemplate;
	    }

}
