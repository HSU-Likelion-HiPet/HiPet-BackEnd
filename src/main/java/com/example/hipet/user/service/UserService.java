package com.example.hipet.user.service;

import com.example.hipet.domain.User;
import com.example.hipet.user.dto.UserLoginDto;
import com.example.hipet.user.dto.UserSignUpDto;
import com.example.hipet.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.hipet.util.response.CustomApiResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional //삽입 연산 -> 트랜잭션 안에서
    public ResponseEntity<CustomApiResponse<?>> signUp(UserSignUpDto userSignUpDto) {
        // 회원 중복 확인
        String loginId=userSignUpDto.getLoginId();

        //아이디 중복
        Optional<User> idExistUser =userRepository.findByLoginId(loginId);
        if(idExistUser.isPresent()){
            CustomApiResponse<Object> failResponse=CustomApiResponse
                    .createFailWithOut(HttpStatus.BAD_REQUEST.value(), "이미 사용 중인 아이디 입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failResponse);
        }

        //존재하지 않으면 회원가입 진행
        User newUser=User.builder()
                .loginId(loginId)
                .password(userSignUpDto.getPassword())
                .address(userSignUpDto.getAddress())
                .userName(userSignUpDto.getUserName())
                .profileInfo("") // 채널 소개는 기본으로 빈 문자열 생성 -> 추후 수정
                .profilePhoto("") // 채널 소개는 기본으로 빈 문자열 생성 -> 추후 수정
                .build();
        userRepository.save(newUser); //사용자 저장

        //회원가입 성공
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CustomApiResponse.createSuccess(HttpStatus.OK.value(), null,"회원가입에 성공하였습니다."));


    }

    //로그인
    public ResponseEntity<CustomApiResponse<?>> login(UserLoginDto userLoginDto) {

        //아이디 존재하지 않음
        Optional<User> idExistUser =userRepository.findByLoginId(userLoginDto.getLoginId());
        if(idExistUser.isEmpty()){
            CustomApiResponse<Object> failResponse=CustomApiResponse
                    .createFailWithOut(HttpStatus.NOT_FOUND.value(), "아이디가 존재하지 않는 회원입니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(failResponse);
        }

        //패스워드 불일치
        if(!idExistUser.get().getPassword().equals(userLoginDto.getPassword())){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(CustomApiResponse.createFailWithOut(HttpStatus.UNAUTHORIZED.value(),
                            "비밀번호가 일치하지 않습니다."));
        }

        //로그인 성공
        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomApiResponse.createSuccess(HttpStatus.OK.value(), null,"로그인에 성공하였습니다."));
    }
}
