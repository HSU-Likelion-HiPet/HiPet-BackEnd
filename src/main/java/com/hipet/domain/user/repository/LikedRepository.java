package com.hipet.domain.user.repository;


import com.hipet.domain.user.entity.Liked;
import com.hipet.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface LikedRepository extends JpaRepository<Liked, Long> {
    Optional<Liked> findByUserId(User userId);

    @Query("SELECT l FROM Liked l WHERE l.userId = :user AND l.animalId.animalId IN :animalIds")
    List<Liked> findByUserAndAnimal(@Param("user") User user, @Param("animalIds") List<Long> animalIds);

    List<Liked> findByUserId_UserId(Long userId);
}
