package com.hipet.domain.user.repository;

import com.hipet.domain.user.entity.Liked;
import com.hipet.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface LikedRepository extends JpaRepository<Liked, Long> {
    Optional<Liked> findByUserId(User userId);

}
