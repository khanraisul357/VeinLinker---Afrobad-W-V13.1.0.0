package com.afrobad.VeinLinker.registrationandlogin.userauthentication.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.afrobad.VeinLinker.registrationandlogin.userauthentication.service.JWTService;
import com.afrobad.VeinLinker.adminlogin.adminauthentication.service.CustomAdminDetailsService;
import com.afrobad.VeinLinker.registrationandlogin.userauthentication.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomAdminDetailsService customAdminDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Get Authorization header
        final String authHeader = request.getHeader("Authorization");

        // 2. Check whether Bearer token exists
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract JWT
        final String jwt = authHeader.substring(7);

        // 4. Extract username(email) from JWT
        final String username =jwtService.extractUsername(jwt);

        // 5. Extract role from JWT
        final String role =jwtService.extractRole(jwt);

        // 6. Continue only if username exists and user or admin is not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
              
           // 7. Load user or admin from database based on account type(USER or ROLE)        	
           UserDetails userDetails;	
           if ("ADMIN".equals(role)) {

              // Admin → admins table
        	  userDetails =customAdminDetailsService.loadUserByUsername(username);

           } else {

              // User → users table
        	  userDetails =customUserDetailsService.loadUserByUsername(username);
           }
           
           

           // 8. Verify JWT
           if (jwtService.isTokenValid(jwt, userDetails)) {

                // 9. Create Authentication object
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // 10. Attach request details
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 11. Tell Spring Security user is authenticated
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }
        }

        // 12. Continue request
        filterChain.doFilter(request, response);
    }
}