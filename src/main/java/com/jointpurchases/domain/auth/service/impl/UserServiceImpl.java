package com.jointpurchases.domain.auth.service.impl;


import com.jointpurchases.domain.auth.exception.UserException;
import com.jointpurchases.domain.auth.model.dto.SignupRequestDto;
import com.jointpurchases.domain.auth.model.entity.User;
import com.jointpurchases.domain.auth.model.type.UserRole;
import com.jointpurchases.domain.auth.repository.UserRepository;
import com.jointpurchases.domain.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.jointpurchases.global.exception.ErrorCode.DUPLICATE_EMAIL;
import static com.jointpurchases.global.exception.ErrorCode.INVALID_ADMIN_PASSWORD;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.token}")
    private String ADMIN_TOKEN;

    @Override
    public Long signup(final SignupRequestDto requestDto) {

        emailDuplicateCheck(requestDto.email());

        UserRole role = userRoleCheck(requestDto);
        String password = passwordEncoder.encode(requestDto.password());

        User user = userRepository.save( User.createUser(requestDto, password, role));
        return user.getId();
    }


    private void emailDuplicateCheck(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
                    throw new UserException(DUPLICATE_EMAIL);
                });
    }

    private UserRole userRoleCheck(SignupRequestDto requestDto) {
        if (requestDto.admin()) {
            if (!ADMIN_TOKEN.equals(requestDto.adminToken())) {
                throw new UserException(INVALID_ADMIN_PASSWORD);
            }
            return UserRole.ADMIN;
        }
        return UserRole.USER;
    }



}