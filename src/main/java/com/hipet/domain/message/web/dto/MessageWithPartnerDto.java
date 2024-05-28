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
    private String senderName;
    private String receiverId;
    private String receiverName;
    private LocalDateTime sendAt;

    public static MessageWithPartnerDto fromEntity(Message message) {
        return MessageWithPartnerDto.builder()
                .text(message.getText())
                .senderId(message.getSenderId().getLoginId())
                .senderName(message.getSenderId().getUserName())
                .receiverId(message.getReceiverId().getLoginId())
                .receiverName(message.getReceiverId().getUserName())
                .sendAt(message.getSendAt())
                .build();
    }
}
