package com.example.hipet.user.service;

import com.example.hipet.domain.User;
import com.example.hipet.user.dto.UserSignUpDto;
import com.example.hipet.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import util.response.CustomApiResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional //삽입 연산 -> 트랜잭션 안에서
    public ResponseEntity<CustomApiResponse<?>> signUp(UserSignUpDto userSignUpDto) {
        // 회원 중복 확인
        String loginId=userSignUpDto.getLoginId();
        String nickname=userSignUpDto.getNickname();

        //아이디 중복
        Optional<User> idExistUser =userRepository.findByLoginId(loginId);
        if(idExistUser.isPresent()){
            CustomApiResponse<Object> failResponse=CustomApiResponse
                    .createFailWithOut(HttpStatus.BAD_REQUEST.value(), "이미 사용 중인 아이디 입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failResponse);
        }

        //닉네임 중복
        Optional<User> nicknameExistUser =userRepository.findByNickname(nickname);
        if(nicknameExistUser.isPresent()){
            CustomApiResponse<Object> failResponse=CustomApiResponse
                    .createFailWithOut(HttpStatus.BAD_REQUEST.value(), "이미 사용 중인 닉네임 입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failResponse);
        }

        //존재하지 않으면 회원가입 진행
        User newUser=User.builder()
                .loginId(loginId)
                .nickname(nickname)
                .password(userSignUpDto.getPassword())
                .phoneNumber(userSignUpDto.getPhoneNumber())
                .address(userSignUpDto.getAddress())
                .profileInfo("") // 채널 소개는 기본으로 빈 문자열 생성 -> 추후 수정
                .build();
        userRepository.save(newUser); //사용자 저장

        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CustomApiResponse.createSuccess(HttpStatus.OK.value(), null,"회원가입에 성공하였습니다."));


    }
}