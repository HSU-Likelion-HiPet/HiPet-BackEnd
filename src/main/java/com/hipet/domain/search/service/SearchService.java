package com.hipet.domain.search.service;

import com.hipet.domain.search.enums.SortStatus;
import com.hipet.domain.search.web.dto.SearchResponseDto;
import com.hipet.global.entity.response.CustomApiResponse;
import com.hipet.global.enums.Category;
import com.hipet.global.enums.Region;
import org.springframework.http.ResponseEntity;

public interface SearchService {

    ResponseEntity<CustomApiResponse<SearchResponseDto.FinalResponseDto>> searchKeywordFilter(Category type, Region region, SortStatus sortStatus, String keyword/*, int page, int pageCount*/);
}
