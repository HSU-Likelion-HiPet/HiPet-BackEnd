package com.hipet.domain.review.web.controller;

import com.hipet.domain.review.service.ReviewServiceImpl;
import com.hipet.domain.review.web.dto.ReviewRequestDto;
import com.hipet.global.entity.response.CustomApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewServiceImpl reviewService;

    // 리뷰 등록
    @PostMapping("")
    public ResponseEntity<CustomApiResponse<?>> addReview(@Valid @ModelAttribute ReviewRequestDto request){
        ResponseEntity<CustomApiResponse<?>> response = reviewService.addReview(request);

        return response;
    }
}
