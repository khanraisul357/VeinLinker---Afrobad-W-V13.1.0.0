package com.afrobad.VeinLinker.registrationandlogin.users.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationForm1Response {

	    private String email;
	    private String draftId;
	    private boolean success;
	    private String message;
	    private int currentStep;
	   
 
}
