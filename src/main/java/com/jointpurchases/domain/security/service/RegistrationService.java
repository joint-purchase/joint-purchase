package com.jointpurchases.domain.security.service;

import com.jointpurchases.domain.security.dto.CheckRegistrationDto;
import com.jointpurchases.domain.security.entity.UserEntity;
import com.jointpurchases.domain.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 1. 비밀번호 불일치 여부 검증
     * 2. 계정 중복 여부 검증
     *
     */
    public void registerUser(CheckRegistrationDto checkRegistrationDto) {
        if (!checkRegistrationDto.getPassword().equals(checkRegistrationDto.getConfirmPassword())) {
            throw new RuntimeException("Error: Passwords do not match!");
        }
        if(userRepository.existsByUsername(checkRegistrationDto.getUsername())){
            throw new RuntimeException("Error: Username is already taken.");
        }

        userRepository.save(toUserEntity(checkRegistrationDto));
    }

    private UserEntity toUserEntity(CheckRegistrationDto checkRegistrationDto) {
        return UserEntity.builder()
                .username(checkRegistrationDto.getUsername())
                .password(passwordEncoder.encode(checkRegistrationDto.getPassword()))
                .email(checkRegistrationDto.getEmail())
                .address(checkRegistrationDto.getAddress())
                .birthday(checkRegistrationDto.getBirthday())
                .phone(checkRegistrationDto.getPhone())
                .role("ROLE_ADMIN") // Default role
                .build();
    }
}
