package util.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) //엔티티 생성/수정/삭제 등의 이벤트 처리
public class BaseEntity {
    @CreatedDate
    @Column(name="createdAt")
    private LocalDateTime createdAt;

    //수정 기능은 구현하지 않을 것이므로 updatedAt은 따로 작성하지 않음
}
