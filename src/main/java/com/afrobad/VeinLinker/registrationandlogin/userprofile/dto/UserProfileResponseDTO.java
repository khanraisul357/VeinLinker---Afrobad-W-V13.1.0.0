package com.afrobad.VeinLinker.registrationandlogin.userprofile.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.afrobad.VeinLinker.registrationandlogin.users.enums.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserProfileResponseDTO {

	private String publicUserId;

    private String fullName;

    private String email;

    private String phone;

    private String fathersName;

    private String mothersName;

    private LocalDate dob;

    private int age;

    private String gender;

    private BigDecimal height;

    private BigDecimal weight;

    private String religion;

    private String maritalStatus;

    private String bloodGroup;

    private String rhFactor;

    // Do NOT include role here for normal user profile

}