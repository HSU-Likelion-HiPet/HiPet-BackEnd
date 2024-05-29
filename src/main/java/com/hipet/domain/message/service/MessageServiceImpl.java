package com.hipet.domain.message.service;

import com.hipet.domain.message.entity.Message;
import com.hipet.domain.message.web.dto.MessagePartnerUserDto;
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
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
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

        // 쪽지 전송
        Message sentMessage = Message.builder()
                .text(req.getText())
                .senderId(senderId)
                .receiverId(receiverId)
                .sendAt(LocalDateTime.now())
                .build();

        messageRepository.save(sentMessage);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomApiResponse.createSuccess(HttpStatus.OK.value(), null, "전송을 성공하였습니다."));
    }

    @Override
    public ResponseEntity<CustomApiResponse<?>> getMessagesWithPartner(String loginUserId, String partnerId) {
        Optional<User> isExistLoginUser = userRepository.findByLoginId(loginUserId);
        if (isExistLoginUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomApiResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "사용자가 존재하지 않습니다."));
        }

        Optional<User> isExistPartnerUser = userRepository.findByLoginId(partnerId);
        if (isExistPartnerUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomApiResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "사용자가 존재하지 않습니다."));
        }

        // 두 사용자 간의 모든 메시지 조회
        List<Message> messagesWithPartner = messageRepository.findMessagesWithPartner(loginUserId, partnerId);

        // 조회된 메시지를 응답 DTO로 변환
        List<MessageWithPartnerDto> res = messagesWithPartner.stream()
                .map(MessageWithPartnerDto::fromEntity) // Message 객체를 DTO로 변환
                .toList(); // 변환된 객체들을 리스트로 수집

        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomApiResponse.createSuccess(HttpStatus.OK.value(), res, "조회에 성공하였습니다."));
    }

    //나와 쪽지를 주고 받은 사용제 리스트 조회
    @Override
    public ResponseEntity<CustomApiResponse<?>> getMessagePartnerList(String loginId) {
        // 로그인한 사용자 아이디가 존재하는지 확인
        Optional<User> isExistLoginUser = userRepository.findByLoginId(loginId);
        if (isExistLoginUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomApiResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "사용자가 존재하지 않습니다."));
        }

        // 내가 주거나 받은 메시지를 모두 조회
        List<Message> partnerList = messageRepository.getMessagePartner(loginId);

        // 상대 사용자 목록 생성
        Map<Long, Message> lastMessage = new HashMap<>();

        // 마지막 메시지 정보
        for (Message message : partnerList) {
            // 메시지 발신자와 로그인한 사용자가 같으면 sender / 다르면 receiver
            User partner = message.getSenderId().getLoginId().equals(loginId) ? message.getReceiverId() : message.getSenderId();
            // 맵에 partner의 ID가 키로 존재하지 않으면(=해당 상대와의 첫번째 메시지라면) or 이 메시지가 이전 메시지보다 최신이라면
            if (!lastMessage.containsKey(partner.getUserId()) ||
                    message.getSendAt().isAfter(lastMessage.get(partner.getUserId()).getSendAt())) {
                lastMessage.put(partner.getUserId(), message);
            }
        }

        // 결과 생성
        List<MessagePartnerUserDto> res = new ArrayList<>();
        for (Map.Entry<Long, Message> entry : lastMessage.entrySet()) {
            Optional<User> partnerOptional = userRepository.findById(entry.getKey());
            if (partnerOptional.isPresent()) {
                User partner = partnerOptional.get();
                Message lastMsg = entry.getValue();

                //로그인한 유저와 발신자가 같은지
                boolean isSendByLoginUser = loginId.equals(lastMsg.getSenderId().getLoginId());

                //로그인한 유저와 파트너가 같은 사용자가 아니면!
                if(!loginId.equals(partner.getLoginId())) {
                    MessagePartnerUserDto partnerUserDto = MessagePartnerUserDto.builder()
                            .partnerUserId(partner.getLoginId())
                            .partnerUserName(partner.getUserName())
                            .lastMessageText(lastMsg.getText())
                            .lastSendTime(lastMsg.getSendAt())
                            .isSendByLoginUser(isSendByLoginUser)
                            .build();

                    res.add(partnerUserDto);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomApiResponse.createSuccess(HttpStatus.OK.value(), res, "조회에 성공하였습니다."));
    }
}

