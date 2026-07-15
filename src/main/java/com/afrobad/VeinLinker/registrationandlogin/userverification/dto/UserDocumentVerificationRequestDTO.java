package com.afrobad.VeinLinker.registrationandlogin.userverification.dto;

import lombok.*;

import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.enums.VerificationDocumentType;
import com.afrobad.VeinLinker.registrationandlogin.userverification.enums.VerificationDocumentStatus;

@Getter
@Setter
public class UserDocumentVerificationRequestDTO {
	
	    private VerificationDocumentType verificationDocumentType;

	    private VerificationDocumentStatus documentVerificationStatus;

	    private String rejectionReason;

	

}
