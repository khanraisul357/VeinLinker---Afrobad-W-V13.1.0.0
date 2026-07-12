package com.afrobad.VeinLinker.registrationandlogin.userverification.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afrobad.VeinLinker.registrationandlogin.cache.service.OTPCacheService;
import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;
import com.afrobad.VeinLinker.registrationandlogin.users.repository.UsersRepository;
import com.afrobad.VeinLinker.registrationandlogin.userverification.dto.OTPSendedResponseDTO;
import com.afrobad.VeinLinker.registrationandlogin.userverification.entity.OTPVerification;
import com.afrobad.VeinLinker.registrationandlogin.userverification.enums.VerificationType;
import com.afrobad.VeinLinker.registrationandlogin.userverification.repository.OTPVerificationRepository;

import jakarta.transaction.Transactional;

@Service
public class OTPVerificationService {
	@Autowired
	private OTPGenerator otpGenerator;
	
	@Autowired
	private OTPCacheService otpCacheService;
	
	@Autowired
	private OTPVerificationRepository otpVerificationRepository;
	
	@Autowired
	private EmailOTPService emailOTPService;
	
	@Autowired
	private PhoneNumberOTPService phoneOTPService;
	
	@Autowired
	private UsersRepository usersRepository;
	
	public OTPSendedResponseDTO startVerification(String userId) {
		
		//Fetching object of user entity stored in MySQL through reference of userID passed in request.
		Users user = usersRepository.findByPublicUserId(userId)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    // Generate OTPs
	    String emailOTP = otpGenerator.generateOTP();
	    String phoneOTP = otpGenerator.generateOTP();


	     //----------------------------------------------------------------------------------
	     //This below Part used to store OTP inside Redis
	     //----------------------------------------------------------------------------------
	    
	    //Save OTP in redis
	    otpCacheService.saveEmailOTP(user.getEmail(), emailOTP);
	    otpCacheService.savePhoneOTP(user.getPhone(), phoneOTP);
	  
	    
	     //----------------------------------------------------------------------------------
	     //This below Part used to create & store OTPVerification entity inside MySQL instead of redis
	     //----------------------------------------------------------------------------------
	    
	    // Create OTPVerification entity only for the first time when it is empty for the passed publicuserId.
	    OTPVerification otpVerification = otpVerificationRepository
	            .findByUser(user)
	            .orElse(null);

	    if (otpVerification == null) {

	        otpVerification = OTPVerification.builder()
	                .user(user)
	                .isEmailVerified(false)
	                .isNumberVerified(false)
	                .expiresAt(LocalDateTime.now().plusMinutes(5))
	                .build();

	        otpVerificationRepository.save(otpVerification);
	    }
	    
	    //----------------------------------------------------------------------------------
	    //This below Part used to Send OTP to Email & SMS(number) of users
	    //----------------------------------------------------------------------------------
	    emailOTPService.sendOTP(user.getEmail(), emailOTP);
	    phoneOTPService.sendOTP(user.getPhone(), phoneOTP);
	    
	    //creating OTPSendedResponseDTO object
	    OTPSendedResponseDTO OTPSendedResponse=OTPSendedResponseDTO.builder()
	    		.emailOTP(emailOTP)
	    		.phoneNumberOTP(phoneOTP)
	    		.message("OTP Sent to Email & Phone Number")
	    		.build();
	    
	    //return the object of response DTO
	    return OTPSendedResponse;
	    
	}
	
	
	//----------------------------------------------------------------------------------
    //Verify OTP: This below Part used to verify OTP--> whether otp submitted by users match with otp stored in redis
    //----------------------------------------------------------------------------------
	@Transactional
	public void verifyOTP(String identifier,String submittedOTP,VerificationType verificationType) {

	    Users user;
	    String storedOTP;

	    // ------------------------------------------------------------
	    // Fetch user and OTP based on verification type
	    // ------------------------------------------------------------

	    if (verificationType == VerificationType.EMAIL) {

	        user = usersRepository.findByEmail(identifier)
	                .orElseThrow(() -> new IllegalArgumentException("User not found."));

	        storedOTP = otpCacheService.getEmailOTP(identifier);

	    } else {

	        user = usersRepository.findByPhone(identifier)
	                .orElseThrow(() -> new IllegalArgumentException("User not found."));

	        storedOTP = otpCacheService.getPhoneOTP(identifier);
	    }

	    // ------------------------------------------------------------
	    // Validate OTP
	    // ------------------------------------------------------------

	    // -------------------------------------------------------------------------
	    // Validate OTP existence (checks whether OTP has expired)
	    // -------------------------------------------------------------------------
	    if (storedOTP == null) {
	        throw new IllegalStateException("OTP has expired.");
	    }

	    // -------------------------------------------------------------------------
	    // Validate OTP correctness (checks whether user entered the correct OTP)
	    // -------------------------------------------------------------------------
	    if (!storedOTP.equals(submittedOTP)) {
	        throw new IllegalArgumentException("Invalid OTP.");
	    }
	    

	    // -------------------------------------------------------------------------
	    // Fetch OTP verification record
	    // -------------------------------------------------------------------------

	    OTPVerification otpVerificationEntity = otpVerificationRepository.findByUser(user)
	            .orElseThrow(() -> new IllegalStateException("Verification record not found."));

	    // ------------------------------------------------------------
	    // Update OTPVerification record
	    // ------------------------------------------------------------
	    if (verificationType == VerificationType.EMAIL) {

	        otpVerificationEntity.setEmailVerified(true);

	        otpCacheService.deleteEmailOTP(identifier);

	    } else {

	    	otpVerificationEntity.setNumberVerified(true);

	        otpCacheService.deletePhoneOTP(identifier);
	    }
	    
        //Save updated OTPVerfication Record
	    otpVerificationRepository.save(otpVerificationEntity);
	}

}
