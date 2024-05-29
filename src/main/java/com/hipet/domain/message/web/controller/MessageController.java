package com.hipet.domain.message.web.controller;

import com.hipet.domain.message.service.MessageService;
import com.hipet.domain.message.web.dto.MessageSendDto;
import com.hipet.global.entity.response.CustomApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/partnerList")
    public ResponseEntity<CustomApiResponse<?>> getPartnerList(@RequestParam String loginId) {
        return messageService.getMessagePartnerList(loginId);

    }


    //특정 상대와의 메세지 내역 조회
    @GetMapping("/list")
    public ResponseEntity<CustomApiResponse<?>> getMessagesWithPartner(
            @RequestParam String loginUserId, @RequestParam String partnerId) {
        return messageService.getMessagesWithPartner(loginUserId, partnerId);
    }
}
