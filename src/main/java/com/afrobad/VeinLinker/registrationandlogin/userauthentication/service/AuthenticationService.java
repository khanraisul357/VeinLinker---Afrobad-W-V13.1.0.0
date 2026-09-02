//responsible for confirming whether a user is a registered user or not through email/number & password.

package com.afrobad.VeinLinker.registrationandlogin.userauthentication.service;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.afrobad.VeinLinker.registrationandlogin.userauthentication.dto.LoginRequestDTO;
import com.afrobad.VeinLinker.registrationandlogin.userauthentication.dto.LoginResponseDTO;
import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;
import com.afrobad.VeinLinker.registrationandlogin.users.repository.UsersRepository;

@Service
public class AuthenticationService {
	
	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTService jwtService;
	
	//method to identify the identifier is email or phone number
	private boolean isEmail(String identifier) {
        return identifier.contains("@");
    }
	
	public LoginResponseDTO startLogin(LoginRequestDTO request) throws AccountNotFoundException {
		
		Users user;
		
		String identifier=request.getIdentifier();
		
		//verify email or phone number
		if(isEmail(identifier)) {
			user = userRepository.findByEmail(request.getIdentifier()) //fetching Users record through email
                    .orElseThrow(() -> new AccountNotFoundException("User not found"));
        } else {
            user = userRepository.findByPhone(request.getIdentifier()) //fetching Users record through phone number
                    .orElseThrow(() -> new AccountNotFoundException("User not found"));
        }
		
		
		// Verify password
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
		    throw new BadCredentialsException("Invalid credentials");
		}
		
        // Generate JWT
		String token = jwtService.generateJWT(user);
		
        // Return response
		LoginResponseDTO loginResponse= LoginResponseDTO.builder()
				                        .message("Login Successful")
				                        .jwt(token)
				                        .build();
				                        
		return loginResponse;
		}
		
	}