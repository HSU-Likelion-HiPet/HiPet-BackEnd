package com.hipet.domain.user.repository;

import com.hipet.domain.user.entity.Liked;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedRepository extends JpaRepository<Liked, Long> {

}
