package com.jointpurchases.domain.auth.security;


import com.jointpurchases.domain.auth.exception.UserException;
import com.jointpurchases.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.jointpurchases.global.exception.ErrorCode.NOT_FOUND_EMAIL;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)  {
        return new UserDetailsImpl(userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(NOT_FOUND_EMAIL)));
    }
}