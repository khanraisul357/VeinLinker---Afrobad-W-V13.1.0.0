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

import com.afrobad.VeinLinker.registrationandlogin.users.service.RegistrationService;

import jakarta.validation.*;

@RestController
public class RegistrationController {
	
	@Autowired
	private RegistrationService multiStepServices;
	
	
	
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
	//Payload divided into multiple individual parts/sections & handled  by the controller method.
	@PostMapping(value = "/register/form-3",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> form3Request(

	        @RequestPart("request")
	        RegistrationForm3Request request,

	        @RequestPart("profileImage")
	        MultipartFile profileImage,

	        @RequestPart(value = "nidFront", required = false)
	        MultipartFile nidFront,

	        @RequestPart(value = "nidBack", required = false)
	        MultipartFile nidBack,

	        @RequestPart(value = "passportFront", required = false)
	        MultipartFile passportFront,

	        @RequestPart(value = "passportBack", required = false)
	        MultipartFile passportBack
	) {

	    multiStepServices.submitForm3(
	            request,
	            profileImage,
	            nidFront,
	            nidBack,
	            passportFront,
	            passportBack);

	    return ResponseEntity.ok().build();
	}
		
	

}
