package com.hipet.domain.User.repository;

import com.hipet.domain.User.entity.Liked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikedRepository extends JpaRepository<Liked, Long> {
    List<Liked> findByUser_UserId(Long id);
}
