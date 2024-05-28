package com.hipet.domain.animal.web.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;


public class GetOneAnimalResponseDto {

    // 최종적으로 반환할 DTO
    @Setter
    @Getter
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
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetOneAnimal {
        private List<AnimalPhotoInfo> photoFiles;
        private String animalName;
        private String category;
        private String price;
        private LocalDate createdAt;
        private String region;
        private String info;
        private List<HashtagInfo> hashtag;
        private Boolean isLiked;
    }

    @Setter
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnimalPhotoInfo {
        private Long photoId;
        private String photoUrl;
    }

    @Setter
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HashtagInfo {
        private Long hashtagId;
        private String hashtagName;
    }

    // 게시자의 정보를 담은 DTO
    @Setter
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetUserInfo{
        private String profileImage;
        private String loginId;
        private String userName;
        private String userInfo;
        private Double totalRateForUser;
    }

    // 분양후기 패널에 쓰일 정보들을 담은 DTO
    @Setter
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetReview{
        private Double totalRate;
        private List<ReviewInfo> reviews;
    }

    @Setter
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewInfo {
        private Long reviewId;
        private String userProfileImageUrl;
        private Double rate;
        private String text;
        private String userName;
        private LocalDate createdAt;
    }

}
