package com.afrobad.VeinLinker.users.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.afrobad.VeinLinker.common.enums.BloodGroup;
import com.afrobad.VeinLinker.common.enums.Gender;
import com.afrobad.VeinLinker.common.enums.MaritalStatus;
import com.afrobad.VeinLinker.common.enums.RhFactor;

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

    @NotNull(message = "Marital status is required")
    private MaritalStatus maritalStatus;

    // bloodGroup and rhFactor are optional — nullable in DB
    private BloodGroup bloodGroup;
    private RhFactor rhFactor;

    // -------------------------------------------------------------------------
    // Getters & Setters
    // -------------------------------------------------------------------------

    public String getRegistrationToken() { return registrationToken; }
    public void setRegistrationToken(String t) { this.registrationToken = t; }

    public String getFathersName() { return fathersName; }
    public void setFathersName(String n) { this.fathersName = n; }

    public String getMothersName() { return mothersName; }
    public void setMothersName(String n) { this.mothersName = n; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate d) { this.dateOfBirth = d; }

    public Gender getGender() { return gender; }
    public void setGender(Gender g) { this.gender = g; }

    public BigDecimal getHeight() { return height; }
    public void setHeight(BigDecimal h) { this.height = h; }

    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal w) { this.weight = w; }

    public MaritalStatus getMaritalStatus() { return maritalStatus; }
    public void setMaritalStatus(MaritalStatus m) { this.maritalStatus = m; }

    public BloodGroup getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(BloodGroup b) { this.bloodGroup = b; }

    public RhFactor getRhFactor() { return rhFactor; }
    public void setRhFactor(RhFactor r) { this.rhFactor = r; }
}