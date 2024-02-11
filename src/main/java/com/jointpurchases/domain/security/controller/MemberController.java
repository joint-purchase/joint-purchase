package com.jointpurchases.domain.security.controller;


import com.jointpurchases.domain.security.http.Response;
import com.jointpurchases.domain.security.model.RegisterDto;
import com.jointpurchases.domain.security.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/security/members")
public class MemberController {

    private final MemberService memberService;



    // 회원가입
    @PostMapping("/register")
    public Response<?> register(@RequestBody RegisterDto registerDto) {
        return new Response<>("true", "가입 성공", memberService.register(registerDto));
    }

}
