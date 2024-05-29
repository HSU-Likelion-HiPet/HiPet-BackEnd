package com.hipet.domain.message.repository;

import com.hipet.domain.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Message> findByMessageId(Long messageId);

    @Query("SELECT m FROM Message m WHERE " +
            "(m.senderId.loginId = :loginId AND m.receiverId.loginId = :partnerId) " +
            "OR (m.senderId.loginId = :partnerId " +
            "AND m.receiverId.loginId = :loginId) ORDER BY m.sendAt ASC")
    List<Message> findMessagesWithPartner(@Param("loginId") String loginId, @Param("partnerId") String partnerId);

    @Query("SELECT m FROM Message m where (m.receiverId.loginId = :loginId) OR (m.senderId.loginId = :loginId)")
    List<Message> getMessagePartner(@Param("loginId") String loginId);
}
