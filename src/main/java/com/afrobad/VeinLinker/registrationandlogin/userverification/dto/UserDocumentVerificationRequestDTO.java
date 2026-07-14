package com.afrobad.VeinLinker.registrationandlogin.userverification.dto;

import lombok.*;

import com.afrobad.VeinLinker.registrationandlogin.userverification.enums.VerificationDocumentStatus;

@Getter
@Setter
public class UserDocumentVerificationRequestDTO {
	

	    private VerificationDocumentStatus status;

	    private String rejectionReason;

	

}
