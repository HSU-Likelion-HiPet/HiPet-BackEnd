package com.hipet.domain.animal.web.controller;

import com.hipet.domain.animal.service.AnimalServiceImpl;
import com.hipet.domain.animal.web.dto.AnimalRequestDto;
import com.hipet.global.entity.response.CustomApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/animal")
public class AnimalController {
    private final AnimalServiceImpl animalService;

    // 동물 등록하기
    @PostMapping("/hipet")
    public ResponseEntity<CustomApiResponse<?>> addAnimals(@Valid @ModelAttribute AnimalRequestDto request){
        ResponseEntity<CustomApiResponse<?>> registerResponse = animalService.registerAnimals(request);

        return registerResponse;
    }
}
