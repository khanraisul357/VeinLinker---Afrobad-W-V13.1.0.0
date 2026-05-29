package com.afrobad.VeinLinker.registrationandlogin.users.dto;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationForm1Request {

    // -------------------------------------------------------------------------
    // @NotBlank → fails if null, empty string, or only whitespace.
    // @Email    → validates proper email format (e.g. user@example.com).
    // @Size     → enforces min/max length at the validation layer.
    // @Pattern  → regex-based validation for phone number format.
    // These annotations are enforced by @Valid in the Controller.
    // -------------------------------------------------------------------------

    @NotBlank(message = "Full name is required")
    @Size(max = 255, message = "Full name must not exceed 255 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(
        regexp = "^\\+?[0-9]{7,15}$",
        message = "Phone number must be 7-15 digits, optionally starting with +"
    )
    private String phone;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    // confirmPassword is validated in the Service layer (password == confirmPassword)
    // NOT in DB — it's just a form field check, never saved.
    @NotBlank(message = "Please confirm your password")
    private String confirmPassword;


}

// -------------------------------------------------------------------------
// Manual Getters & Setters
// (Avoid @Data on DTOs to prevent Lombok @EqualsAndHashCode issues later)
// -------------------------------------------------------------------------

//public String getFullName() { return fullName; }
//public void setFullName(String fullName) { this.fullName = fullName; }
//
//public String getEmail() { return email; }
//public void setEmail(String email) { this.email = email; }
//
//public String getPhone() { return phone; }
//public void setPhone(String phone) { this.phone = phone; }
//
//public String getPassword() { return password; }
//public void setPassword(String password) { this.password = password; }
//
//public String getConfirmPassword() { return confirmPassword; }
//public void setConfirmPassword(String confirmPassword) {
//    this.confirmPassword = confirmPassword;
//}