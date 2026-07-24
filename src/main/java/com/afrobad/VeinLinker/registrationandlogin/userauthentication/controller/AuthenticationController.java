//responsible for triggering all endpoints related to authentication

package com.afrobad.VeinLinker.registrationandlogin.userauthentication.controller;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import com.afrobad.VeinLinker.registrationandlogin.userauthentication.dto.*;
import com.afrobad.VeinLinker.registrationandlogin.userauthentication.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/login")
	private EntityResponse<?> login(@RequestBody LoginRequestDTO request) throws AccountNotFoundException{
		authenticationService.startLogin(request);
		return null;
	}
}
