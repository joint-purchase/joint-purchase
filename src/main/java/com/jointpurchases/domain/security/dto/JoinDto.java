package com.jointpurchases.domain.security.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class JoinDto {

    private String username;
    private String password;
    private String email;
    private String address;
    private LocalDate birthday;
    private String phone;
    private String role;
}