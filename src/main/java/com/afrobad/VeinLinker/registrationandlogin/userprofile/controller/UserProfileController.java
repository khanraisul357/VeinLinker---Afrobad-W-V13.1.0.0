
package com.afrobad.VeinLinker.registrationandlogin.userprofile.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.afrobad.VeinLinker.registrationandlogin.userprofile.dto.*;
import com.afrobad.VeinLinker.registrationandlogin.userprofile.service.ProfileDocumentUpdateService;
import com.afrobad.VeinLinker.registrationandlogin.userprofile.service.ProfileImageUpdateService;
import com.afrobad.VeinLinker.registrationandlogin.userprofile.service.UserProfileService;
import com.afrobad.VeinLinker.registrationandlogin.userprofile.service.UserProfileUpdateService;

@RestController
@RequestMapping("/api")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;
    
    @Autowired
    private UserProfileUpdateService userProfileUpdateService;
    
    @Autowired
    private ProfileImageUpdateService profileImageUpdateService;
    
    @Autowired
    private ProfileDocumentUpdateService profileDocumentUpdateService;

 // VIEW PROFILE
    @GetMapping("/users/profile")
    public ResponseEntity<UserProfileResponseDTO> getUserProfile(Authentication authentication) {

        // Get the username(email) of the currently authenticated user
        String email = authentication.getName();

        // Get that user's profile from the service
        UserProfileResponseDTO response =userProfileService.getProfile(email);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    
    
    //UPDATE NON-DOCUMENT INFORMATION IN THE PROFILE
    @PutMapping("/users/profile/edit")
    public ResponseEntity<UserProfileResponseDTO> updateProfile(Authentication authentication,@RequestBody UserProfileUpdateRequestDTO request) {

        String email = authentication.getName();

        UserProfileResponseDTO response=userProfileUpdateService.updateProfile(email,request);
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    //UPDATE PROFILE IMAGE IN THE PROFILE
    @PutMapping("/users/profile/edit/profileimage")
    public ResponseEntity<?> updateProfileImage(Authentication authentication,@RequestParam("profileImage") MultipartFile profileImage) throws IOException {
    	
    	 String email = authentication.getName();

         profileImageUpdateService.updateProfileImage(email,profileImage);

         return ResponseEntity.ok("Profile image updated successfully");
    	
    }
    
    @PutMapping("/users/profile/edit/document")
    public ResponseEntity<?> updateDocument(Authentication authentication,@ModelAttribute ProfileDocumentUpdateRequestDTO request) {

        String email = authentication.getName();

        profileDocumentUpdateService.updateDocument(email,request);

        return ResponseEntity.ok(
                "Document updated successfully"
        );
    }
    
}
