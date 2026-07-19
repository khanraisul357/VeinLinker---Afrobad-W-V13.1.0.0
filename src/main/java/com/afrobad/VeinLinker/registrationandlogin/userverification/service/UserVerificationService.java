package com.afrobad.VeinLinker.registrationandlogin.userverification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;
import com.afrobad.VeinLinker.registrationandlogin.users.enums.UserStatus;
import com.afrobad.VeinLinker.registrationandlogin.users.repository.UsersRepository;
import com.afrobad.VeinLinker.registrationandlogin.userverification.entity.OTPVerification;
import com.afrobad.VeinLinker.registrationandlogin.userverification.entity.UserDocumentVerification;
import com.afrobad.VeinLinker.registrationandlogin.userverification.enums.VerificationDocumentStatus;
import com.afrobad.VeinLinker.registrationandlogin.userverification.repository.OTPVerificationRepository;
import com.afrobad.VeinLinker.registrationandlogin.userverification.repository.UserDocumentVerificationRepository;

@Service
public class UserVerificationService {
	
	@Autowired 
	private UsersRepository userRepository;
	
	@Autowired
	private OTPVerificationRepository otpVerificationRepository;
	
	@Autowired
	private UserDocumentVerificationRepository userDocumentVeriifcationRepository; 
	
	public String verifyUser(Users user) {
		
		    
		OTPVerification otpVerification = otpVerificationRepository.findByUser(user)
				.orElseThrow(() -> new IllegalArgumentException("OTPVeerification record not found for this user"));

		UserDocumentVerification documentVerification = userDocumentVeriifcationRepository .findByUser(user)
				.orElseThrow(() -> new IllegalArgumentException("OTPVeerification record not found for this user"));
		
		
		if (!otpVerification.isEmailVerified() && !otpVerification.isNumberVerified()) {
	        return "Email and phone number are not verified.";
	    }

	    if (!otpVerification.isEmailVerified()) {
	        return "Email is not verified.";
	    }

	    if (!otpVerification.isNumberVerified()) {
	        return "Phone number is not verified.";
	    }

	    if (documentVerification.getDocumentVerificationStatus() == VerificationDocumentStatus.PENDING) {
	        return "Your document is under review.";
	    }
	     

		// Document rejected
	    if (documentVerification.getDocumentVerificationStatus() == VerificationDocumentStatus.REJECTED) {
	        user.setIsVerified(UserStatus.REJECTED);
	        userRepository.save(user);
	        return "Documents rejected. Please re-upload.";
	    }    

		if((otpVerification.isEmailVerified()) && (otpVerification.isNumberVerified()) && documentVerification.getDocumentVerificationStatus()==VerificationDocumentStatus.VERIFIED) {
			
			
			user.setIsVerified(UserStatus.VERIFIED);
			userRepository.save(user);
			
			return "Your account is verified!";
		}
		
		 	 

	    // Still waiting for remaining verification(s)
	    return "User verification is pending.";
		    

		
		
		
	}

}
