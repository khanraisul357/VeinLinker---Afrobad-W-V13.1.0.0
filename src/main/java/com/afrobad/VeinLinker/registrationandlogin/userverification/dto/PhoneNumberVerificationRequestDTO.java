package com.afrobad.VeinLinker.registrationandlogin.userverification.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneNumberVerificationRequestDTO {
	
	private String phoneNumber;
	private String otp;

}
