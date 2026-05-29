package com.afrobad.VeinLinker.registrationandlogin.users.dto;

public class RegistrationForm2Response {
    private boolean success;
    private int step;
    private String registrationToken;
    private String message;

    // Force everyone to use the static method by hiding the "new" keyword
    private RegistrationForm2Response() {} 

    public static RegistrationForm2Response success(String token) {
        RegistrationForm2Response r = new RegistrationForm2Response();
        r.success = true;
        r.step = 2;
        r.registrationToken = token;
        r.message = "Step 2 complete. Please upload your verification documents.";
        return r;
    }

    // Getters
    public boolean isSuccess() { return success; }
    public int getStep() { return step; }
    public String getRegistrationToken() { return registrationToken; }
    public String getMessage() { return message; }
}