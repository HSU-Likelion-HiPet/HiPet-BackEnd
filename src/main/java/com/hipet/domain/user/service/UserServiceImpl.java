package com.hipet.domain.user.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.hipet.domain.animal.entity.Animal;
import com.hipet.domain.animal.repository.AnimalRepository;
import com.hipet.domain.review.entity.Review;
import com.hipet.domain.review.repository.ReviewRepository;
import com.hipet.domain.user.entity.Liked;
import com.hipet.domain.user.repository.LikedRepository;
import com.hipet.domain.user.repository.UserRepository;
import com.hipet.domain.user.web.dto.UserLoginDto;
import com.hipet.domain.user.web.dto.UserPageDto;
import com.hipet.domain.user.web.dto.UserPageUpdateDto;
import com.hipet.domain.user.web.dto.UserSignUpDto;
import com.hipet.global.aws.s3.AmazonS3Manager;
import com.hipet.global.entity.response.CustomApiResponse;
import com.hipet.global.enums.Region;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.hipet.domain.user.entity.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final ReviewRepository reviewRepository;
    private final LikedRepository likedRepository;
    private final AmazonS3Manager amazonS3Manager;

    @Transactional
    @Override
    public ResponseEntity<CustomApiResponse<?>> signUp(UserSignUpDto userSignUpDto) {
        // 회원 중복 확인
        String loginId = userSignUpDto.getLoginId();

        // 아이디 중복 확인
        Optional<User> idExistUser = userRepository.findByLoginId(loginId);
        if (idExistUser.isPresent()) {
            CustomApiResponse<Object> failResponse = CustomApiResponse
                    .createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "이미 사용 중인 아이디 입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failResponse);
        }

        // 존재하지 않으면 회원가입 진행
        User newUser = User.builder()
                .loginId(loginId)
                .password(userSignUpDto.getPassword())
                .region(Region.valueOf("_ALL"))
                .userName(userSignUpDto.getUserName())
                .profileInfo("") // 채널 소개는 기본으로 빈 문자열 생성 -> 추후 수정
                .profilePhoto("") // 채널 소개는 기본으로 빈 문자열 생성 -> 추후 수정
                .build();
        userRepository.save(newUser); // 사용자 저장

        // 회원가입 성공
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CustomApiResponse.createSuccess(HttpStatus.OK.value(), null, "회원가입에 성공하였습니다."));
    }

    @Override
    public ResponseEntity<CustomApiResponse<?>> login(UserLoginDto userLoginDto) {
        // 아이디 존재하지 않음
        Optional<User> idExistUser = userRepository.findByLoginId(userLoginDto.getLoginId());
        if (idExistUser.isEmpty()) {
            CustomApiResponse<Object> failResponse = CustomApiResponse
                    .createFailWithoutData(HttpStatus.NOT_FOUND.value(), "아이디가 존재하지 않는 회원입니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(failResponse);
        }

        // 패스워드 불일치
        if (!idExistUser.get().getPassword().equals(userLoginDto.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(CustomApiResponse.createFailWithoutData(HttpStatus.UNAUTHORIZED.value(),
                            "비밀번호가 일치하지 않습니다."));
        }

        // 로그인 성공
        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomApiResponse.createSuccess(HttpStatus.OK.value(), null, "로그인에 성공하였습니다."));
    }

    @Override
    public ResponseEntity<CustomApiResponse<?>> getUserPage(String loginId) {
        // 아이디를 가진 사용자가 존재하는지 확인
        Optional<User> idExistUser = userRepository.findByLoginId(loginId);
        if (idExistUser.isEmpty()) {
            CustomApiResponse<Object> failResponse = CustomApiResponse
                    .createFailWithoutData(HttpStatus.NOT_FOUND.value(), "아이디가 존재하지 않는 회원입니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(failResponse);
        }

        User user = idExistUser.get();

        // 사용자가 작성한 글, 받은 리뷰, 찜한 게시물 조회
        List<Animal> animals = userRepository.findByLoginId(user.getLoginId()).get().getAnimalList();
        List<Review> reviews = reviewRepository.findByUserId_UserId(user.getUserId());
        List<Liked> likes = likedRepository.findByUserId_UserId(user.getUserId());

        // UserPageDto 생성
        UserPageDto userPageDto = UserPageDto.builder()
                .userId(user.getLoginId())
                .userName(user.getUserName())
                .profileInfo(user.getProfileInfo())
                .profilePicture(user.getProfilePhoto())
                .reviews(reviews)
                .animals(animals)
                .likes(likes)
                .build();

        // 사용자가 존재한다면 사용자 정보 반환
        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomApiResponse.createSuccess(HttpStatus.OK.value(), userPageDto, "사용자 정보 조회에 성공하였습니다."));
    }

    @Transactional
    @Override
    public ResponseEntity<CustomApiResponse<?>> modifyUserPage(String loginId, UserPageUpdateDto.Req req) {
        Optional<User> idExistUser = userRepository.findByLoginId(loginId);
        if (idExistUser.isEmpty()) {
            CustomApiResponse<Object> failResponse = CustomApiResponse
                    .createFailWithoutData(HttpStatus.NOT_FOUND.value(), "아이디가 존재하지 않는 회원입니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(failResponse);
        }

        User user = idExistUser.get();
        user.changeUserName(req.getUserName());
        user.changeRegion(req.getRegion());
        user.changeProfileInfo(req.getProfileInfo());

        // 프로필 사진 업로드
        if (req.getProfilePhoto() != null && !req.getProfilePhoto().isEmpty()) {
            try {
                String profilePhotoUrl = amazonS3Manager.uploadFile(req.getProfilePhoto());
                user.changeProfilePhoto(profilePhotoUrl);
            } catch (AmazonServiceException e) {
                CustomApiResponse<Object> failResponse = CustomApiResponse
                        .createFailWithoutData(HttpStatus.INTERNAL_SERVER_ERROR.value(), "프로필 사진 업로드에 실패하였습니다: " + e.getErrorMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(failResponse);
            } catch (SdkClientException e) {
                CustomApiResponse<Object> failResponse = CustomApiResponse
                        .createFailWithoutData(HttpStatus.INTERNAL_SERVER_ERROR.value(), "프로필 사진 업로드에 실패하였습니다: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(failResponse);
            }
        }

        userRepository.flush();

        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomApiResponse.createSuccess(HttpStatus.OK.value(), null, "수정을 성공하였습니다."));
    }

    @Transactional
    @Override
    public ResponseEntity<CustomApiResponse<?>> deleteUserPostAnimal(String loginId, List<Long> animalIds) {
        Optional<User> idExistUser = userRepository.findByLoginId(loginId);
        if (idExistUser.isEmpty()) {
            CustomApiResponse<Object> failResponse = CustomApiResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "아이디가 존재하지 않는 회원입니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(failResponse);
        }

        User user = idExistUser.get();
        List<Animal> deleteAnimals = animalRepository.findAllById(animalIds);

        List<Animal> deleteOwnAnimals = deleteAnimals.stream()
                .filter(animal -> animal.getUser().equals(user))
                .collect(Collectors.toList());

        animalRepository.deleteAll(deleteOwnAnimals);
        animalRepository.flush();

        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomApiResponse.createSuccess(HttpStatus.OK.value(), null, "게시물이 삭제되었습니다."));
    }

    @Transactional
    @Override
    public ResponseEntity<CustomApiResponse<?>> deleteUserLikedAnimal(String loginId, List<Long> animalIds) {
        Optional<User> idExistUser = userRepository.findByLoginId(loginId);
        if (idExistUser.isEmpty()) {
            CustomApiResponse<Object> failResponse = CustomApiResponse
                    .createFailWithoutData(HttpStatus.NOT_FOUND.value(), "아이디가 존재하지 않는 회원입니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(failResponse);
        }

        User user = idExistUser.get();
        List<Liked> deleteLiked = likedRepository.findByUserAndAnimal(user, animalIds);
        likedRepository.deleteAll(deleteLiked);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomApiResponse.createSuccess(HttpStatus.OK.value(), null, "찜한 게시물을 삭제하였습니다."));
    }
}
