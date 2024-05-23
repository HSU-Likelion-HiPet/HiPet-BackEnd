package com.example.hipet.user.service;

import com.example.hipet.user.dto.UserLoginDto;
import com.example.hipet.user.dto.UserPageUpdateDto;
import com.example.hipet.user.dto.UserSignUpDto;
import com.example.hipet.util.response.CustomApiResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    //회원가입
    ResponseEntity<CustomApiResponse<?>> signUp(UserSignUpDto userSignUpDto);

    //로그인
    ResponseEntity<CustomApiResponse<?>> login(UserLoginDto userLoginDto);

    //사용자 페이지 조회
    ResponseEntity<CustomApiResponse<?>> getUserPage(String loginId);

    //사용자 페이지 수정
    ResponseEntity<CustomApiResponse<?>> modifyUserPage(String loginId, UserPageUpdateDto.Req req);
}
