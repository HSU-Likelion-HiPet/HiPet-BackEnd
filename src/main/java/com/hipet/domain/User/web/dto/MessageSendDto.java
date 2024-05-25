package com.hipet.domain.User.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class MessageSendDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class messageReq {
        @NotBlank(message = "발신자는 비어있을 수 없습니다.")
        private String senderID; //발신자 id

        @NotBlank(message = "수신자는 비어있을 수 없습니다.")
        private String receiverID; //수신자 id

        @NotBlank(message = "메시지 내용은 비어 있을 수 없습니다.")
        private String text; //메시지 내용



    }
}
