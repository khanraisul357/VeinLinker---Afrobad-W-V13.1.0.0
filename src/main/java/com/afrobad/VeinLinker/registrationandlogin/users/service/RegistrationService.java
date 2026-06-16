// Connects  to Redis, MySQL, & maps to User, Files, and UserVerification entities, and saves them using their respective repositories.

package com.afrobad.VeinLinker.registrationandlogin.users.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.afrobad.VeinLinker.registrationandlogin.cache.drafts.RegistrationDraft;
import com.afrobad.VeinLinker.registrationandlogin.cache.drafts.UploadedFileDraft;
import com.afrobad.VeinLinker.registrationandlogin.cache.enums.RegistrationStatus;
import com.afrobad.VeinLinker.registrationandlogin.cache.service.RegistrationCacheService;
import com.afrobad.VeinLinker.registrationandlogin.mapper.RegistrationMapper;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.enums.FileType;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.enums.Format;
import com.afrobad.VeinLinker.registrationandlogin.users.dto.*;
import com.afrobad.VeinLinker.registrationandlogin.users.enums.Role;
import com.afrobad.VeinLinker.registrationandlogin.users.repository.UsersRepository;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
@Service
public class RegistrationService {
	
	@Autowired
	private UsersRepository repository;
	
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private RegistrationMapper mapper;
	
	@Autowired
    private RegistrationCacheService cacheService;
	
	// Isolated Business Validation Method
    private void validateForm1(RegistrationForm1Request request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered!");
        }

        if (repository.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentException("Phone number is already registered!");
        }
        
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match!");
        }
        
    }
    
    //Method to get format(jpg,jpeg,png,pdf) of the uploaded file
    private Format getFormat(MultipartFile file) {

        String originalName = file.getOriginalFilename();

        if (originalName == null) {
            throw new IllegalArgumentException("File name is missing.");
        }

        String extension =
                originalName.substring(
                        originalName.lastIndexOf('.') + 1
                ).toUpperCase();

        return Format.valueOf(extension);
    }
	
	public RegistrationForm1Response processForm1(RegistrationForm1Request request) {
		
		//Run the business validations first(whether users email or phone number exists in DB)
        validateForm1(request);
        
        //Hash the password immediately
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        //map matching fields of RegistrationForm1Response to RegistrationDraft
        RegistrationDraft draft = mapper.form1RequestDtoToDraft(request);
        
        //Set hashedPassword to RegistrationDraft.PasswordHashFormat
        draft.setPasswordHashFormat(hashedPassword);
        
        //By default all users role is set to user
        draft.setRole(Role.USER);
        
        // Initialize tracking values to System Fields in RegistrationDraft
        draft.setDraftId("REG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        draft.setCurrentStep(1);
        draft.setRegistrationStatus(RegistrationStatus.IN_PROGRESS);
        
        LocalDateTime now = LocalDateTime.now();
        draft.setCreatedAt(now);
        draft.setUpdatedAt(now);
        
        

        /*Calling method RegistrationCacheService.saveDraft() to save form 1 data temporarily to Redis 
        using email as the unique key
        */
        cacheService.saveDraft(request.getEmail(), draft);
		
        
        //Convert RegistrationDraft into RegistrationForm1Response & Return to controller
        return RegistrationForm1Response.builder()
                .email(draft.getEmail())
                .draftId(draft.getDraftId())
                .success(true)
                .message("Form 1 draft saved successfully.")
                .currentStep(draft.getCurrentStep())
                .build();
		
		
	}
	
	
	public RegistrationForm2Response processForm2(RegistrationForm2Request request) {
		
	    //Fetch the incomplete draft from Redis through email as key.
	    RegistrationDraft existingDraft = cacheService.getDraft(request.getEmail());
	    if (existingDraft == null) {
	        throw new IllegalStateException("Registration session expired or not found.");
	    }

	    //MapStruct updates ONLY the fields present in Form 2 Request.
	    //Your Form 1 data (email, phone, passwordHash) remains completely untouched and safe!
	    mapper.updateDraftWithForm2(request, existingDraft);

	    //Update the step counter metadata manually
	    existingDraft.setCurrentStep(2);
	    existingDraft.setUpdatedAt(java.time.LocalDateTime.now());

	    //Save the updated combined draft back to Redis
	    cacheService.saveDraft(request.getEmail(), existingDraft);
	    
	 
	    //Convert RegistrationDraft into RegistrationForm1Response & Return to controller
        return RegistrationForm2Response.builder()
                .success(true)
                .message("Form 2 draft saved successfully.")
                .currentStep(existingDraft.getCurrentStep())
                .build();
	}
	
	
	
	public RegistrationDraft submitForm3(RegistrationForm3Request request, MultipartFile frontImage, MultipartFile backImage, MultipartFile profileImage ) {
		
		//Fetch the incomplete draft from Redis through email as key.
	    RegistrationDraft existingDraft = cacheService.getDraft(request.getEmail());
	    if (existingDraft == null) {
	        throw new IllegalStateException("Registration session expired or not found.");
	    }
	    
	    //Mapping each file request(MultipartFIle) to UploadedFileDraft
	    //Why?: I have to store each uploaded file as UploadedFileDraft object in a list, since list type is <UploadedFileDraft>
	    UploadedFileDraft frontDraft= mapper.MultipartFileToUploadedDocumentDraft(frontImage,FileType.NID_FRONT,getFormat(frontImage));
	    UploadedFileDraft backDraft= mapper.MultipartFileToUploadedDocumentDraft(backImage,FileType.NID_BACK,getFormat(backImage));
	    UploadedFileDraft profileDraft= mapper.MultipartFileToUploadedDocumentDraft(profileImage,FileType.PROFILE_IMAGE,getFormat(profileImage));
	    
	    //UploadedFileDraft objects are added to the list
	    existingDraft.getUploadedDocuments().add(frontDraft); 
	    existingDraft.getUploadedDocuments().add(backDraft); 
	    existingDraft.getUploadedDocuments().add(profileDraft);
	    
	    //Update the step counter metadata manually
	    existingDraft.setCurrentStep(3);
	    existingDraft.setUpdatedAt(java.time.LocalDateTime.now());
	    
	    //Save the updated combined draft back to Redis
	    cacheService.saveDraft(request.getEmail(), existingDraft);
		
	    return existingDraft;
	    
	}
	
//	public boolean submitRegistration(String email) {
//		//Fetch the complete draft from Redis through email as key.
//	    RegistrationDraft existingDraft = cacheService.getDraft(email);
//	    if (existingDraft == null) {
//	        throw new IllegalStateException("Registration session expired or not found.");
//	    }
//	    
//	    return true;
//	}
	
	


}
