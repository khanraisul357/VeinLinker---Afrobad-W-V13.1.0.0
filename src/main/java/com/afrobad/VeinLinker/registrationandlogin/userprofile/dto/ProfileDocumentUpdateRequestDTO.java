package com.afrobad.VeinLinker.registrationandlogin.userprofile.dto;

import org.springframework.web.multipart.MultipartFile;

import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.enums.VerificationDocumentType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDocumentUpdateRequestDTO {


	    private VerificationDocumentType documentType;

	    private MultipartFile frontImage;

	    private MultipartFile backImage;
    
}