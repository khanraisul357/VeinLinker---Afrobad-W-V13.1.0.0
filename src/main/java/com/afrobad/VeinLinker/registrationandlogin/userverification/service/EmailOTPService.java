//This class responsible for sending OTP to emails of users

package com.afrobad.VeinLinker.registrationandlogin.userverification.service;

import org.springframework.stereotype.Service;

@Service
public class EmailOTPService {
	
	public String sendOTP(String Email,String emailOTP){
		return emailOTP;
		
	}

}
