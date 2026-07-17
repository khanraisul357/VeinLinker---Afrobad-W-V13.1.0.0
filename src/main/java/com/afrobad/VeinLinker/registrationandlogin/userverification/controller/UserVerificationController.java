package com.afrobad.VeinLinker.registrationandlogin.userverification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import com.afrobad.VeinLinker.registrationandlogin.userverification.dto.UserDocumentVerificationRequestDTO;
import com.afrobad.VeinLinker.registrationandlogin.userverification.dto.EmailVerificationRequestDTO;
import com.afrobad.VeinLinker.registrationandlogin.userverification.dto.OTPSendedResponseDTO;
import com.afrobad.VeinLinker.registrationandlogin.userverification.dto.PhoneNumberVerificationRequestDTO;
import com.afrobad.VeinLinker.registrationandlogin.userverification.enums.VerificationType;


import com.afrobad.VeinLinker.registrationandlogin.userverification.service.OTPVerificationService;
import com.afrobad.VeinLinker.registrationandlogin.userverification.service.UserDocumentVerificationService;
import com.afrobad.VeinLinker.registrationandlogin.userverification.service.UserVerificationService;

@Controller
public class UserVerificationController {
	
	@Autowired
	private OTPVerificationService otpVerificationService;
	
	@Autowired
	private UserDocumentVerificationService userDocumentVerificationService;
	
	
	@PostMapping("/user/{userId}/verification")
	public ResponseEntity<OTPSendedResponseDTO> sendOTP(@PathVariable String userId){
		OTPSendedResponseDTO response=otpVerificationService.startVerification(userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	//Controller method to verify email
	@PostMapping("/verify/email")
	public ResponseEntity<String> verifyEmail(@RequestBody EmailVerificationRequestDTO request){
		
		String response=otpVerificationService.verifyOTP(request.getEmail(),request.getOtp(),VerificationType.EMAIL);		
		return ResponseEntity.ok("Email is verified " + response);
	}
	
	//Controller method to verify phone number
	@PostMapping("/verify/phonenumber")
	public ResponseEntity<String> verifyPhoneNumber(@RequestBody PhoneNumberVerificationRequestDTO request){
		
		String response=otpVerificationService.verifyOTP(request.getPhoneNumber(),request.getOtp(),VerificationType.NUMBER);				
		return ResponseEntity.ok("Phone Number is verified. " + response);
	}
	
	//Controller method to verify users verification document(NID or Passport)
	@PatchMapping("/admin/{adminId}/documentverification/{userId}")
	public ResponseEntity<String> verifyDocument(@PathVariable Long adminId,@PathVariable String userId,@RequestBody UserDocumentVerificationRequestDTO request) {

		String response=userDocumentVerificationService.verifyUserDocument(adminId,userId,request);
		return ResponseEntity.ok("Document is verified. " + response);
    }
		

}
