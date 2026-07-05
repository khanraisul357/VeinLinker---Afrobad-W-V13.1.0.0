package com.afrobad.VeinLinker.registrationandlogin.userverification.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneNumberVerificationRequestDTO {
	
	private String phone;
	private String otp;

}
