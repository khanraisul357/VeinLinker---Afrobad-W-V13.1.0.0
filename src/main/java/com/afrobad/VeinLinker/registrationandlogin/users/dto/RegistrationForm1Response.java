package com.afrobad.VeinLinker.registrationandlogin.users.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationForm1Response {
    private boolean success;
    private int step;
    private String registrationToken;
    private String message;


    public static RegistrationForm1Response success(String token) {
        RegistrationForm1Response r = new RegistrationForm1Response();
        r.success = true;
        r.step = 1;
        r.registrationToken = token;
        r.message = "Step 1 complete. Please fill you health informations";
        return r;
    }
    
 
}

////Getters
//public boolean isSuccess() { return success; }
//public String getMessage() { return message; }
//public String getRegistrationToken() { return registrationToken; }
//public int getStep() { return step; }