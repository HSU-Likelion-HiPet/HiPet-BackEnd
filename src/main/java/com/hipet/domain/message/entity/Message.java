package com.hipet.domain.message.entity;

import com.hipet.domain.User.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Table(name="MESSAGES")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MESSAGE_ID")
    private Long messageId; //메세지 고유의 아이디

    @Column(name="TEXT")
    private String text; //본문 내용

    @ManyToOne
    @JoinColumn(name = "RECEIVER_ID", referencedColumnName = "USER_ID")
    private User receiverId; // 발신자 ID 고유 번호

    @ManyToOne
    @JoinColumn(name = "SENDER_ID", referencedColumnName = "USER_ID")
    private User senderId; // 수신자 ID 고유 번호

    @Column(name="sendAt")
    private LocalDateTime sendAt; //전송 시각
}
