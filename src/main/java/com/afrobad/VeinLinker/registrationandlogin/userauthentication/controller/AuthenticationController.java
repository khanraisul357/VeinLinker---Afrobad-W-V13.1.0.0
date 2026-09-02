//responsible for triggering all endpoints related to authentication

package com.afrobad.VeinLinker.registrationandlogin.userauthentication.controller;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import com.afrobad.VeinLinker.registrationandlogin.userauthentication.dto.*;
import com.afrobad.VeinLinker.registrationandlogin.userauthentication.service.AuthenticationService;

@RestController
@RequestMapping("/api/user")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/login")
	private ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) throws AccountNotFoundException{
	    LoginResponseDTO response=authenticationService.startLogin(request);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}
}
