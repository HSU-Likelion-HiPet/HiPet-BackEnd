package com.hipet.domain.User.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Table(name="MESSAGES")
public class Message {
    @Id
    @GeneratedValue
    @Column(name="MESSAGE_ID")
    private Long messageId; //메세지 고유의 아이디

    @Column(name="TEXT")
    private String text; //본문 내용

    @Column(name="RECEIVER_ID")
    private Long receiverId; // 발신자 ID 고유 번호

    @Column(name="SENDER_ID")
    private Long senderId; // 수신자 ID 고유 번호

    @Column(name="sendAt")
    private Date sendAt; //전송 시각
}
