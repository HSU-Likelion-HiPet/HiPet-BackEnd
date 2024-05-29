package com.hipet.domain.message.web.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessagePartnerUserDto {
    private String partnerUserId;
    private String partnerUserName;
    private String lastMessageText;
    private LocalDateTime lastSendTime;
    private boolean isSendByLoginUser;

}