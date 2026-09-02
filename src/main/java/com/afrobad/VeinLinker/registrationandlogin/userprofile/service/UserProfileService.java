package com.afrobad.VeinLinker.registrationandlogin.userprofile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afrobad.VeinLinker.registrationandlogin.userprofile.dto.UserProfileResponseDTO;
import com.afrobad.VeinLinker.registrationandlogin.userprofile.dto.UserProfileUpdateRequestDTO;
import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;
import com.afrobad.VeinLinker.registrationandlogin.users.enums.*;
import com.afrobad.VeinLinker.registrationandlogin.users.repository.UsersRepository;

@Service
public class UserProfileService {

    @Autowired
    private UsersRepository usersRepository;


    // View the authenticated user's profile
    public UserProfileResponseDTO getProfile(String email) {

        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() ->new RuntimeException("User not found"));

        return UserProfileResponseDTO.builder()
                .publicUserId(user.getPublicUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .fathersName(user.getFathersName())
                .mothersName(user.getMothersName())
                .dob(user.getDob())
                .age(user.getAge())
                .gender(user.getGender().name())
                .height(user.getHeight())
                .weight(user.getWeight())
                .religion(user.getReligion().name())
                .maritalStatus(user.getMaritalStatus().name())
                .bloodGroup(user.getBloodGroup().name())
                .rhFactor(user.getRhFactor().name())
                .build();
    }


    
}