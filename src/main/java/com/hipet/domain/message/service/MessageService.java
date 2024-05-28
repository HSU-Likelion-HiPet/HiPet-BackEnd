package com.hipet.domain.message.service;

import com.hipet.domain.message.web.dto.MessageSendDto;
import com.hipet.global.entity.response.CustomApiResponse;
import org.springframework.http.ResponseEntity;

public interface MessageService {
    ResponseEntity<CustomApiResponse<?>> sendMessage(MessageSendDto.messageReq req);

    ResponseEntity<CustomApiResponse<?>> getMessagesWithPartner(String user1, String user2);

    ResponseEntity<CustomApiResponse<?>> getMessagePartnerList(String loginId);
}
