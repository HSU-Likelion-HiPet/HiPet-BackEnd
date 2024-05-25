package com.hipet.domain.message.web.controller;

import com.hipet.domain.message.service.MessageService;
import com.hipet.domain.message.web.dto.MessageSendDto;
import com.hipet.global.entity.response.CustomApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    //메시지 전송
    @PostMapping("/send")
    public ResponseEntity<CustomApiResponse<?>> sendMessage(@RequestBody MessageSendDto.messageReq requestDto) {
        return messageService.sendMessage(requestDto);
    }
}
