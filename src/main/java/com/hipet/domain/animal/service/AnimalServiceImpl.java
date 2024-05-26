package com.hipet.domain.animal.service;

import com.hipet.domain.User.entity.Liked;
import com.hipet.domain.User.entity.User;
import com.hipet.domain.User.repository.LikedRepository;
import com.hipet.domain.User.repository.UserRepository;
import com.hipet.domain.animal.entity.Animal;
import com.hipet.domain.animal.entity.AnimalPhotos;
import com.hipet.domain.animal.entity.HashTag;
import com.hipet.domain.animal.enums.Gender;
import com.hipet.domain.animal.repository.AnimalRepository;
import com.hipet.domain.animal.repository.HashTagRepository;
import com.hipet.domain.animal.web.dto.AnimalRequestDto;
import com.hipet.domain.animal.web.dto.GetOneAnimalRequestDto;
import com.hipet.domain.animal.web.dto.GetOneAnimalResponseDto;
import com.hipet.domain.review.entity.Review;
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

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService{

    private final AnimalPhotoServiceImpl animalPhotoService;
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;
    private final HashTagRepository hashTagRepository;
    private final LikedRepository likedRepository;

    // 동물 등록
    @Override
    @Transactional
    public ResponseEntity<CustomApiResponse<?>> registerAnimals(AnimalRequestDto request) {

        if(request == null){
            CustomApiResponse<?> response = CustomApiResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "전송 데이터 목록을 확인해주세요.");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        List<AnimalPhotos> animalPhotosList = animalPhotoService.photosUpload(request.getPhotoFiles());
        String price = "";

        if(request.getIsPrice()){
            price = request.getPrice();
        }
        else{
            price = null;
        }

        Optional<User> getUser = userRepository.findByLoginId(request.getUserId());

        if(getUser.isEmpty()){
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
                // Animal Entity의 reviews와 liked 필드에 대한 값 주입은 필요하지 않은가?
                .build();

        for(AnimalPhotos animalPhoto : animalPhotosList) {
            animal.addAnimalPhoto(animalPhoto);
        }

        for(String hashTag : request.getHashtag()){
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
    public CustomApiResponse<GetOneAnimalResponseDto.FinalResponseDto> getOneAnimal(GetOneAnimalRequestDto request) {

        // 최종 반환할 응답 DTO
        GetOneAnimalResponseDto.FinalResponseDto getOneAnimalResponseDto = new GetOneAnimalResponseDto.FinalResponseDto();

        Optional<Animal> foundData = animalRepository.findByAnimalId(request.getAnimalId());

        if(foundData.isEmpty()){
            CustomApiResponse<GetOneAnimalResponseDto.FinalResponseDto> response = CustomApiResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "요청을 다시 확인해주세요.");

            return response;
        }

        Animal animal = foundData.get();

        List<AnimalPhotos> animalPhotos = animal.getAnimalPhotos();
        List<GetOneAnimalResponseDto.AnimalPhotoInfo> animalPhotoInfos = new ArrayList<>();

        for(AnimalPhotos animalPhoto : animalPhotos){
            GetOneAnimalResponseDto.AnimalPhotoInfo photoInfo = new GetOneAnimalResponseDto.AnimalPhotoInfo();
            photoInfo.setPhotoId(animalPhoto.getPhotoId());
            photoInfo.setPhotoUrl(animalPhoto.getPhotoUrl());

            animalPhotoInfos.add(photoInfo);
        }

        List<HashTag> hashTags = hashTagRepository.findALLByAnimal(animal);
        List<GetOneAnimalResponseDto.HashtagInfo> hashTagInfos = new ArrayList<>();

        for(HashTag hashTag : hashTags){
            GetOneAnimalResponseDto.HashtagInfo hashtagInfo = new GetOneAnimalResponseDto.HashtagInfo();
            hashtagInfo.setHashtagId(hashtagInfo.getHashtagId());
            hashtagInfo.setHashtagName(hashtagInfo.getHashtagName());

            hashTagInfos.add(hashtagInfo);
        }

        Boolean getIsLiked = false;
        if((animal.getUser().getLoginId()).equals(request.getLoginId())){
            getIsLiked = true;
        }

        // animal 에 담을 DTO
        GetOneAnimalResponseDto.GetOneAnimal getOneAnimal = new GetOneAnimalResponseDto.GetOneAnimal();
        getOneAnimal.setPhotoFiles(animalPhotoInfos);
        getOneAnimal.setAnimalName(animal.getAnimalName());
        getOneAnimal.setCategory(String.valueOf(animal.getCategory()));
        getOneAnimal.setCreatedAt(animal.getCreatedAt().toLocalDate());
        getOneAnimal.setRegion(String.valueOf(animal.getRegion()));
        getOneAnimal.setInfo(animal.getInformation());
        getOneAnimal.setHashtag(hashTagInfos);
        getOneAnimal.setIsLiked(getIsLiked);

        // user 에 담을 DTO
        GetOneAnimalResponseDto.GetUserInfo getUserInfo = new GetOneAnimalResponseDto.GetUserInfo();
        User user = animal.getUser();

        getUserInfo.setProfileImage(animal.getUser().getProfilePhoto());
        getUserInfo.setLoginId(animal.getUser().getLoginId());
        getUserInfo.setUserName(animal.getUser().getUserName());
        getUserInfo.setUserInfo(animal.getUser().getProfileInfo());
        getUserInfo.setTotalRateForUser(animal.getUser().getTotalUserRate());

        // review 에 담을 DTO
        GetOneAnimalResponseDto.GetReview getReview = new GetOneAnimalResponseDto.GetReview();

        List<GetOneAnimalResponseDto.ReviewInfo> reviewInfos = new ArrayList<>();
        List<Review> reviews = animal.getReviews();

        for(Review review : reviews){
            GetOneAnimalResponseDto.ReviewInfo reviewInfo = new GetOneAnimalResponseDto.ReviewInfo();
            reviewInfo.setReviewId(review.getReviewId());
            reviewInfo.setUserProfileImageUrl(review.getUserId().getProfilePhoto());
            reviewInfo.setRate(reviewInfo.getRate());
            reviewInfo.setText(reviewInfo.getText());
            reviewInfo.setUserName(reviewInfo.getUserName());
            reviewInfo.setCreatedAt(reviewInfo.getCreatedAt());

            reviewInfos.add(reviewInfo);
        }

        Double totalCount = reviews.stream().mapToDouble(Review::getRate).sum();

        getReview.setTotalRate(totalCount);
        getReview.setReviews(reviewInfos);

        getOneAnimalResponseDto.setAnimal(getOneAnimal);
        getOneAnimalResponseDto.setUser(getUserInfo);
        getOneAnimalResponseDto.setReview(getReview);

        CustomApiResponse<GetOneAnimalResponseDto.FinalResponseDto> response = CustomApiResponse.createSuccess(HttpStatus.OK.value(), getOneAnimalResponseDto, "동물 조회에 성공하였습니다.");

        return response;

    }


}
