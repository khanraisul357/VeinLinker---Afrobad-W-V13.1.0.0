package com.afrobad.VeinLinker.registrationandlogin.users.dto;

public class RegistrationForm3Response {
    private boolean success;
    private int step;
    private String publicUserId;
    private String message;

    // Force everyone to use the static method by hiding the "new" keyword
    private RegistrationForm3Response() {} 

    public static RegistrationForm3Response success(String publicUserId) {
        RegistrationForm3Response r = new RegistrationForm3Response();
        r.success = true;
        r.step = 3;
        r.publicUserId = publicUserId;
        r.message = "Registration complete. Your account is pending verification.";
        return r;
    }

    // Getters
    public boolean isSuccess() { return success; }
    public int getStep() { return step; }
    public String getPublicUserId() { return publicUserId; }
    public String getMessage() { return message; }
}