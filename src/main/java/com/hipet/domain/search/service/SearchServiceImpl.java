package com.hipet.domain.search.service;

import com.hipet.domain.animal.entity.Animal;
import com.hipet.domain.animal.entity.HashTag;
import com.hipet.domain.animal.repository.AnimalRepository;
import com.hipet.domain.search.enums.SortStatus;
import com.hipet.domain.search.web.dto.SearchResponseDto;
import com.hipet.global.entity.response.CustomApiResponse;
import com.hipet.global.enums.Category;
import com.hipet.global.enums.Region;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SearchServiceImpl implements SearchService{

    private final AnimalRepository animalRepository;

    @Override
    public ResponseEntity<CustomApiResponse<SearchResponseDto.FinalResponseDto>> searchKeywordFilter(Category type, Region region, SortStatus sortStatus, String keyword/*, int page, int pageCount*/) {
        int totalNum = 0;

        Sort sort = null;
        if(sortStatus == SortStatus._EARLIEST){
            sort = Sort.by(Sort.Order.asc("createdAt"));
        }
        else if(sortStatus == SortStatus._LATEST){
            sort = Sort.by(Sort.Order.desc("createdAt"));
        }

        List<Animal> animalList = new ArrayList<>();

        if (type == null && region == null && keyword == null) {

            // Case 1: All values are null
            CustomApiResponse<SearchResponseDto.FinalResponseDto> response = CustomApiResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "요청을 다시 확인해주새요");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } else if (type == null && region == null) {

            // Case 2: type and region are null
            animalList = animalRepository.findByAnimalNameContaining(keyword, sort);

        } else if (type == null && keyword == null) {

            // Case 3: type and keyword are null
            animalList = animalRepository.findByRegion(region, sort);

        } else if (region == null && keyword == null) {

            // Case 4: region and keyword are null
            animalList = animalRepository.findByCategory(type, sort);

        } else if (type == null) {

            // Case 5: Only type is null
            animalList = animalRepository.findByRegionAndAnimalNameContaining(region, keyword, sort);

        } else if (region == null) {

            // Case 6: Only region is null
            animalList = animalRepository.findByCategoryAndAnimalNameContaining(type, keyword, sort);

        } else if (keyword == null) {

            // Case 7: Only keyword is null
            animalList = animalRepository.findByRegionAndCategory(region, type, sort);

        } else {

            // Case 8: No values are null
            animalList = animalRepository.findByCategoryAndRegionAndAnimalNameContaining(type, region, keyword, sort);
            log.info("animalList = {}", animalList);
        }

        totalNum = animalList.size();

        List<SearchResponseDto.AnimalListInfo> animalLists = new ArrayList<>();
        for (Animal animal : animalList) {
            List<SearchResponseDto.Hashtags> hashtags = new ArrayList<>();
            List<HashTag> hashTagList = animal.getHashTag();

            for (HashTag hashTag : hashTagList) {
                SearchResponseDto.Hashtags hashtag = SearchResponseDto.Hashtags.builder()
                        .hashtagId(hashTag.getHashtagId())
                        .tag(hashTag.getKeyword())
                        .build();

                hashtags.add(hashtag);
            }

            SearchResponseDto.AnimalListInfo getAnimalInfo = SearchResponseDto.AnimalListInfo.builder()
                    .animalId(animal.getAnimalId())
                    .photo(animal.getAnimalPhotos().get(0).getPhotoUrl())
                    .animalName(animal.getAnimalName())
                    .price(animal.getPrice())
                    .region(String.valueOf(animal.getRegion()))
                    .hashtag(hashtags)
                    .createdAt(LocalDate.from(animal.getCreatedAt()))
                    .build();

            animalLists.add(getAnimalInfo);
        }

        SearchResponseDto.FinalResponseDto searchResponse = SearchResponseDto.FinalResponseDto.builder()
                .keyword(keyword)
                .totalNum(totalNum)
                .animalListInfo(animalLists)
                .build();

        CustomApiResponse<SearchResponseDto.FinalResponseDto> response = CustomApiResponse.createSuccess(HttpStatus.OK.value(), searchResponse, "검색 및 필터링에 성공하였습니다.");

        return ResponseEntity.ok(response);

    }
}
