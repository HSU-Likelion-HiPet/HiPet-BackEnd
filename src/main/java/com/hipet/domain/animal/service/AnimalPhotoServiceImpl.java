package com.hipet.domain.animal.service;

import com.hipet.domain.animal.entity.AnimalPhotos;
import com.hipet.domain.animal.repository.AnimalRepository;
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
public class AnimalPhotoServiceImpl implements AnimalPhotoService{

    private final AmazonS3Manager amazonS3Manager;
    private final AnimalRepository animalRepository;

    @Transactional
    @Override
    public List<AnimalPhotos> photosUpload(List<MultipartFile> files) {

        // S3에 저장된 이미지 파일의 경로(URL)을 담을 리스트
        List<AnimalPhotos> photosURL = new ArrayList<>();

        for(MultipartFile file : files){

            // S3에 이미지 저장
            String URL = amazonS3Manager.uploadFile(file);

            AnimalPhotos photo = AnimalPhotos.builder()
                    .photoUrl(URL)
                    .build();

            photosURL.add(photo);
        }

        return photosURL;
    }
}
