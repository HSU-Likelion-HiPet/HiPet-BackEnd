package com.hipet.domain.animal.service;

import com.hipet.domain.animal.entity.AnimalPhotos;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AnimalPhotoService {
    List<AnimalPhotos> photosUpload(List<MultipartFile> files);
}
