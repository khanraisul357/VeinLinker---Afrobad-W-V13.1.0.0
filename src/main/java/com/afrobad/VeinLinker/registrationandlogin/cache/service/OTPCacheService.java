package com.afrobad.VeinLinker.registrationandlogin.cache.service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.mapstruct.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class OTPCacheService {

	    @Autowired
	    private StringRedisTemplate redisTemplateForOTP;
	    
	    private static final Duration OTP_EXPIRATION_MINUTES_FOR_EMAIL_OTP = Duration.ofMinutes(2);//2 Minutes
	    private static final Duration OTP_EXPIRATION_MINUTES_FOR_PHONENUMBER_OTP = Duration.ofMinutes(2);//2 Minutes


	    public void saveEmailOTP(String email, String otp) {

	        redisTemplateForOTP.opsForValue().set( "EMAIL_OTP:" + email, otp,OTP_EXPIRATION_MINUTES_FOR_EMAIL_OTP);
	    }

	    public void savePhoneOTP(String phoneNumber, String otp) {

	        redisTemplateForOTP.opsForValue().set("PHONE_OTP:" + phoneNumber,otp,OTP_EXPIRATION_MINUTES_FOR_PHONENUMBER_OTP);
	    }

	    public String getEmailOTP(String email) {
	        return (String) redisTemplateForOTP.opsForValue().get("EMAIL_OTP:" + email);
	    }

	    public String getPhoneOTP(String phoneNumber) {
	        return (String) redisTemplateForOTP.opsForValue().get("PHONE_OTP:" + phoneNumber);
	    }

	    public void deleteEmailOTP(String email) {
	        redisTemplateForOTP.delete("EMAIL_OTP:" + email);
	    }

	    public void deletePhoneOTP(String phoneNumber) {
	        redisTemplateForOTP.delete("PHONE_OTP:" + phoneNumber);
	    }

	    
	    //Methods to generate time left of email otp expiration
	    public Long getTimeLeftForEmailOTP(String email) {
	        Long ttl= redisTemplateForOTP.getExpire(
	                "EMAIL_OTP:" + email,
	                TimeUnit.SECONDS
	        );
	        
	        return ttl != null && ttl > 0 ? ttl : 0L;//This prevents returning -1 or -2 to the client.
	    }
	    
	  //Methods to generate time left of phone number otp expiration
	    public Long getTimeLeftForPhoneNumberOTP(String phoneNumber) {
	        Long ttl= redisTemplateForOTP.getExpire(
	                "PHONE_OTP:" + phoneNumber,
	                TimeUnit.SECONDS
	        );
	        
	        return ttl != null && ttl > 0 ? ttl : 0L;//This prevents returning -1 or -2 to the client.
	    }

}
