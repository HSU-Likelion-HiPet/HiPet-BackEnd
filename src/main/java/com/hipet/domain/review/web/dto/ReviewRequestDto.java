package com.hipet.domain.review.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ReviewRequestDto {
    private String loginId;
    private Double rate;
    private List<MultipartFile> photoFiles;
    private String text;
    private Long animalId;
}
