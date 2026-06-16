package com.afrobad.VeinLinker.registrationandlogin.users.dto;

import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.enums.VerificationDocumentType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationForm3Request {
	
	@NotBlank(message = "Email session identifier is required")
    @Email(message = "Invalid email formatting")
    private String email;//email as token

	@NotBlank(message= "Cannot be empty")
    @NotNull(message = "Document type is required")
    private VerificationDocumentType documentType;

}

