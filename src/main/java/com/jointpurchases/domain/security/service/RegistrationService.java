package com.jointpurchases.domain.security.service;

import com.jointpurchases.domain.security.dto.CheckedRegistrationDto;
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

    public void registerUser(CheckedRegistrationDto checkedRegistrationDto) {
        if(userRepository.existsByUsername(checkedRegistrationDto.getUsername())){
            throw new RuntimeException("Error: Username is already taken.");
        }

        userRepository.save(UserEntity.builder()
                .username(checkedRegistrationDto.getUsername())
                .password(passwordEncoder.encode(checkedRegistrationDto.getPassword()))
                .email(checkedRegistrationDto.getEmail())
                .address(checkedRegistrationDto.getAddress())
                .birthday(checkedRegistrationDto.getBirthday())
                .phone(checkedRegistrationDto.getPhone())
                .role("ROLE_ADMIN") // Default role
                .build());
    }
}
