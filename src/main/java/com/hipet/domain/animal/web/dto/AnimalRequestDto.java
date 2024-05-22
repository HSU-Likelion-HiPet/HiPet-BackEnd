package com.hipet.domain.animal.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalRequestDto {
    private String userId;
    private String animalName;
    private boolean isPrice;
    private String price;
    private String category;
    private String gender;
    private String description;
    private List<String> hashtag;
    private List<MultipartFile> photoFiles;
}
