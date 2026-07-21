package com.afrobad.VeinLinker.registrationandlogin.userverification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afrobad.VeinLinker.admin.Repository.AdminRepository;
import com.afrobad.VeinLinker.admin.entity.Admin;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.entity.UserFile;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.repository.UserFileRepository;
import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;
import com.afrobad.VeinLinker.registrationandlogin.userverification.entity.UserDocumentVerification;
import com.afrobad.VeinLinker.registrationandlogin.userverification.enums.VerificationDocumentStatus;
import com.afrobad.VeinLinker.registrationandlogin.users.repository.UsersRepository;
import com.afrobad.VeinLinker.registrationandlogin.userverification.dto.UserDocumentVerificationRequestDTO;
import com.afrobad.VeinLinker.registrationandlogin.userverification.repository.UserDocumentVerificationRepository;
import com.afrobad.VeinLinker.registrationandlogin.userverification.service.UserVerificationService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserDocumentVerificationService {
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private UserDocumentVerificationRepository userDocumentVerificationRepository;
	
	@Autowired
	private UserFileRepository userFileRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private UserVerificationService userVerificationService;
	
	public String verifyUserDocument(Long adminId,String userId,UserDocumentVerificationRequestDTO request) {
		
		//Fetch user by userId 
		Users user = usersRepository.findByPublicUserId(userId)
	                .orElseThrow(() -> new RuntimeException("User not found"));
		
		//Fetch UserDocumentVerification by user
		UserDocumentVerification userDocumentVerification=userDocumentVerificationRepository.findByUser(user)
				    .orElseThrow(() -> new RuntimeException("UserDocumentVerification record for this user not found")); 
		
		//Fetch UserFiles by user
		List<UserFile> userFiles=userFileRepository.findByUser(user)
				     .orElseThrow(() -> new RuntimeException("UserFile record for this user not found"));
		
		//Fetch the admin entity using the adminId (passed from your controller/auth context)
		Admin admin = adminRepository.findById(adminId)
		        .orElseThrow(() -> new RuntimeException("Admin not found with ID: " + adminId));
		
		//Updating(Document status PENDING to VERIFIED) UserDocumentVerification entity for particular user
		userDocumentVerification.setVerificationDocumentType(
		        request.getVerificationDocumentType());

		userDocumentVerification.setDocumentVerificationStatus(
		        request.getDocumentVerificationStatus());

		userDocumentVerification.setReviewedBy(admin);
		userDocumentVerification.setReviewedAt(LocalDateTime.now());
		
		
		//save in MySQL
		userDocumentVerificationRepository.save(userDocumentVerification);
		
		//Check user is verified or not
		String statusMessage=userVerificationService.verifyUser(user);
		
		return statusMessage;
		
		//Updating (uploadStatus.PENDING to uploadStatus.UPLOADED)
		
	}

	

}
