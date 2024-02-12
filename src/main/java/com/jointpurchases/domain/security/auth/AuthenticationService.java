package com.jointpurchases.domain.security.auth;

import com.jointpurchases.domain.security.config.JwtService;
import com.jointpurchases.domain.security.user.Role;
import com.jointpurchases.domain.security.user.User;
import com.jointpurchases.domain.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;


    // 회원가입
    public AuthenticationResponse register(RegisterRequest request) {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Error: Passwords do not match!");
        }
        if(userRepository.existsByName(request.getName())){
            throw new RuntimeException("Error: Username is already taken.");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .birth(request.getBirth())
                .address(request.getAddress())
                .phone(request.getPhone())
                .role(Role.USER)
                .build();
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }

    // 인증
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }
}
