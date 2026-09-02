package com.afrobad.VeinLinker.registrationandlogin.userauthentication.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;
import com.afrobad.VeinLinker.registrationandlogin.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException {

    	 Users user = usersRepository.findByEmail(username)
                 .orElseThrow(() ->new UsernameNotFoundException("User not found with email: " + username));

        return  org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().name())
                .build();
    }
}