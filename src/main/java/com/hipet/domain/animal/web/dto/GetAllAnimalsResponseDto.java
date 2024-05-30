package com.hipet.domain.animal.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class GetAllAnimalsResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FinalResponseDto{
        private int totalCount;
        private List<GetAnimalInfos> allAnimals;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetAnimalInfos{
        private Long animalId;
        private String image;
        private String animalName;
        private List<Hashtag> hashtag;
        private String price;
        private String region;
        private LocalDate createdAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Hashtag{
        private Long hashtagId;
        private String keyword;
    }

}
