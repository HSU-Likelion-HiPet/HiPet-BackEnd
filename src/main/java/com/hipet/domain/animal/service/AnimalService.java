package com.hipet.domain.animal.service;

import com.hipet.domain.animal.web.dto.AnimalRequestDto;
import com.hipet.domain.animal.web.dto.GetAllAnimalsResponseDto;
import com.hipet.domain.animal.web.dto.GetOneAnimalRequestDto;
import com.hipet.domain.animal.web.dto.GetOneAnimalResponseDto;
import com.hipet.domain.user.entity.User;
import com.hipet.global.entity.response.CustomApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface AnimalService {
    ResponseEntity<CustomApiResponse<?>> registerAnimals(AnimalRequestDto request);

    @Transactional
    ResponseEntity<CustomApiResponse<GetOneAnimalResponseDto.FinalResponseDto>> getOneAnimal(GetOneAnimalRequestDto request);

    Double getTotalRateForUser(User user);

    ResponseEntity<CustomApiResponse<GetAllAnimalsResponseDto.FinalResponseDto>> getAllAnimals();
}
