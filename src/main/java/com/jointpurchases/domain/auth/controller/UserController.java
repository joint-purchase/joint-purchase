package com.jointpurchases.domain.auth.controller;


import com.jointpurchases.domain.auth.model.dto.SignupRequestDto;
import com.jointpurchases.domain.auth.service.UserService;
import com.jointpurchases.global.common.ServiceResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequestDto requestDto){
        return ResponseEntity.ok()
                .body(ServiceResult.success("회원가입을 축하드립니다.",
                        userService.signup(requestDto)));
    }


}