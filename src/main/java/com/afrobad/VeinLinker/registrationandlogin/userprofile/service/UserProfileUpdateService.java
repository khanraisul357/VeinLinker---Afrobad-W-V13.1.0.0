package com.afrobad.VeinLinker.registrationandlogin.userprofile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afrobad.VeinLinker.registrationandlogin.userprofile.dto.UserProfileResponseDTO;
import com.afrobad.VeinLinker.registrationandlogin.userprofile.dto.UserProfileUpdateRequestDTO;
import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;
import com.afrobad.VeinLinker.registrationandlogin.users.repository.UsersRepository;

@Service
public class UserProfileUpdateService {
	
	@Autowired
    private UsersRepository usersRepository;
	
	// Update the authenticated user's profile
    public UserProfileResponseDTO updateProfile(String email,UserProfileUpdateRequestDTO request) {

        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        //set new data from input fields
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }

        if (request.getFathersName() != null) {
            user.setFathersName(request.getFathersName());
        }

        if (request.getMothersName() != null) {
            user.setMothersName(request.getMothersName());
        }

        if (request.getDob() != null) {
            user.setDob(request.getDob());
        }

        if (request.getGender() != null) {
            user.setGender(request.getGender());
            
        }

        if (request.getHeight() != null) {
            user.setHeight(request.getHeight());
        }

        if (request.getWeight() != null) {
            user.setWeight(request.getWeight());
        }

        if (request.getReligion() != null) {
            user.setReligion(request.getReligion());
           
        }

        if (request.getMaritalStatus() != null) {
            user.setMaritalStatus(request.getMaritalStatus());
           
        }

        if (request.getBloodGroup() != null) {
            user.setBloodGroup(request.getBloodGroup());
        }

        if (request.getRhFactor() != null) {
            user.setRhFactor(request.getRhFactor());
        }

        //update the database
        Users updatedUser = usersRepository.save(user);

        return UserProfileResponseDTO.builder()
                .publicUserId(updatedUser.getPublicUserId())
                .fullName(updatedUser.getFullName())
                .email(updatedUser.getEmail())
                .phone(updatedUser.getPhone())
                .fathersName(updatedUser.getFathersName())
                .mothersName(updatedUser.getMothersName())
                .dob(updatedUser.getDob())
                .age(updatedUser.getAge())
                .gender(updatedUser.getGender().name())
                .height(updatedUser.getHeight())
                .weight(updatedUser.getWeight())
                .religion(updatedUser.getReligion().name())
                .maritalStatus(updatedUser.getMaritalStatus().name())
                .bloodGroup(updatedUser.getBloodGroup().name())
                .rhFactor(updatedUser.getRhFactor().name())
                .build();
    }
}
