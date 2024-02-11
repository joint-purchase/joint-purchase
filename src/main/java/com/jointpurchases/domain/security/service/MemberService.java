package com.jointpurchases.domain.security.service;

import com.jointpurchases.domain.security.entity.Member;
import com.jointpurchases.domain.security.model.MemberRole;
import com.jointpurchases.domain.security.model.RegisterDto;
import com.jointpurchases.domain.security.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 일반회원 저장
    public Member register(RegisterDto registerDto){


        if(memberRepository.findByName(registerDto.getName()).isPresent()){
            throw new RuntimeException("중복 된 계정이 존재합니다.");
        }
        if(!registerDto.getPassword().equals(registerDto.getConfirmPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        return memberRepository.save(toMember(registerDto));
    }



    // 기본회원 설정: 회원정보 저장
    private Member toMember(RegisterDto registerDto) {
        return Member.builder()
                .name(registerDto.getName())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .address(registerDto.getAddress())
                .birth(registerDto.getBirth())
                .phone(registerDto.getPhone())
                .role(MemberRole.USER)
                .build();
    }
}
