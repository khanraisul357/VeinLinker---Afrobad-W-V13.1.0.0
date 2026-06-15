//This layer is responsible for talking to redis, & save/update/delete/read drafts to cache memory.

package com.afrobad.VeinLinker.registrationandlogin.cache.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.afrobad.VeinLinker.registrationandlogin.cache.drafts.RegistrationDraft;

import java.time.Duration;

@Service
public class RegistrationCacheService {

	//Injecting object of RedisTemplate 
    @Autowired 
    private RedisTemplate<String, RegistrationDraft> redisTemplate;

    // Use a unified namespace prefix to keep keys separated inside Redis
    private static final String REDIS_PREFIX = "RegistrationDraftCache:"; //prefix of redis key, like "RegistrationDraftCache: 1"
    private static final Duration CACHE_TIMEOUT_MINUTES = Duration.ofMinutes(30);;//30 Minutes

    /**
     * Saves or updates the registration draft in Redis using the email as the lookup key.
     */
    public void saveDraft(String email, RegistrationDraft draft) {
    	
    	//if email = "  User@Gmail.com " 
        //email.toLowerCase().trim() → "user@gmail.com"
    	//Final Redis Key becomes → RegistrationDraftCache:user@gmail.com
        String cacheKey = REDIS_PREFIX + email.toLowerCase().trim();
        
        
        redisTemplate.opsForValue().set(cacheKey, draft, CACHE_TIMEOUT_MINUTES);
    }

    /**
     * Retrieves the current registration progress from Redis.
     */
    public RegistrationDraft getDraft(String email) {
        String cacheKey = REDIS_PREFIX + email.toLowerCase().trim();
        return (RegistrationDraft) redisTemplate.opsForValue().get(cacheKey);
    }
//
//    /**
//     * Clears out the Redis cache once Form 3 is successfully saved to MySQL.
//     */
//    public void deleteDraft(String email) {
//        String cacheKey = REDIS_PREFIX + email.toLowerCase().trim();
//        redisTemplate.delete(cacheKey);
//    }
}