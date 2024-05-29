package com.hipet.domain.user.service;

import com.hipet.domain.user.web.dto.LikedRequestDto;
import com.hipet.domain.animal.entity.Animal;
import com.hipet.domain.animal.repository.AnimalRepository;
import com.hipet.domain.user.entity.Liked;
import com.hipet.domain.user.entity.User;
import com.hipet.domain.user.repository.LikedRepository;
import com.hipet.domain.user.repository.UserRepository;
import com.hipet.global.entity.response.CustomApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LikedServiceImpl implements LikedService{
    private final LikedRepository likedRepository;
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public ResponseEntity<CustomApiResponse<?>> AnimalLiked(LikedRequestDto request) {

        if(request == null){
            log.info("request is null");
            throw new RuntimeException("request is null");
        }

        Optional<Animal> getAnimal = animalRepository.findByAnimalId(request.getAnimalId());

        if(getAnimal.isEmpty()){
            log.info("animal not found");
            CustomApiResponse<?> response = CustomApiResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "해당 동물을 찾을 수 없습니다.");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Animal getAnimalInfo = getAnimal.get();

        Optional<User> getUser = userRepository.findByLoginId(request.getLoginId());

        if(getUser.isEmpty()){
            log.info("user not found");
            CustomApiResponse<?> response = CustomApiResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "로그인 된 사용자를 찾을 수 없습니다.");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        User user = getUser.get();

        Liked newLiked = Liked.builder()
                .userId(user)
                .animalId(getAnimalInfo)
                .build();

        getUser.get().addLiked(newLiked);
        getAnimalInfo.addLiked(newLiked);

        likedRepository.save(newLiked);

        CustomApiResponse<?> response = CustomApiResponse.createSuccess(HttpStatus.OK.value(), null, "동물 관심 등록이 완료되었습니다.");

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
