package com.afrobad.VeinLinker.registrationandlogin.users.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationForm2Response {
    private boolean success;
    private String message;
    private int currentStep;
   
}