package com.hipet.domain.search.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class SearchResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FinalResponseDto{
        private String keyword;
        private int totalNum;
        private List<AnimalListInfo> animalListInfo;
        /*private Pagination pagination;*/
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnimalListInfo{
        private Long animalId;
        private String photo;
        private String animalName;
        private String price;
        private String region;
        private List<Hashtags> hashtag;
        private LocalDate createdAt;
    }

/*    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pagination{
        private int currentPage;
        private int totalPages;
        private int pageSize;
        private int totalItems;
    }*/

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Hashtags{
        private Long hashtagId;
        private String tag;
    }
}
