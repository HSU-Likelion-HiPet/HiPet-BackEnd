package com.hipet.domain.message.web.dto;

import com.hipet.domain.message.entity.Message;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageWithPartnerDto {
    private String text;
    private String senderId;
    private String receiverId;
    private LocalDateTime sendAt;

    public static MessageWithPartnerDto fromEntity(Message message) {
        return MessageWithPartnerDto.builder()
                .text(message.getText())
                .senderId(message.getSenderId().getLoginId())
                .receiverId(message.getReceiverId().getLoginId())
                .sendAt(message.getSendAt())
                .build();
    }
}
