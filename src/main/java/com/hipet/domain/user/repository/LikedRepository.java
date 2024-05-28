package com.hipet.domain.user.repository;

import com.hipet.domain.user.entity.Liked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikedRepository extends JpaRepository<Liked, Long> {
    List<Liked> findByUser_UserId(Long id);
}
