package com.hipet.domain.animal.service;

import com.hipet.domain.animal.web.dto.AnimalRequestDto;
import com.hipet.domain.animal.web.dto.GetOneAnimalResponseDto;
import com.hipet.global.entity.response.CustomApiResponse;
import org.springframework.http.ResponseEntity;

public interface AnimalService {
    ResponseEntity<CustomApiResponse<?>> registerAnimals(AnimalRequestDto request);
    ResponseEntity<CustomApiResponse<GetOneAnimalResponseDto.FinalResponseDto>> getOneAnimal(Long animalId);
}
