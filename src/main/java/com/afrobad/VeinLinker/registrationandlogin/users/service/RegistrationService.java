// Connects  to Redis, MySQL, & maps to User, Files, and UserVerification entities, and saves them using their respective repositories.

package com.afrobad.VeinLinker.registrationandlogin.users.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.afrobad.VeinLinker.registrationandlogin.cache.drafts.RegistrationDraft;
import com.afrobad.VeinLinker.registrationandlogin.cache.drafts.UploadedFileDraft;
import com.afrobad.VeinLinker.registrationandlogin.cache.enums.RegistrationStatus;
import com.afrobad.VeinLinker.registrationandlogin.cache.service.RegistrationCacheService;
import com.afrobad.VeinLinker.registrationandlogin.mapper.RegistrationMapper;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.entity.Files;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.entity.UserFile;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.enums.FileType;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.enums.Format;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.enums.UploadStatus;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.repository.FileRepository;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.repository.UserFileRepository;
import com.afrobad.VeinLinker.registrationandlogin.users.dto.*;
import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;
import com.afrobad.VeinLinker.registrationandlogin.users.enums.AccountStatus;
import com.afrobad.VeinLinker.registrationandlogin.users.enums.Role;
import com.afrobad.VeinLinker.registrationandlogin.users.enums.UserStatus;
import com.afrobad.VeinLinker.registrationandlogin.users.repository.UsersRepository;
import com.afrobad.VeinLinker.registrationandlogin.userverification.service.OTPVerificationService;

import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
@Service
public class RegistrationService {
	
	@Autowired
	private UsersRepository repository;
	
	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private UserFileRepository userFileRepository;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private RegistrationMapper mapper;
	
	@Autowired
    private RegistrationCacheService cacheService;
	
	@Autowired
	private Validator validator;
	
	
	
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
        
        
        //Calling method RegistrationCacheService.saveDraft() to save form 1 data temporarily to Redis using email as the unique key
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

	    /*
	     * MapStruct updates ONLY the fields present in Form 2 Request.
	     * Form 1 data (email, phone, passwordHash) remains completely untouched and safe!
	    */
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
	
	
	//Method to get format(jpg,jpeg,png,pdf) of the uploaded file
    private Format getFormat(MultipartFile file) {

        String originalName = file.getOriginalFilename();

        if (originalName == null) {
            throw new IllegalArgumentException("File name is missing.");
        }

        String extension =
                originalName.substring(
                        originalName.lastIndexOf('.') + 1
                ).toLowerCase();

        return Format.valueOf(extension);
    }

	
	public RegistrationDraft submitForm3(RegistrationForm3Request request, MultipartFile frontImage, MultipartFile backImage, MultipartFile profileImage ) {
		
		//Fetch the incomplete draft from Redis through email as key.
	    RegistrationDraft existingDraft = cacheService.getDraft(request.getEmail());
	    if (existingDraft == null) {
	        throw new IllegalStateException("Registration session expired or not found.");
	    }
	    
	    /*
	     * Mapping each file request(MultipartFIle) to UploadedFileDraft
	     * Why?: I have to store each uploaded file as UploadedFileDraft object in a list, since list type is <UploadedFileDraft>
	    */
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
	
	//Method to validate draft fetched from redis before storing it to DB permanently
	private void validateRegistrationDraft(RegistrationDraft draft) {
		
        /*
         * This single line triggers EVERY annotation rule inside your draft object
         * Validator.validate() method handing draft to hibernet validator & triggers hibernet validator.
         * Scans RegistrationDraft Fields & looks for JVA.
         * Returns Set containing ConstraintViolation.
         * If no field validation failed, returns set containing zero/size=0/empty ConstraintViolation object(violations=0 object)
         * If there 5 field validation failed, returns set containing five/size=5/not empty ConstraintViolation object(violations=5 objects) 
		 */
        Set<ConstraintViolation<RegistrationDraft>> violations = validator.validate(draft);
        
        // If there are any errors, deal with them immediately
        if (!violations.isEmpty()) {
            // Option A: Extract the first error message and throw an exception
            String firstErrorMessage = violations.iterator().next().getMessage();
            throw new IllegalArgumentException(firstErrorMessage);
        }
	}
	
	//Method to generate publicUserId
	private String generatePublicId(long internalId) {
	    return String.format("VL-%07d", internalId);
	}
	
	@Transactional
	public void submitRegistrationForm(String email) {
		
		//Step-1:Fetch the complete draft from Redis through email as key.
	    RegistrationDraft draft = cacheService.getDraft(email);
	    if (draft == null) {
	        throw new IllegalStateException("Registration session expired or not found.");
	    }
	    
	    //Step-2:Validate fetched draft
	    validateRegistrationDraft(draft);
	    
	    //Step-3: Creating Users Object-->Map Users to RegistrationDraft(Converting RegistrationDraft into Users)
	    Users user=mapper.draftToUsers(draft);
	    
	    // =========================================================================
	    // 1.Identity Fields Initialization
	    // =========================================================================
	    
	    //MySQL generates internalUserId after insert operation(Users object saved to DB)
	    //Generates encryptedUserId
	    user.setEncryptedUserId(UUID.randomUUID().toString());
	    //publicUserID generated only after internalUserId generated by MySQL.

	    // ======================================================
	    // 2. Lifecycle state Fields (initial state)
	    // ======================================================

	    user.setIsVerified(UserStatus.PENDING);
	    user.setAccountStatus(AccountStatus.INACTIVE);
	    user.setLastActiveMode(null); // no activity yet

	    // ======================================================
	    // 3. Stat Fields (safety net, even if @Builder.Default exists)
	    // ======================================================

	    user.setDonationCount(0);
	    user.setReceivingCount(0);
	    user.setPoints(0);
	    user.setLevel(0);
	    user.setReliabilityScore(100);

	    // ======================================================
	    // 4. Save first (important for public ID generation)
	    // ======================================================

//	    user = repository.save(user);

	    // ======================================================
	    // 5. Public ID generation (depends on DB-generated ID)
	    // ======================================================
	    
	    // First save
	    user = repository.save(user);

	    // Now internalUserId exists
	    user.setPublicUserId(generatePublicId(user.getInternalUserId()));

	    // Second save updates only publicUserId
	    repository.save(user);

	    
	    
	    //Step-4:Converting List of UploadedFileDraft to List of Files & Creating Files Object
	    
	    List<Files> files =draft.getUploadedDocuments().stream().map(mapper::draftToFiles).toList();
	  
	    //Step-5: Saving list of Files to DB
        fileRepository.saveAll(files);
        
        //Step-6:Creating List of UserFile Object
        List<UserFile> userFiles = new ArrayList<>();
        
        for(int i=0;i<files.size();i++) {

          UploadedFileDraft fileDraft =draft.getUploadedDocuments().get(i);//extracting object from list(uploadedDocuments)

          Files file =files.get(i);//extracting object from list(files)

          UserFile userFile=UserFile.builder()//userFile is single UserFile Object
        	         .user(user)
        	         .file(file)
        	         .documentType(fileDraft.getDocumentType())
        	         .status(UploadStatus.PENDING)
        	         .build();
        	            
          userFiles.add(userFile);
        }
        
        //Step-8: Saving list of UserFile to DB
        userFileRepository.saveAll(userFiles);
        
        //Delete draft from redis
        
        
	    
	}
	
	


}
