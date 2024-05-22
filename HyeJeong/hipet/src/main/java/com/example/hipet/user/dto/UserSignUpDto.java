package com.example.hipet.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UserSignUpDto {
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디 형식을 맞춰주세요")
    @NotEmpty(message = "아이디가 필요합니다.")
    private String loginId;

    @NotEmpty(message = "비밀번호가 필요합니다.")
    private String password;

    @Pattern(regexp = "^010\\d{8}$", message = "전화번호 형식을 맞춰주세요.")
    @NotEmpty(message = "전화번호가 필요합니다.")
    private String phoneNumber;

    @NotEmpty(message = "주소가 필요합니다.")
    private String address;

    @NotEmpty(message = "닉네임이 필요합니다.")
    private String nickname;
}
