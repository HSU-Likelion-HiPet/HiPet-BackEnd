package com.hipet.domain.animal.web.dto;

import com.hipet.domain.animal.entity.AnimalPhotos;
import com.hipet.domain.animal.entity.HashTag;
import com.hipet.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


public class GetOneAnimalResponseDto {

    // 최종적으로 반환할 DTO
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FinalResponseDto {
        private GetOneAnimal animal;
        private GetUserInfo user;
        private GetReview review;
    }

    // 동물 정보를 담은 DTO
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetOneAnimal {
        private List<AnimalPhotos> photoFiles;
        private String animalName;
        private String category;
        private String price;
        private LocalDate createdAt;
        private String region;
        private String info;
        private List<HashTag> hashtag;
    }

    // 게시자의 정보를 담은 DTO
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetUserInfo{
        private String profileImage;
        private String loginId;
        private String userName;
        private String userInfo;
        private Double totalRateForUser; // 당근온도같은?
    }

    // 분양후기 패널에 쓰일 정보들을 담은 DTO
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetReview{
        private Double totalRate;
        private List<Review> reviews;
    }

}
