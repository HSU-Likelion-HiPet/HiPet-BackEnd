package com.hipet.domain.user.web.dto;

import lombok.*;

public class UserPageUpdateDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Req {
        private String userName; //사용자 이름
        private String region; //사용자 주소
        private String profileInfo; //사용자 소개
        private String profilePhoto; //사용자 사진


    }
}
