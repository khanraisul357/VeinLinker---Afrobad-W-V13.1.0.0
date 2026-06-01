// Connects  to Redis, MySQL, & maps to User, Files, and UserVerification entities, and saves them using their respective repositories.

package com.afrobad.VeinLinker.registrationandlogin.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afrobad.VeinLinker.registrationandlogin.users.dto.*;
import com.afrobad.VeinLinker.registrationandlogin.users.repository.UsersRepository;

@Service
public class RegistrationService {
	
	@Autowired
	private UsersRepository repository;
	
	// Isolated Business Validation Method
    private void validateForm1BusinessRules(RegistrationForm1Request request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered!");
        }

        if (repository.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentException("Phone number is already registered!");
        }
        
        //Run the business validations first(wether users email or phone number exists in DB)
        validateForm1BusinessRules(request);
        
        // Add any other Form 1 specific business rules here
    }
	
	public RegistrationForm1Response form1(RegistrationForm1Request request) {
		return null;
		
	}

}
