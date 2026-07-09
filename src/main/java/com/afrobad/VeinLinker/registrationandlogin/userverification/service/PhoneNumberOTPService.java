//This class responsible for sending OTP to SMS(Phone Number) of users

package com.afrobad.VeinLinker.registrationandlogin.userverification.service;

import org.springframework.stereotype.Service;

@Service
public class PhoneNumberOTPService {
	
	public String sendOTP(String Email,String emailOTP){
		return emailOTP;
		
	}

}
