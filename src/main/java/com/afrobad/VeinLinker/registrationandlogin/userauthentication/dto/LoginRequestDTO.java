package com.afrobad.VeinLinker.registrationandlogin.userauthentication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
public class LoginRequestDTO {
	
	 @NotBlank(message = "Email or phone number is required")
	 private String identifier;   // Email or phone number
	 
	 @NotBlank(message = "Password is required")
	 private String password;

}
