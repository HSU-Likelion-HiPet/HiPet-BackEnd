package com.hipet.domain.animal.service;

import com.hipet.domain.User.repository.UserRepository;
import com.hipet.domain.animal.entity.Animal;
import com.hipet.domain.animal.entity.AnimalPhotos;
import com.hipet.domain.animal.entity.HashTag;
import com.hipet.domain.animal.enums.Gender;
import com.hipet.domain.animal.repository.AnimalRepository;
import com.hipet.domain.animal.web.dto.AnimalRequestDto;
import com.hipet.global.entity.response.CustomApiResponse;
import com.hipet.global.enums.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService{

    private final AnimalPhotoServiceImpl animalPhotoService;
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ResponseEntity<CustomApiResponse<?>> registerAnimals(AnimalRequestDto request) {

        if(request == null){
            CustomApiResponse<?> response = CustomApiResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "전송 데이터 목록을 확인해주세요.");

            return ResponseEntity.ok(response);
        }

        List<AnimalPhotos> animalPhotosList = animalPhotoService.photosUpload(request.getPhotoFiles());
        String price = "";

        if(request.isPrice()){
            price = request.getPrice();
        }
        else{
            price = null;
        }

        Animal animal = Animal.builder()
                .animalName(request.getAnimalName())
                .price(price)
                .category(Category.valueOf(request.getCategory()))
                .gender(Gender.valueOf(request.getGender()))
                .information(request.getDescription())
                .animalPhotos(new ArrayList<>())
                .user(userRepository.findByLoginId(request.getUserId()))
                .hashTag(new ArrayList<>())
                .build();

        for(AnimalPhotos animalPhoto : animalPhotosList) {
            animal.addAnimalPhoto(animalPhoto);
        }

        for(String hashTag : request.getHashtag()){
            HashTag newTag = HashTag.builder()
                    .keyword(hashTag)
                    .build();

            animal.addHashtag(newTag);
        }

        animalRepository.save(animal);

        CustomApiResponse<?> response = CustomApiResponse.createSuccess(HttpStatus.OK.value(), null, "동물 등록이 완료되었습니다.");

        return ResponseEntity.ok(response);
    }
}
