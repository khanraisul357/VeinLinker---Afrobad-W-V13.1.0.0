package com.afrobad.VeinLinker.registrationandlogin.cache.service;

import java.time.Duration;

import org.mapstruct.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class OTPCacheService {

	    @Autowired
	    private StringRedisTemplate redisTemplateForOTP;
	    
	    private static final Duration OTP_EXPIRATION_MINUTES = Duration.ofMinutes(2);;//2 Minutes


	    public void saveEmailOTP(String email, String otp) {

	        redisTemplateForOTP.opsForValue().set( "EMAIL_OTP:" + email, otp,OTP_EXPIRATION_MINUTES);
	    }

	    public void savePhoneOTP(String phone, String otp) {

	        redisTemplateForOTP.opsForValue().set("PHONE_OTP:" + phone,otp,OTP_EXPIRATION_MINUTES);
	    }

	    public String getEmailOTP(String email) {
	        return (String) redisTemplateForOTP.opsForValue().get("EMAIL_OTP:" + email);
	    }

	    public String getPhoneOTP(String phone) {
	        return (String) redisTemplateForOTP.opsForValue().get("PHONE_OTP:" + phone);
	    }

	    public void deleteEmailOTP(String email) {
	        redisTemplateForOTP.delete("EMAIL_OTP:" + email);
	    }

	    public void deletePhoneOTP(String phone) {
	        redisTemplateForOTP.delete("PHONE_OTP:" + phone);
	    }

}
