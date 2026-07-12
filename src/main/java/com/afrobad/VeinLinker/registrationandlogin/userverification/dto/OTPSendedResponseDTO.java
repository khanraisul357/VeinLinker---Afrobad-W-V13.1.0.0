package com.afrobad.VeinLinker.registrationandlogin.userverification.dto;
import lombok.*;

@Getter
@Setter
@Builder
public class OTPSendedResponseDTO {
	
	String emailOTP;
	String phoneNumberOTP;
	String message;

}
