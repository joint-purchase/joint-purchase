package com.jointpurchases.domain.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jointpurchases.domain.security.config.JwtService;
import com.jointpurchases.domain.security.token.Token;
import com.jointpurchases.domain.security.token.TokenManagementService;
import com.jointpurchases.domain.security.token.TokenRepository;
import com.jointpurchases.domain.security.token.TokenType;
import com.jointpurchases.domain.security.user.Role;
import com.jointpurchases.domain.security.user.User;
import com.jointpurchases.domain.security.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    // 토큰관리서비스 의존성 추가
    private final TokenManagementService tokenManagementService;


    // 회원가입
    public RegisterResponse register(RegisterRequest request) {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Error: Passwords do not match!");
        }
        if(userRepository.existsByName(request.getName())){
            throw new RuntimeException("Error: Username is already taken.");
        }

        // UserDetails 상속 객체 생성
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .birth(request.getBirth())
                .address(request.getAddress())
                .phone(request.getPhone())
                .role(Role.USER)
                .build();

        // User 객체 저장
        User savedUser = userRepository.save(user);

        // user 객체 토대로 토큰 생성
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // 토큰 저장
        saveUserToken(savedUser, jwtToken);

        // 토큰을 응답에서 body에 사용할 객체에 넣어서 반환
        return RegisterResponse.builder()
                .accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    // 인증
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // DB에 토큰 저장
        revokeAllUserTokens(user);
        saveUserToken(user,jwtToken);

        // 별도 객체에 토큰 저장
        // TODO 프론트와 연결시 삭제해야 함
        tokenManagementService.storeAccessToken(user.getId().toString(), jwtToken);
        tokenManagementService.storeRefreshToken(user.getId().toString(), refreshToken);



        return AuthenticationResponse.builder()
                .accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            return;
        }
        // 토큰 추출하기

        int lengthOfHeader = 6;
        refreshToken = authHeader.substring(lengthOfHeader);

        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail).orElseThrow();


            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);

                // db 에 토큰 저장
                revokeAllUserTokens(user);
                saveUserToken(user,accessToken);

                // 별도 객체에 토큰 저장
                // TODO 프론트 연결 시 삭제필요
                tokenManagementService.storeAccessToken(user.getId().toString(), accessToken);

                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
