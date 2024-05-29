package com.hipet.domain.review.repository;

import com.hipet.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId_UserId(Long userId);
}
