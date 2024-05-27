package com.hipet.domain.review.service;

import com.hipet.domain.animal.entity.AnimalPhotos;
import com.hipet.domain.review.entity.ReviewImage;
import com.hipet.global.aws.s3.AmazonS3Manager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewImageServiceImpl implements ReviewImageService{

    private final AmazonS3Manager amazonS3Manager;

    @Override
    @Transactional
    public List<ReviewImage> uploadReviewPhotos(List<MultipartFile> files) {
        if (files == null) {
            throw new IllegalArgumentException("Files list cannot be null");
        }

        // S3에 저장된 이미지 파일의 경로(URL)을 담을 리스트
        List<ReviewImage> reviewPhotosURL = new ArrayList<>();

        for(MultipartFile file : files){

            // S3에 이미지 저장
            String URL = amazonS3Manager.uploadFile(file);

            log.info("URL : {}", URL);
            ReviewImage photo = ReviewImage.builder()
                    .imageURL(URL)
                    .build();

            reviewPhotosURL.add(photo);
        }

        return reviewPhotosURL;
    }
}
