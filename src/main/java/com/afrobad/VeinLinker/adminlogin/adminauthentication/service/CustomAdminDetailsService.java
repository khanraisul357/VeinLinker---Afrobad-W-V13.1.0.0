package com.afrobad.VeinLinker.adminlogin.adminauthentication.service;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.afrobad.VeinLinker.adminlogin.admin.entity.Admin;
import com.afrobad.VeinLinker.adminlogin.admin.repository.AdminRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CustomAdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() ->new UsernameNotFoundException("Admin not found with email: " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(admin.getEmail())
                .password(admin.getPassword())
                .authorities(new SimpleGrantedAuthority("ROLE_" + admin.getRole().name()))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!admin.isActive())
                .build();
    }
}