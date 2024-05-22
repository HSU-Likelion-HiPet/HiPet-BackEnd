package com.hipet.domain.User.repository;

import com.hipet.domain.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLoginId(String loginId);
}
