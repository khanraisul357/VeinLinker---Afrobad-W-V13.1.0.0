package com.afrobad.VeinLinker.registrationandlogin.userauthentication.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
public class LoginResponseDTO {
	
	 
	 private String message;   // Email or phone number
	 
	
	 private String jwt;

}
