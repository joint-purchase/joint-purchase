package com.jointpurchases.domain.security.user;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "일반회원"),
    SELLER("ROLE_SELLER", "판매회원");


    UserRole(String value, String description) {
        this.value = value;
        this.description = description;
    }

    private final String value;
    private final String description;
}
