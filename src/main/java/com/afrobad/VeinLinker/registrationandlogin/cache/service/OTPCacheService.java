package com.afrobad.VeinLinker.registrationandlogin.cache.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class OTPCacheService {

	    @Autowired
	    private RedisTemplate<String, Object> redisTemplate;

	    private static final long OTP_EXPIRATION_MINUTES = 5;//After 5 minutes OTP will expire

	    public void saveEmailOTP(String email, String otp) {

	        redisTemplate.opsForValue().set( "EMAIL_OTP:" + email, otp,Duration.ofMinutes(OTP_EXPIRATION_MINUTES));
	    }

	    public void savePhoneOTP(String phone, String otp) {

	        redisTemplate.opsForValue().set("PHONE_OTP:" + phone,otp,Duration.ofMinutes(OTP_EXPIRATION_MINUTES));
	    }

	    public String getEmailOTP(String email) {
	        return (String) redisTemplate.opsForValue().get("EMAIL_OTP:" + email);
	    }

	    public String getPhoneOTP(String phone) {
	        return (String) redisTemplate.opsForValue().get("PHONE_OTP:" + phone);
	    }

	    public void deleteEmailOTP(String email) {
	        redisTemplate.delete("EMAIL_OTP:" + email);
	    }

	    public void deletePhoneOTP(String phone) {
	        redisTemplate.delete("PHONE_OTP:" + phone);
	    }

}
