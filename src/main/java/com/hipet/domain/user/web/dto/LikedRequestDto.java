package com.hipet.domain.User.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LikedRequestDto {

    private String loginId;
    private Long animalId;

}
