package com.jointpurchases.domain.auth.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record SignupRequestDto(

        @Size(min = 3, max = 10, message = "3자 이상 10자 이하여야 합니다.")
        @NotNull(message = "이름을 입력해주세요.")
        String username,

        @Size(min = 8, max = 15, message = "8자 이상 15자 이하여야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9]*$")
        String password,

        @Email(message = "이메일 형식이 맞지 않습니다.")
        @NotNull(message = "이메일을 입력해주세요.")
        String email,

        @NotNull(message = "주소를 입력해주세요.")
        String address,

        @NotNull(message = "생년월일을 입력해주세요.")
        @JsonFormat(pattern = "yyyyMMdd")
        LocalDate birth,

        @NotNull(message = "전화번호를 입력해주세요.")
        String phone,

        boolean admin,

        String adminToken
) {
}
