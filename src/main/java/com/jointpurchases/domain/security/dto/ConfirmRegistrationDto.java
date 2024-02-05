package com.jointpurchases.domain.security.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ConfirmRegistrationDto {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String role;
    private String address;

    private LocalDate birthday;
    private String phone;
}