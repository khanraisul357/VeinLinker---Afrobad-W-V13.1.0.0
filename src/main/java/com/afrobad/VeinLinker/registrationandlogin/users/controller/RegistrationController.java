// Handles Form 1, 2, 3 endpoints

package com.afrobad.VeinLinker.registrationandlogin.users.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.afrobad.VeinLinker.registrationandlogin.cache.drafts.RegistrationDraft;
import com.afrobad.VeinLinker.registrationandlogin.users.dto.*;
// handles business logic (multi-step registration flow)
import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;
import com.afrobad.VeinLinker.registrationandlogin.users.service.RegistrationService;
import com.afrobad.VeinLinker.registrationandlogin.userverification.dto.*;
import com.afrobad.VeinLinker.registrationandlogin.userverification.enums.VerificationType;
import com.afrobad.VeinLinker.registrationandlogin.userverification.service.OTPVerificationService;

import jakarta.validation.*;

@RestController
public class RegistrationController {
	
	@Autowired
	private RegistrationService multiStepServices;
	
	@Autowired
	private OTPVerificationService otpVerificationService;
	
	private Users user=new Users();
	
	
	@PostMapping("/register/form-1")
	public ResponseEntity<RegistrationForm1Response> form1Request(@Valid @RequestBody RegistrationForm1Request request){			
		
		RegistrationForm1Response response=multiStepServices.processForm1(request);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);// 201 created(new source created)
	}
	
	@PostMapping("/register/form-2")
	public ResponseEntity<RegistrationForm2Response> form2Request(@Valid @RequestBody RegistrationForm2Request request){
			
		RegistrationForm2Response response=multiStepServices.processForm2(request);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);// 201 created(new source created)

	}
	

	//I am telling spring, this method accepts/consumes http request/data which is in multi-part format instead of JSON
	//why using multipart format?: Because incoming request contains both text & raw binary data & in JSON format we can only
	//send text data
	//Payload divided into multiple parts/sections & each part received by the controller method.
	//Spring automatically converts multipart format to RegistrationForm3Request DTO
	@PostMapping(value = "/register/form-3",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<RegistrationDraft> form3Request(

			@Valid @ModelAttribute RegistrationForm3Request request, // Catches text fields
            @RequestPart("frontImage") MultipartFile frontImage,     // Catches binary NID/Passport Front
            @RequestPart("backImage") MultipartFile backImage,       // Catches binary NID/Passport Back
            @RequestPart("profileImage") MultipartFile profileImage   // Catches binary Profile image
	) {

	    RegistrationDraft draft=multiStepServices.submitForm3(
	            request,
	            frontImage,
	            backImage,
	            profileImage
	            );

	    return ResponseEntity.status(HttpStatus.CREATED).body(draft);
	}
	
	
	@PostMapping("/registration/submit")
	public ResponseEntity<String> submitFormRequest(@RequestBody RegistrationFormSubmitRequest request){
		
		multiStepServices.submitRegistrationForm(request.getEmail());
		return ResponseEntity.status(HttpStatus.CREATED).body( "Registration completed successfully.");
	}
	
	@PostMapping("/user/{userId}/verification")
	public ResponseEntity<OTPSendedResponseDTO> sendOTP(@PathVariable String userId){
		OTPSendedResponseDTO response=otpVerificationService.startVerification(userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	//Controller method to verify email
	@PostMapping("/verify/email")
	public ResponseEntity<String> verifyEmail(@RequestBody EmailVerificationRequestDTO request){
		
		otpVerificationService.verifyOTP(request.getEmail(),request.getOtp(),VerificationType.EMAIL);
		
		return ResponseEntity.status(HttpStatus.OK).body( "Email Verified");
	}
	
	//Controller method to verify phone number
	@PostMapping("/verify/phonenumber")
	public ResponseEntity<String> verifyPhoneNumber(@RequestBody PhoneNumberVerificationRequestDTO request){
		
		otpVerificationService.verifyOTP(request.getPhone(),request.getOtp(),VerificationType.NUMBER);
		
		return ResponseEntity.status(HttpStatus.OK).body( "Phone Number Verified");
	}
		
	

}
