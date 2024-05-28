package com.hipet.domain.review.service;

import com.hipet.domain.User.entity.User;
import com.hipet.domain.User.repository.UserRepository;
import com.hipet.domain.animal.entity.Animal;
import com.hipet.domain.animal.repository.AnimalRepository;
import com.hipet.domain.review.entity.Review;
import com.hipet.domain.review.entity.ReviewImage;
import com.hipet.domain.review.repository.ReviewRepository;
import com.hipet.domain.review.web.dto.ReviewRequestDto;
import com.hipet.global.entity.response.CustomApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewImageServiceImpl reviewImageService;

    // 리뷰 등록
    @Override
    @Transactional
    public ResponseEntity<CustomApiResponse<?>> addReview(ReviewRequestDto request) {

        if(request == null) {
            log.error("Request is null");
            CustomApiResponse<?> response = CustomApiResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "전송 데이터 목록을 확인해주세요.");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        List<ReviewImage> reviewImages = reviewImageService.uploadReviewPhotos(request.getPhotoFiles());

        Optional<User> getAuthorUser = userRepository.findByLoginId(request.getLoginId());

        if(getAuthorUser.isEmpty()) {
            log.error("User with loginId {} not found", request.getLoginId());
            CustomApiResponse<?> response = CustomApiResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "작성자 정보를 불러오지 못했습니다.");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        User author = getAuthorUser.get();

        Optional<Animal> getAnimalInfo = animalRepository.findById(request.getAnimalId());

        if(getAnimalInfo.isEmpty()) {
            log.error("Animal with id {} not found", request.getAnimalId());
            CustomApiResponse<?> response = CustomApiResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "리뷰를 작성할 분양글 정보를 불러오지 못했습니다.");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Animal getAnimal = getAnimalInfo.get();

        Review newReview = Review.builder()
                .rate(request.getRate())
                .text(request.getText())
                .userId(author)
                .reviewImages(reviewImages)
                .animalId(getAnimal)
                .build();

        reviewRepository.save(newReview);

        CustomApiResponse<?> response = CustomApiResponse.createSuccess(HttpStatus.OK.value(), null, "리뷰 등록이 완료되었습니다.");

        return ResponseEntity.ok(response);
    }
}
