package com.afrobad.VeinLinker.config.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.afrobad.VeinLinker.registrationandlogin.userauthentication.filter.JWTAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .formLogin(form -> form.disable())

            .httpBasic(basic -> basic.disable())

            .authorizeHttpRequests(auth -> auth

                // Public endpoints
                .requestMatchers("/api/login").permitAll()//Anyone is allowed to send a request to /api/login without already being authenticated.
                .requestMatchers("/register/form-1").permitAll()//Anyone is allowed to send a request to /register/form-1 without already being authenticated.
                .requestMatchers("/register/form-2").permitAll()//Anyone is allowed to send a request to /register/form-2 without already being authenticated.
                .requestMatchers("/register/form-3").permitAll()//Anyone is allowed to send a request to /register/form-3 without already being authenticated.
                .requestMatchers("/registration/submit").permitAll()//Anyone is allowed to send a request to /registration/submit without already being authenticated.

                // Everything else requires authentication
                .anyRequest().authenticated()
            )

            // Run JWT filter before Spring's username/password filter
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}