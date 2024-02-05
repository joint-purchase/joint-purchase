package com.jointpurchases.domain.security.contoller;

import com.jointpurchases.domain.security.dto.CheckRegistrationDto;
import com.jointpurchases.domain.security.dto.RequestRegistrationDto;
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
    public ResponseEntity<?> registerUser(@RequestBody RequestRegistrationDto requestRegistrationDto) {

        registrationService.registerUser(toCheckedRegistrationDto(requestRegistrationDto));
        return ResponseEntity.ok("User registered successfully");
    }

    // DTO 매핑
    private static CheckRegistrationDto toCheckedRegistrationDto(RequestRegistrationDto requestRegistrationDto) {
        return CheckRegistrationDto.builder()
                .username(requestRegistrationDto.getUsername())
                .password(requestRegistrationDto.getPassword())
                .confirmPassword(requestRegistrationDto.getConfirmPassword())
                .phone(requestRegistrationDto.getPhone())
                .address(requestRegistrationDto.getAddress())
                .email(requestRegistrationDto.getEmail())
                .birthday(requestRegistrationDto.getBirthday())
                .build();
    }
}
