package com.afrobad.VeinLinker.adminlogin.adminauthentication.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class AdminLoginRequestDTO {

	private String email;
    private String password;
}
