package com.hipet.domain.animal.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetOneAnimalRequestDto {
    private Long animalId;
    private String loginId;
}
