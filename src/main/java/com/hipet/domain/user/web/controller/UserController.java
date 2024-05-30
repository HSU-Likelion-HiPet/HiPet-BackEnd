package com.hipet.domain.user.web.controller;

import com.hipet.domain.user.web.dto.UserLoginDto;
import com.hipet.domain.user.web.dto.UserPageUpdateDto;
import com.hipet.domain.user.web.dto.UserSignUpDto;
import com.hipet.domain.user.service.UserServiceImpl;
import com.hipet.global.entity.response.CustomApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
    private final UserServiceImpl userService;

    //회원가입
    @PostMapping(path = "/signUp")
    private ResponseEntity<CustomApiResponse<?>> signUp(@RequestBody @Valid UserSignUpDto userSignUpDto){
        return userService.signUp(userSignUpDto);
    }

    //로그인
    @PostMapping(path="/login")
    private ResponseEntity<CustomApiResponse<?>> login(@RequestBody @Valid UserLoginDto userLoginDto){
        return userService.login(userLoginDto);
    }


    //특정 사용자의 페이지(채널) 조회
    @GetMapping("/{loginId}")
    public ResponseEntity<CustomApiResponse<?>> getUserPage(@PathVariable  String loginId){
        ResponseEntity<CustomApiResponse<?>> result =userService.getUserPage(loginId);
        return result;
    }

    // 나의 페이지(채널) 수정
    @PutMapping("/{loginId}")
    public ResponseEntity<CustomApiResponse<?>> modifyUserPage(
            @PathVariable String loginId,
            @ModelAttribute @Valid UserPageUpdateDto.Req req) {
        ResponseEntity<CustomApiResponse<?>> result = userService.modifyUserPage(loginId, req);
        return result;
    }

    //내가 등록한 동물 글 삭제
    @DeleteMapping("/{loginId}/animal")
    public ResponseEntity<CustomApiResponse<?>> deleteAnimal(@PathVariable String loginId, @RequestBody Map<String, List<Long>> animalIds) {
        List<Long> ids=animalIds.get("animalIds");
        return userService.deleteUserPostAnimal(loginId, ids);
    }


    //내가 찜한 게시글 삭제
    @DeleteMapping("/{loginId}/liked")
    public ResponseEntity<CustomApiResponse<?>> deleteLiked(@PathVariable String loginId, @RequestBody Map<String, List<Long>> animalIds) {
        List<Long> ids=animalIds.get("animalIds");
        return userService.deleteUserLikedAnimal(loginId, ids);
    }




}
