package com.hipet.domain.animal.web.controller;

import com.hipet.domain.user.service.LikedServiceImpl;
import com.hipet.domain.User.web.dto.LikedRequestDto;
import com.hipet.domain.animal.service.AnimalServiceImpl;
import com.hipet.domain.animal.web.dto.AnimalRequestDto;
import com.hipet.domain.animal.web.dto.GetOneAnimalRequestDto;
import com.hipet.domain.animal.web.dto.GetOneAnimalResponseDto;
import com.hipet.global.entity.response.CustomApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/animal")
public class AnimalController {
    private final AnimalServiceImpl animalService;
    private final LikedServiceImpl likedService;

    // 동물 등록하기
    @PostMapping("/hipet")
    public ResponseEntity<CustomApiResponse<?>> addAnimals(@Valid @ModelAttribute AnimalRequestDto request){
        log.info("request : {}", request);

        ResponseEntity<CustomApiResponse<?>> registerResponse = animalService.registerAnimals(request);

        return registerResponse;
    }

    // 동물 상세보기
    @GetMapping("/detail")
    public ResponseEntity<CustomApiResponse<GetOneAnimalResponseDto.FinalResponseDto>> getAnimalPost(@Valid @RequestBody GetOneAnimalRequestDto request){
        // GetOneAnimalRequestDto에서 animalId를 추출
        Long animalId = request.getAnimalId();
        ResponseEntity<CustomApiResponse<GetOneAnimalResponseDto.FinalResponseDto>> getAnimalInfo = animalService.getOneAnimal(animalId);

        return getAnimalInfo;
    }

    // 찜 기능
    @PostMapping("/like")
    public ResponseEntity<CustomApiResponse<?>> likedAnimal(@Valid @RequestBody LikedRequestDto request){
        ResponseEntity<CustomApiResponse<?>> likedAnimal = likedService.AnimalLiked(request);

        return likedAnimal;
    }
}
