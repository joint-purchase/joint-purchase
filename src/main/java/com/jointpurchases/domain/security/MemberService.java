package com.jointpurchases.domain.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(MemberCreateForm memberCreateForm) {

        return memberRepository.save(Member.builder()
                .name(memberCreateForm.getName())
                .password(passwordEncoder.encode(memberCreateForm.getPassword()))
                .email(memberCreateForm.getEmail())
                .birth(memberCreateForm.getBirth())
                .phone(memberCreateForm.getPhone())
                .address(memberCreateForm.getAddress())
                .build());
    }
}
