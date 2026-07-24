//responsible for confirming whether a user is a registered user or not through email/number & password.

package com.afrobad.VeinLinker.registrationandlogin.userauthentication.service;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afrobad.VeinLinker.registrationandlogin.userauthentication.dto.LoginRequestDTO;
import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;
import com.afrobad.VeinLinker.registrationandlogin.users.repository.UsersRepository;

@Service
public class AuthenticationService {
	
	@Autowired
	private UsersRepository userRepository;
	
	//method to identify the identifier is email or phone number
	private boolean isEmail(String identifier) {
        return identifier.contains("@");
    }
	
	public void startLogin(LoginRequestDTO request) throws AccountNotFoundException {
		
		Users user;
		
		String identifier=request.getIdentifier();
		
		if(isEmail(identifier)) {
			user = userRepository.findByEmail(request.getIdentifier()) //fetching Users record through email
                    .orElseThrow(() -> new AccountNotFoundException("User not found"));
        } else {
            user = userRepository.findByPhone(request.getIdentifier()) //fetching Users record through phone number
                    .orElseThrow(() -> new AccountNotFoundException("User not found"));
        }
		
		
		// Verify password
        // Generate JWT
        // Return response
		
		}
		
	}