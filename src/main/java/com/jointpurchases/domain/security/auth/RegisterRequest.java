package com.jointpurchases.domain.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String name;

    private String password;

    private String confirmPassword;

    private String email;

    private String role;

    private String address;

    private LocalDate birth;

    private String phone;

}

