package com.hipet.domain.User.service;

import com.hipet.domain.User.entity.User;
import com.hipet.domain.User.repository.UserRepository;
import com.hipet.domain.User.web.dto.UserLoginDto;
import com.hipet.domain.User.web.dto.UserPageUpdateDto;
import com.hipet.domain.User.web.dto.UserSignUpDto;
import com.hipet.global.entity.response.CustomApiResponse;
import com.hipet.global.enums.Region;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Transactional//삽입 연산 -> 트랜잭션 안에서
    @Override
    public ResponseEntity<CustomApiResponse<?>> signUp(UserSignUpDto userSignUpDto) {
        // 회원 중복 확인
        String loginId=userSignUpDto.getLoginId();

        //아이디 중복
        Optional<User> idExistUser =userRepository.findByLoginId(loginId);
        if(idExistUser.isPresent()){
            CustomApiResponse<Object> failResponse=CustomApiResponse
                    .createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "이미 사용 중인 아이디 입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failResponse);
        }

        //존재하지 않으면 회원가입 진행
        User newUser=User.builder()
                .loginId(loginId)
                .password(userSignUpDto.getPassword())
                .address(Region.valueOf(userSignUpDto.getAddress()))
                .userName(userSignUpDto.getUserName())
                .profileInfo("") // 채널 소개는 기본으로 빈 문자열 생성 -> 추후 수정
                .profilePhoto("") // 채널 소개는 기본으로 빈 문자열 생성 -> 추후 수정
                .build();
        userRepository.save(newUser); //사용자 저장

        //회원가입 성공
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CustomApiResponse.createSuccess(HttpStatus.OK.value(), null,"회원가입에 성공하였습니다."));


    }

    @Override //로그인
    public ResponseEntity<CustomApiResponse<?>> login(UserLoginDto userLoginDto) {

        //아이디 존재하지 않음
        Optional<User> idExistUser =userRepository.findByLoginId(userLoginDto.getLoginId());
        if(idExistUser.isEmpty()){
            CustomApiResponse<Object> failResponse=CustomApiResponse
                    .createFailWithoutData(HttpStatus.NOT_FOUND.value(), "아이디가 존재하지 않는 회원입니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(failResponse);
        }

        //패스워드 불일치
        if(!idExistUser.get().getPassword().equals(userLoginDto.getPassword())){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(CustomApiResponse.createFailWithoutData(HttpStatus.UNAUTHORIZED.value(),
                            "비밀번호가 일치하지 않습니다."));
        }

        //로그인 성공
        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomApiResponse.createSuccess(HttpStatus.OK.value(), null,"로그인에 성공하였습니다."));
    }

    //사용자 정보 => 페이지 (채널) 조회
    //찜 목록, 작성 게시글 및 후기 목록은 다른 걸 구현해야 개발할 수 있음
    @Override
    public ResponseEntity<CustomApiResponse<?>> getUserPage(String loginId) {

        //아이디를 가진 사용자가 먼저 존재하는지 확인
        Optional<User> idExistUser =userRepository.findByLoginId(loginId);
        if(idExistUser.isEmpty()){
            CustomApiResponse<Object> failResponse=CustomApiResponse
                    .createFailWithoutData(HttpStatus.NOT_FOUND.value(),"아이디가 존재하지 않는 회원입니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(failResponse);
        }

        //사용자가 존재한다면 사용자 정보 반환
        User user=idExistUser.get();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomApiResponse.createSuccess(HttpStatus.OK.value(), user,"사용자 정보 조회에 성공하였습니다."));


    }

    @Override //수정
    public ResponseEntity<CustomApiResponse<?>> modifyUserPage(String loginId, UserPageUpdateDto.Req req) {
        //아이디를 가진 사용자가 먼저 존재하는지 확인
        Optional<User> idExistUser =userRepository.findByLoginId(loginId);
        if(idExistUser.isEmpty()){
            CustomApiResponse<Object> failResponse=CustomApiResponse
                    .createFailWithoutData(HttpStatus.NOT_FOUND.value(), "아이디가 존재하지 않는 회원입니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(failResponse);
        }

        //사용자 정보 업데이트
        User user =idExistUser.get();
        user.changeUserName(req.getUserName());
        user.changeAddress(Region.valueOf(req.getAddress()));
        user.changeProfileInfo(req.getProfileInfo());
        user.changeProfilePhoto(req.getProfilePhoto());
        userRepository.flush(); //변경사항을 데이터베이스에 즉시 적용

        // 수정된 게시글 정보 응답
        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomApiResponse.createSuccess(HttpStatus.OK.value(), null,"수정을 성공하였습니다."));




    }
}
