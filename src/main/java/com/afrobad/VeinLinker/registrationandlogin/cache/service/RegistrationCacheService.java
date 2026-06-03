//This layer is responsible for talking to redis, & save/update/delete/read drafts to cache memory.

package com.afrobad.VeinLinker.registrationandlogin.cache.service;

package com.afrobad.VeinLinker.registrationandlogin.cache.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.afrobad.VeinLinker.registrationandlogin.cache.drafts.RegistrationDraft;

import java.util.concurrent.TimeUnit;

@Service
public class RegistrationCacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // Use a unified namespace prefix to keep keys separated inside Redis
    private static final String REDIS_PREFIX = "RegistrationDraftCache:";
    private static final long CACHE_TIMEOUT_MINUTES = 1800000;

    /**
     * Saves or updates the registration draft in Redis using the email as the lookup key.
     */
    public void saveDraft(String email, RegistrationDraft draft) {
        String cacheKey = REDIS_PREFIX + email.toLowerCase().trim();
        
        // Stores the object as JSON and sets it to expire automatically in 60 minutes
        redisTemplate.opsForValue().set(cacheKey, draft, CACHE_TIMEOUT_MINUTES, TimeUnit.MINUTES);
    }

    /**
     * Retrieves the current registration progress from Redis.
     */
    public RegistrationDraft getDraft(String email) {
        String cacheKey = REDIS_PREFIX + email.toLowerCase().trim();
        return (RegistrationDraft) redisTemplate.opsForValue().get(cacheKey);
    }

    /**
     * Clears out the Redis cache once Form 3 is successfully saved to MySQL.
     */
    public void deleteDraft(String email) {
        String cacheKey = REDIS_PREFIX + email.toLowerCase().trim();
        redisTemplate.delete(cacheKey);
    }
}