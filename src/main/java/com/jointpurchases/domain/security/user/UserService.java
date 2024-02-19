package com.jointpurchases.domain.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    /**
     * 비밀번호 변경 메서드
     * @param changePasswordRequest : 비밀번호 변경 요청
     * @param connectedUser : 현재 로그인한 사용자 정보
     */
    public void changePassword(ChangePasswordRequest changePasswordRequest, Principal connectedUser) {

        // 사용자 정보 저장
        var user = ((User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal());

        // 현재 입력한 비밀번호가 맞는 지 확인하기
        if(!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())){
            throw new IllegalStateException("입력한 비밀번호가 잘못되었습니다.");
        }
        // 암호 두개가 일치하는 지 확인
        if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmationPassword())){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        // 암호 재설정
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }
}
