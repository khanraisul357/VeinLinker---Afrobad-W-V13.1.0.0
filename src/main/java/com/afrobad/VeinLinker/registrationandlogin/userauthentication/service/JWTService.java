/*
 * Responsibility of this service is to take a successfully authenticated Users object
 * and create a signed JWT string containing information about that user and the token's lifetime.
 */

package com.afrobad.VeinLinker.registrationandlogin.userauthentication.service;

import java.util.Date;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afrobad.VeinLinker.config.securityconfig.JWTProperties;
import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;

@Service
public class JWTService {

    // Inject JWT configuration
    @Autowired
    private JWTProperties jwtProperties;


    // ============================================================
    // 1. GENERATE JWT
    // ============================================================

    /**
     * Generates a signed JWT for an authenticated user.
     */
    public String generateJWT(Users user) {

        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("userId", user.getPublicUserId());
        extraClaims.put("role", user.getRole());

        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(
                    new Date(
                        System.currentTimeMillis()
                        + jwtProperties.getExpiration()
                    )
                )
                .signWith(getSignInKey())
                .compact();
    }


    // ============================================================
    // 2. EXTRACT ANY CLAIM
    // ============================================================

    /**
     * Extracts a specific claim from the JWT.
     */
    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }


    // ============================================================
    // 3. EXTRACT USERNAME / EMAIL
    // ============================================================

    /**
     * Extracts the subject (email) from the JWT.
     */
    public String extractUsername(String token) {

        return extractClaim(
                token,
                Claims::getSubject
        );
    }


    // ============================================================
    // 4. EXTRACT EXPIRATION
    // ============================================================

    /**
     * Extracts the expiration date from the JWT.
     */
    public Date extractExpiration(String token) {

        return extractClaim(
                token,
                Claims::getExpiration
        );
    }


    // ============================================================
    // 5. CHECK WHETHER TOKEN IS EXPIRED
    // ============================================================

    /**
     * Returns true if the JWT has expired.
     */
    public boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());
    }


    // ============================================================
    // 6. CHECK WHETHER TOKEN IS VALID
    // ============================================================

    /**
     * Checks whether:
     *
     * 1. Token belongs to the expected user
     * 2. Token has not expired
     * 3. Token signature is valid
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {

        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }


    // ============================================================
    // 7. EXTRACT ALL CLAIMS
    // ============================================================

    /**
     * Extracts all claims from the JWT.
     *
     * This also verifies the JWT signature.
     */
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    // ============================================================
    // 8. CREATE SECRET KEY
    // ============================================================

    /**
     * Converts the Base64 secret string into a SecretKey.
     */
    private SecretKey getSignInKey() {

        byte[] keyBytes =
                Decoders.BASE64.decode(
                        jwtProperties.getSecret()
                );

        return Keys.hmacShaKeyFor(keyBytes);
    }
}