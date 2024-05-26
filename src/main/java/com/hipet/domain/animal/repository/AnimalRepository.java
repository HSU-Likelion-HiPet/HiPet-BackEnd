package com.hipet.domain.animal.repository;

import com.hipet.domain.animal.entity.Animal;
import com.hipet.domain.animal.entity.AnimalPhotos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Optional<Animal> findByAnimalId(Long animalId);
}
