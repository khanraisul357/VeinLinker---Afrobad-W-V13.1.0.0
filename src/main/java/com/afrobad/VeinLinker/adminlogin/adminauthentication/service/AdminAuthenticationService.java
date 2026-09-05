package com.afrobad.VeinLinker.adminlogin.adminauthentication.service;


import com.afrobad.VeinLinker.adminlogin.admin.entity.Admin;
import com.afrobad.VeinLinker.adminlogin.admin.repository.AdminRepository;
import com.afrobad.VeinLinker.adminlogin.adminauthentication.dto.AdminLoginRequestDTO;
import com.afrobad.VeinLinker.adminlogin.adminauthentication.dto.AdminLoginResponseDTO;
import com.afrobad.VeinLinker.registrationandlogin.userauthentication.service.JWTService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAuthenticationService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    

    public AdminLoginResponseDTO login(AdminLoginRequestDTO request) {

        Admin admin = adminRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!admin.isActive()) {
            throw new RuntimeException("Admin account is inactive");
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                admin.getPassword())) {

            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateJWT(admin);

        return new AdminLoginResponseDTO(token);
    }
}