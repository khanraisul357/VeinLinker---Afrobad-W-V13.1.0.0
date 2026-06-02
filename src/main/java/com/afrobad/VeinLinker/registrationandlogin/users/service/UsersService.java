//This service will be needed for after user is registered or created, for login,edit & view profile

package com.afrobad.VeinLinker.registrationandlogin.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afrobad.VeinLinker.registrationandlogin.users.repository.UsersRepository;

@Service
public class UsersService{
	
	private UsersRepository repository;
	UsersService(UsersRepository repository){
		this.repository=repository;
	}
	
	
	
}

////1. Check if email or phone already exists
//if (usersRepository.existsByEmail(request.getEmail())) {
// throw new IllegalArgumentException("Email already exists");
//}
//if (usersRepository.existsByPhone(request.getPhone())) {
// throw new IllegalArgumentException("Phone number already exists");
//}
//
////2. If completely new, map DTO to Entity and Save
//Users newUser = new Users();
//newUser.setEmail(request.getEmail());
//newUser.setPhone(request.getPhone());
////Ensure you encode this before saving!
//newUser.setPassword(passwordEncoder.encode(request.getPassword())); 
//
//Users savedUser = usersRepository.save(newUser);
//
////3. Return response with savedUser.getId() so Step 2 knows who this belongs to!