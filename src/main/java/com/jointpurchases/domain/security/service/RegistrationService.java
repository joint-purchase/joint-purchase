package com.jointpurchases.domain.security.service;

import com.jointpurchases.domain.security.user.User;
import com.jointpurchases.domain.security.model.CheckRegistrationDto;
import com.jointpurchases.domain.security.user.UserRole;
import com.jointpurchases.domain.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(CheckRegistrationDto checkRegistrationDto) {
        if (!checkRegistrationDto.getPassword().equals(checkRegistrationDto.getConfirmPassword())) {
            throw new RuntimeException("Error: Passwords do not match!");
        }
        if(userRepository.existsByName(checkRegistrationDto.getUsername())){
            throw new RuntimeException("Error: Username is already taken.");
        }

        userRepository.save(toUserEntity(checkRegistrationDto));
    }

    private User toUserEntity(CheckRegistrationDto checkRegistrationDto) {
        return User.builder()
                .name(checkRegistrationDto.getUsername())
                .password(passwordEncoder.encode(checkRegistrationDto.getPassword()))
                .email(checkRegistrationDto.getEmail())
                .address(checkRegistrationDto.getAddress())
                .birth(checkRegistrationDto.getBirthday())
                .phone(checkRegistrationDto.getPhone())
                .role(UserRole.ADMIN) // Default role
                .build();
    }

}
