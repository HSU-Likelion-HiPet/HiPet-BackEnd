package com.hipet.domain.User.service;


import com.hipet.domain.user.web.dto.LikedRequestDto;
import com.hipet.global.entity.response.CustomApiResponse;
import org.springframework.http.ResponseEntity;

public interface LikedService {
    ResponseEntity<CustomApiResponse<?>> AnimalLiked(LikedRequestDto request);
}
