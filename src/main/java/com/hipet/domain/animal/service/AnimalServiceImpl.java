package com.hipet.domain.animal.service;

import com.hipet.domain.user.entity.User;
import com.hipet.domain.user.repository.UserRepository;
import com.hipet.domain.animal.entity.Animal;
import com.hipet.domain.animal.entity.AnimalPhotos;
import com.hipet.domain.animal.entity.HashTag;
import com.hipet.domain.animal.enums.Gender;
import com.hipet.domain.animal.repository.AnimalRepository;
import com.hipet.domain.animal.web.dto.AnimalRequestDto;
import com.hipet.domain.animal.web.dto.GetOneAnimalResponseDto;
import com.hipet.domain.review.entity.Review;
import com.hipet.domain.review.repository.ReviewRepository;
import com.hipet.global.entity.response.CustomApiResponse;
import com.hipet.global.enums.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalPhotoServiceImpl animalPhotoService;
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    // 동물 등록
    @Override
    @Transactional
    public ResponseEntity<CustomApiResponse<?>> registerAnimals(AnimalRequestDto request) {

        if (request == null) {
            CustomApiResponse<?> response = CustomApiResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "전송 데이터 목록을 확인해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        List<AnimalPhotos> animalPhotosList = animalPhotoService.photosUpload(request.getPhotoFiles());
        String price = request.getIsPrice() ? request.getPrice() : null;

        Optional<User> getUser = userRepository.findByLoginId(request.getUserId());

        if (getUser.isEmpty()) {
            CustomApiResponse<?> response = CustomApiResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "전송 데이터 목록을 확인해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        User user = getUser.get();

        Animal animal = Animal.builder()
                .animalName(request.getAnimalName())
                .price(price)
                .category(Category.valueOf(request.getCategory()))
                .gender(Gender.valueOf(request.getGender()))
                .information(request.getDescription())
                .animalPhotos(new ArrayList<>())
                .user(user)
                .hashTag(new ArrayList<>())
                .build();

        for (AnimalPhotos animalPhoto : animalPhotosList) {
            animal.addAnimalPhoto(animalPhoto);
        }

        for (String hashTag : request.getHashtag()) {
            HashTag newTag = HashTag.builder()
                    .keyword(hashTag)
                    .build();

            animal.addHashtag(newTag);
        }

        animalRepository.save(animal);

        CustomApiResponse<?> response = CustomApiResponse.createSuccess(HttpStatus.OK.value(), null, "동물 등록이 완료되었습니다.");
        return ResponseEntity.ok(response);
    }

    // 등록한 동물 상세보기
    @Override
    @Transactional
    public ResponseEntity<CustomApiResponse<GetOneAnimalResponseDto.FinalResponseDto>> getOneAnimal(Long animalId) {
        GetOneAnimalResponseDto.FinalResponseDto getOneAnimalResponseDto = new GetOneAnimalResponseDto.FinalResponseDto();

        Optional<Animal> foundData = animalRepository.findByAnimalId(animalId);

        if (foundData.isEmpty()) {
            CustomApiResponse<GetOneAnimalResponseDto.FinalResponseDto> response = CustomApiResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "요청을 다시 확인해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Animal animal = foundData.get();

        // animal 에 담을 DTO 변환 로직
        GetOneAnimalResponseDto.GetOneAnimal getOneAnimal = new GetOneAnimalResponseDto.GetOneAnimal();
        List<GetOneAnimalResponseDto.AnimalPhotoInfo> animalPhotoInfoList = animal.getAnimalPhotos().stream()
                .map(photo -> new GetOneAnimalResponseDto.AnimalPhotoInfo(photo.getPhotoId(), photo.getPhotoUrl()))
                .collect(Collectors.toList());
        getOneAnimal.setPhotoFiles(animalPhotoInfoList);

        List<GetOneAnimalResponseDto.HashtagInfo> hashtagInfoList = animal.getHashTag().stream()
                .map(tag -> new GetOneAnimalResponseDto.HashtagInfo(tag.getHashtagId(), tag.getKeyword()))
                .collect(Collectors.toList());
        getOneAnimal.setHashtag(hashtagInfoList);

        getOneAnimal.setAnimalName(animal.getAnimalName());
        getOneAnimal.setCategory(String.valueOf(animal.getCategory()));
        getOneAnimal.setCreatedAt(animal.getCreatedAt().toLocalDate());
        getOneAnimal.setRegion(String.valueOf(animal.getRegion()));
        getOneAnimal.setInfo(animal.getInformation());

        GetOneAnimalResponseDto.GetUserInfo getUserInfo = new GetOneAnimalResponseDto.GetUserInfo();
        getUserInfo.setProfileImage(animal.getUser().getProfilePhoto());
        getUserInfo.setLoginId(animal.getUser().getLoginId());
        getUserInfo.setUserName(animal.getUser().getUserName());
        getUserInfo.setUserInfo(animal.getUser().getProfileInfo());
        getUserInfo.setTotalRateForUser(animal.getUser().getTotalUserRate());

        GetOneAnimalResponseDto.GetReview getReview = new GetOneAnimalResponseDto.GetReview();
        List<GetOneAnimalResponseDto.ReviewInfo> reviewInfoList = animal.getReviews().stream()
                .map(review -> new GetOneAnimalResponseDto.ReviewInfo(review.getReviewId(), review.getUser().getProfilePhoto(), review.getRate(), review.getText(), review.getUser().getUserName(), review.getCreatedAt().toLocalDate()))
                .collect(Collectors.toList());
        Double totalCount = animal.getReviews().stream().mapToDouble(Review::getRate).sum();
        getReview.setTotalRate(totalCount);
        getReview.setReviews(reviewInfoList);

        getOneAnimalResponseDto.setAnimal(getOneAnimal);
        getOneAnimalResponseDto.setUser(getUserInfo);
        getOneAnimalResponseDto.setReview(getReview);

        CustomApiResponse<GetOneAnimalResponseDto.FinalResponseDto> response = CustomApiResponse.createSuccess(HttpStatus.OK.value(), getOneAnimalResponseDto, "동물 조회에 성공하였습니다.");

        return ResponseEntity.ok(response);
    }
}
