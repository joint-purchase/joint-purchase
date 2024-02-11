package com.jointpurchases.domain.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jointpurchases.domain.security.model.MemberRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonIgnore // 컨트롤러에서 반환시 비밀번호 숨겨짐
    private String password;

    private String address;
    private LocalDate birth;
    private String phone;

    @Enumerated(EnumType.STRING)
    private MemberRole role;
}
