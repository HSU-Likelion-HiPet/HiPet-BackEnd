package com.hipet.domain.message.service;

import com.hipet.domain.message.entity.Message;
import com.hipet.domain.message.web.dto.MessageWithPartnerDto;
import com.hipet.domain.user.entity.User;
import com.hipet.domain.user.repository.UserRepository;
import com.hipet.domain.message.repository.MessageRepository;
import com.hipet.domain.message.web.dto.MessageSendDto;
import com.hipet.global.entity.response.CustomApiResponse;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Override
    @Transactional
    public ResponseEntity<CustomApiResponse<?>> sendMessage(MessageSendDto.messageReq req) {
        // 쪽지 발신자가 db에 존재하는지 확인
        Optional<User> isExistSendUser = userRepository.findByLoginId(req.getSenderId());
        if (isExistSendUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomApiResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "발신자가 존재하지 않습니다."));

        }

        // 쪽지 수신자가 db에 존재하는지 확인
        Optional<User> isExistReceiveUser = userRepository.findByLoginId(req.getReceiverId());
        if (isExistReceiveUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomApiResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "수신자가 존재하지 않습니다."));
        }

        User senderId = isExistSendUser.get();
        User receiverId = isExistReceiveUser.get();

        //쪽지 전송
        Message sentMessage = Message.builder()
                .text(req.getText())
                .senderId(senderId)
                .receiverId(receiverId)
                .sendAt(LocalDateTime.now())
                .build();

        messageRepository.save(sentMessage);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomApiResponse.createSuccess(HttpStatus.OK.value(), null,"전송을 성공하였습니다."));
    }

    @Override
    public ResponseEntity<CustomApiResponse<?>> getMessagesWithPartner(String loginUserId, String partnerId) {
        Optional<User> isExistLoginUser = userRepository.findByLoginId(loginUserId);
        if (isExistLoginUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomApiResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(),"사용자가 존재하지 않습니다."));
        }

        Optional<User> isExistPartnerUser = userRepository.findByLoginId(partnerId);
        if (isExistPartnerUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomApiResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(),"사용자가 존재하지 않습니다."));
        }

        //두 사용자 간의 모든 메시지 조회
        List<Message> messagesWithPartner=messageRepository.findMessagesWithPartner(loginUserId, partnerId);

        //조회된 메시지를 응답 DTO로 변환
        List<MessageWithPartnerDto> res=messagesWithPartner.stream()
                .map(MessageWithPartnerDto::fromEntity)//Message 객체를 DTO로 변환
                .toList(); //변환된 객체들을 리스트로 수집

        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomApiResponse.createSuccess(HttpStatus.OK.value(), res, "조회에 성공하였습니다."));
    }
}
