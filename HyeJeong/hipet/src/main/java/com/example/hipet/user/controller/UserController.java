package com.example.hipet.user.controller;

import com.example.hipet.user.dto.UserLoginDto;
import com.example.hipet.user.dto.UserSignUpDto;
import com.example.hipet.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.hipet.util.response.CustomApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    //회원가입
    @PostMapping(path = "/sign-up")
    private ResponseEntity<CustomApiResponse<?>> signUp(@RequestBody @Valid UserSignUpDto userSignUpDto){
        return userService.signUp(userSignUpDto);
    }

    //로그인
    @PostMapping(path="/login")
    private ResponseEntity<CustomApiResponse<?>> login(@RequestBody @Valid UserLoginDto userLoginDto){
        return userService.login(userLoginDto);
    }
}
