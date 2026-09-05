package com.afrobad.VeinLinker.config.adminconfig;


import com.afrobad.VeinLinker.adminlogin.admin.entity.Admin;
import com.afrobad.VeinLinker.adminlogin.admin.repository.AdminRepository;
import com.afrobad.VeinLinker.registrationandlogin.users.enums.Role;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminDataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

   

    @Override
    public void run(String... args) {

        if (adminRepository.existsByEmail("admin@veinlinker.com")) {
            
            return;
        }

        Admin admin = Admin.builder()
                .username("admin")
                .email("admin@veinlinker.com")
                .password(passwordEncoder.encode("Admin@123"))
                .fullName("VeinLinker Administrator")
                .role(Role.ADMIN)
                .isActive(true)
                .build();

        adminRepository.save(admin);

        System.out.println("======================================");
        System.out.println("Initial Admin account created");
        System.out.println("Email: admin@veinlinker.com");
        System.out.println("======================================");
    }
}