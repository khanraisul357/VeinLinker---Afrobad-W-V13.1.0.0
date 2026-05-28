package com.afrobad.VeinLinker.users.dto;

// A single response class used by all 3 steps.
// Different fields are populated depending on which step was completed.
public class ResponseSendedToClient {

    private boolean success;
    private String message;

    // Returned after Step 1 — used by frontend to call Step 2 and Step 3
    private String registrationToken;

    // Returned after Step 3 — the user's public-facing ID
    private String publicUserId;

    // Current step number (1, 2, or 3) — helps frontend show progress bar
    private int step;

    // -------------------------------------------------------------------------
    // Static factory methods — clean way to build responses without
    // needing to call setters everywhere in the Service layer.
    // -------------------------------------------------------------------------

    public static ResponseSendedToClient step1Success(String registrationToken) {
        ResponseSendedToClient r = new ResponseSendedToClient();
        r.success = true;
        r.step = 1;
        r.registrationToken = registrationToken;
        r.message = "Step 1 complete. Proceed to health information.";
        return r;
    }

    public static ResponseSendedToClient step2Success(String registrationToken) {
        ResponseSendedToClient r = new ResponseSendedToClient();
        r.success = true;
        r.step = 2;
        r.registrationToken = registrationToken;
        r.message = "Step 2 complete. Please upload your NID and profile picture.";
        return r;
    }

    public static ResponseSendedToClient step3Success(String publicUserId) {
        ResponseSendedToClient r = new ResponseSendedToClient();
        r.success = true;
        r.step = 3;
        r.publicUserId = publicUserId;
        r.message = "Registration complete. Your account is pending verification.";
        return r;
    }

    public static ResponseSendedToClient failure(String message, int step) {
        ResponseSendedToClient r = new ResponseSendedToClient();
        r.success = false;
        r.step = step;
        r.message = message;
        return r;
    }

    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getRegistrationToken() { return registrationToken; }
    public String getPublicUserId() { return publicUserId; }
    public int getStep() { return step; }
}