package com.hipet.domain.animal.web.controller;

import com.hipet.domain.animal.service.AnimalServiceImpl;
import com.hipet.domain.animal.web.dto.AnimalRequestDto;
import com.hipet.domain.animal.web.dto.GetOneAnimalResponseDto;
import com.hipet.global.entity.response.CustomApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/animal")
public class AnimalController {
    private final AnimalServiceImpl animalService;

    // 동물 등록하기
    @PostMapping("/hipet")
    public ResponseEntity<CustomApiResponse<?>> addAnimals(@Valid @ModelAttribute AnimalRequestDto request){
        log.info("request : {}", request);

        ResponseEntity<CustomApiResponse<?>> registerResponse = animalService.registerAnimals(request);

        return registerResponse;
    }

    @GetMapping("/{animalId}")
    public ResponseEntity<CustomApiResponse<GetOneAnimalResponseDto.FinalResponseDto>> getAnimalPost(@Valid @PathVariable Long animalId){
        ResponseEntity<CustomApiResponse<GetOneAnimalResponseDto.FinalResponseDto>> getAnimalInfo = animalService.getOneAnimal(animalId);

        return getAnimalInfo;
    }
}
