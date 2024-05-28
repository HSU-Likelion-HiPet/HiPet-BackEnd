package com.hipet.domain.user.service;

import com.hipet.domain.User.web.dto.LikedRequestDto;
import com.hipet.global.entity.response.CustomApiResponse;
import org.springframework.http.ResponseEntity;

public interface LikedService {
    ResponseEntity<CustomApiResponse<?>> AnimalLiked(LikedRequestDto request);
}
