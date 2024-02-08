package com.jointpurchases.domain.security.entity;

import com.jointpurchases.domain.security.MemberRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    private String name;

    private String password;

    @Column(unique = true)
    private String email;

    private String address;
    private LocalDate birth;
    private String phone;

    @Enumerated(EnumType.STRING)
    private MemberRole role;
}
