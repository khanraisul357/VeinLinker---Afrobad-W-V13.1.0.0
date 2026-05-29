package com.afrobad.VeinLinker.registrationandlogin.users.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.afrobad.VeinLinker.registrationandlogin.users.enums.BloodGroup;
import com.afrobad.VeinLinker.registrationandlogin.users.enums.Gender;
import com.afrobad.VeinLinker.registrationandlogin.users.enums.MaritalStatus;
import com.afrobad.VeinLinker.registrationandlogin.users.enums.Religion;
import com.afrobad.VeinLinker.registrationandlogin.users.enums.RhFactor;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationForm2Request {

    // -------------------------------------------------------------------------
    // registrationToken links Step 2 back to the Step 1 data stored in memory.
    // Without this token, we can't find the temporary registration session.
    // -------------------------------------------------------------------------
    @NotBlank(message = "Registration token is required")
    private String registrationToken;

    @NotBlank(message = "Father's name is required")
    private String fathersName;

    @NotBlank(message = "Mother's name is required")
    private String mothersName;

    // @Past ensures the date of birth is always in the past (can't be born tomorrow)
    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is required")
    private Gender gender;

    // Height in cm — must be realistic (e.g. 50cm to 300cm)
    @NotNull(message = "Height is required")
    @DecimalMin(value = "50.00", message = "Height must be at least 50 cm")
    @DecimalMax(value = "300.00", message = "Height must not exceed 300 cm")
    private BigDecimal height;

    // Weight in kg — must be realistic (e.g. 10kg to 500kg)
    @NotNull(message = "Weight is required")
    @DecimalMin(value = "10.00", message = "Weight must be at least 10 kg")
    @DecimalMax(value = "500.00", message = "Weight must not exceed 500 kg")
    private BigDecimal weight;

    @NotNull(message = "Religion is required")
    private Religion religion;
    
    @NotNull(message = "Marital status is required")
    private MaritalStatus maritalStatus;

    
    @NotNull(message = "Blood Group is required")
    private BloodGroup bloodGroup;
    
    @NotNull(message = "Rh Factor is required")
    private RhFactor rhFactor;

   
}