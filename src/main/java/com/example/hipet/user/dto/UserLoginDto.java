package com.example.hipet.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UserLoginDto {
    @NotEmpty(message = "아이디가 필요합니다.")
    private String loginId;

    @NotEmpty(message = "비밀번호가 필요합니다.")
    private String password;
}
