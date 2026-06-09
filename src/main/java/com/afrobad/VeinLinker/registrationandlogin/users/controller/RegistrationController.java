// Handles Form 1, 2, 3 endpoints

package com.afrobad.VeinLinker.registrationandlogin.users.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

import com.afrobad.VeinLinker.registrationandlogin.cache.drafts.RegistrationDraft;
import com.afrobad.VeinLinker.registrationandlogin.users.dto.*;
// handles business logic (multi-step registration flow)

import com.afrobad.VeinLinker.registrationandlogin.users.service.RegistrationService;

import jakarta.validation.*;

@RestController
public class RegistrationController {
	
	@Autowired
	private RegistrationService multiStepServices;
	
//	@Autowired 
//	private RegistrationDraft draft;
	
	@PostMapping("/register/form-1")
	public ResponseEntity<RegistrationForm1Response> form1Request(@Valid @RequestBody RegistrationForm1Request request){
			
		
		RegistrationForm1Response response=multiStepServices.processForm1(request);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);// 201 created(new source created)
	}
	
//	@PostMapping("/register/form-2")
//	public ResponseEntity<RegistrationForm2Response> form2Request(@Valid @RequestBody RegistrationForm2Request request){
//		
//		
//		RegistrationForm2Response response=multiStepServices.processForm2(draft.getEmail(),request);
//		return ResponseEntity.status(HttpStatus.CREATED).body(response);// 201 created(new source created)
//
//	}
	
	

}
