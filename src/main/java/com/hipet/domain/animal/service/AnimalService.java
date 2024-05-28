package com.hipet.domain.animal.service;

import com.hipet.domain.animal.web.dto.AnimalRequestDto;
import com.hipet.domain.animal.web.dto.GetOneAnimalRequestDto;
import com.hipet.domain.animal.web.dto.GetOneAnimalResponseDto;
import com.hipet.global.entity.response.CustomApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface AnimalService {
    ResponseEntity<CustomApiResponse<?>> registerAnimals(AnimalRequestDto request);

//    ResponseEntity<CustomApiResponse<GetOneAnimalResponseDto.FinalResponseDto>> getOneAnimal(GetOneAnimalRequestDto request);

    // 등록한 동물 상세보기
    @Transactional
    ResponseEntity<CustomApiResponse<GetOneAnimalResponseDto.FinalResponseDto>> getOneAnimal(Long animalId);


}
