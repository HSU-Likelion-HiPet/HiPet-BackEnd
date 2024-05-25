package com.hipet.domain.animal.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class AnimalRequestDto {
    private String userId;
    private String animalName;
    private Boolean isPrice;
    private String price;
    private String category;
    private String gender;
    private String description;
    private List<String> hashtag;
    private List<MultipartFile> photoFiles;
}
