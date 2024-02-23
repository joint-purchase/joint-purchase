package com.jointpurchases.domain.auth.service;


import com.jointpurchases.domain.auth.model.dto.SignupRequestDto;

public interface UserService {
    Long signup(SignupRequestDto requestDto);

}