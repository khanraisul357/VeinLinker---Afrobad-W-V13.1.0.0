package com.afrobad.VeinLinker.adminlogin.adminauthentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.afrobad.VeinLinker.adminlogin.adminauthentication.dto.AdminLoginRequestDTO;
import com.afrobad.VeinLinker.adminlogin.adminauthentication.dto.AdminLoginResponseDTO;
import com.afrobad.VeinLinker.adminlogin.adminauthentication.service.AdminAuthenticationService;


import lombok.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminAuthenticationController {

    private final AdminAuthenticationService authenticationService;



    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponseDTO> login(@RequestBody AdminLoginRequestDTO request) {

    	AdminLoginResponseDTO response=authenticationService.login(request);
    	
        return ResponseEntity.ok(response);
    }
}