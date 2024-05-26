package com.hipet.domain.review.service;

import com.hipet.domain.review.entity.ReviewImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewImageService {
    List<ReviewImage> uploadReviewPhotos(List<MultipartFile> files);
}
