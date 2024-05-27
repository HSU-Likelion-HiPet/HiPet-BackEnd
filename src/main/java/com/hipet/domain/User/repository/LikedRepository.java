package com.hipet.domain.User.repository;

import com.hipet.domain.User.entity.Liked;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedRepository extends JpaRepository<Liked, Long> {

}
