package com.afrobad.VeinLinker.users.dto;

import jakarta.validation.constraints.NotBlank;

// Step 3 carries only the registrationToken.
// The actual files (NID image + profile picture) come as
// MultipartFile in the Controller method signature directly —
// they can't be inside a JSON request body DTO.
public class RegistrationForm3Request {

    @NotBlank(message = "Registration token is required")
    private String registrationToken;

    public String getRegistrationToken() { return registrationToken; }
    public void setRegistrationToken(String t) { this.registrationToken = t; }
}