package com.jointpurchases.domain.security.contoller;

import com.jointpurchases.domain.security.dto.CheckedRegistrationDto;
import com.jointpurchases.domain.security.dto.ConfirmRegistrationDto;
import com.jointpurchases.domain.security.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody ConfirmRegistrationDto confirmRegistrationDto) {
        if (!confirmRegistrationDto.getPassword().equals(confirmRegistrationDto.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Error: Passwords do not match!");
        }

        // 비밀번호 일치 확인 후 사용자 생성 로직
        CheckedRegistrationDto user
                = CheckedRegistrationDto.builder()
                .username(confirmRegistrationDto.getUsername())
                .password(confirmRegistrationDto.getPassword())
                .phone(confirmRegistrationDto.getPhone())
                .address(confirmRegistrationDto.getAddress())
                .email(confirmRegistrationDto.getEmail())
                .birthday(confirmRegistrationDto.getBirthday())
                .build();

        registrationService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
