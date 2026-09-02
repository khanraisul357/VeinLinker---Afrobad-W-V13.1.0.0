package com.afrobad.VeinLinker.registrationandlogin.userprofile.dto;
import com.afrobad.VeinLinker.registrationandlogin.users.enums.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileUpdateRequestDTO {
	private String fullName;

    private String fathersName;

    private String mothersName;

    private LocalDate dob;

    private Gender gender;

    private BigDecimal height;

    private BigDecimal weight;

    private Religion religion;

    private MaritalStatus maritalStatus;

    private BloodGroup bloodGroup;

    private RhFactor rhFactor;
}