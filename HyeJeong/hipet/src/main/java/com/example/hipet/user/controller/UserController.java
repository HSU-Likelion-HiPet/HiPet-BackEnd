package com.example.hipet.user.controller;

import com.example.hipet.user.dto.UserSignUpDto;
import com.example.hipet.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.response.CustomApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/sign-up")
    private ResponseEntity<CustomApiResponse<?>> signUp(@RequestBody @Valid UserSignUpDto userSignUpDto){
        return userService.signUp(userSignUpDto);
    }
}
