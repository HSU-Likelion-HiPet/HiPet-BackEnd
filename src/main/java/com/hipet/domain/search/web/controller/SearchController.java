package com.hipet.domain.search.web.controller;

import com.hipet.domain.search.enums.SortStatus;
import com.hipet.domain.search.service.SearchServiceImpl;
import com.hipet.domain.search.web.dto.SearchResponseDto;
import com.hipet.global.entity.response.CustomApiResponse;
import com.hipet.global.enums.Category;
import com.hipet.global.enums.Region;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/search")
public class SearchController {

    private final SearchServiceImpl searchService;

    @GetMapping("")
    public ResponseEntity<CustomApiResponse<SearchResponseDto.FinalResponseDto>> searchKeyword(@RequestParam(name = "type", required = false) Category type,
                                                                                               @RequestParam(name = "region", required = false) Region region,
                                                                                               @RequestParam(name = "sortStatus", defaultValue = "_EARLIEST") SortStatus sortStatus,
                                                                                               @RequestParam(name = "keyword", required = false) String keyword/*,
                                                                                               @RequestParam(name = "page", defaultValue = "0") int page,
                                                                                               @RequestParam(name = "pageCount") int pageCount*/
    ){
        ResponseEntity<CustomApiResponse<SearchResponseDto.FinalResponseDto>> respnose = searchService.searchKeywordFilter(type, region, sortStatus, keyword/*, page, pageCount*/);

        return respnose;
    }

}
