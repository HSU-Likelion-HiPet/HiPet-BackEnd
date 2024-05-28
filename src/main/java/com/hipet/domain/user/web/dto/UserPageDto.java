package com.hipet.domain.user.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hipet.domain.animal.entity.Animal;
import com.hipet.domain.review.entity.Review;

import com.hipet.domain.user.entity.Liked;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize
public class UserPageDto {
    private String userId;
    private String userName;
    private String profileInfo;
    private String profilePicture;
    private List<Review> reviews;
    private List<Animal> animals;
    private List<Liked> likes;
}
