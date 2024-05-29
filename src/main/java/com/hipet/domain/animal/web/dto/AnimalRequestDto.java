package com.hipet.domain.animal.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hipet.domain.animal.enums.Gender;
import com.hipet.global.enums.Category;
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
    @JsonProperty("isPrice")
    private Boolean isPrice;
    private String price;
    private Category category;
    private Gender gender;
    private String description;
    private List<String> hashtag;
    private List<MultipartFile> photoFiles;
}
