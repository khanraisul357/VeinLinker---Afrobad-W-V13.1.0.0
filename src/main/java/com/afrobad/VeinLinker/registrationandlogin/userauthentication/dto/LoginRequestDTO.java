package com.afrobad.VeinLinker.registrationandlogin.userauthentication.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class LoginRequestDTO {
	
	 private String identifier;   // Email or phone number
	 private String password;

}
