//responsible for triggering all endpoints related to authentication

package com.afrobad.VeinLinker.registrationandlogin.userauthentication.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import com.afrobad.VeinLinker.registrationandlogin.userauthentication.dto.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	@PostMapping("login")
	private EntityResponse<?> login(@RequestBody LoginRequestDTO request){
		return null;
	}
}
