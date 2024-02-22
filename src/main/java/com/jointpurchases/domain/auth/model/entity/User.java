package com.jointpurchases.domain.auth.model.entity;


import com.jointpurchases.domain.auth.model.dto.SignupRequestDto;
import com.jointpurchases.domain.auth.model.type.UserRole;
import com.jointpurchases.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Builder
    public User(String email, String username, String password, LocalDate birth,
                  String address, String phone, UserRole role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.birth = birth;
        this.address = address;
        this.phone = phone;
        this.role = role;
    }

    public static User createUser(SignupRequestDto requestDto, String password, UserRole role) {
        return User.builder()
                .username(requestDto.username())
                .password(password)
                .email(requestDto.email())
                .address(requestDto.address())
                .birth(requestDto.birth())
                .phone(requestDto.phone())
                .role(role)
                .build();
    }

}