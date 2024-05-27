package com.hipet.domain.review.service;

import com.hipet.domain.review.web.dto.ReviewRequestDto;
import com.hipet.global.entity.response.CustomApiResponse;
import org.springframework.http.ResponseEntity;

public interface ReviewService {
    ResponseEntity<CustomApiResponse<?>> addReview(ReviewRequestDto request);
}
