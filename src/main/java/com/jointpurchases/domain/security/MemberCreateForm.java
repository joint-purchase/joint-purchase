package com.jointpurchases.domain.security;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberCreateForm {

    @Size(min =3, max =25)
    @NotEmpty(message = "사용자 ID는 필수항목입니다.")
    private String name;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String checkPassword;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    private String email;

    @NotEmpty(message = "주소는 필수항목입니다.")
    private String address;

    @NotNull(message = "생일은 필수항목입니다.")
    private LocalDate birth;

    @NotNull(message = "전화번호는 필수항목입니다.")
    private String phone;

    @NotNull(message="회원 유형 선택은 필수입니다.")
    private String role;
}
