package com.hipet.domain.animal.repository;

import com.hipet.domain.animal.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
