package com.hipet.domain.animal.repository;

import com.hipet.domain.animal.entity.Animal;
import com.hipet.domain.animal.entity.AnimalPhotos;
import com.hipet.global.enums.Category;
import com.hipet.global.enums.Region;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Optional<Animal> findByAnimalId(Long animalId);

    List<Animal> findByCategoryAndRegionAndAnimalNameContaining(Category category, Region region, String animalName, Sort sort);

    List<Animal> findByAnimalNameContaining(String animalName, Sort sort);

    List<Animal> findByRegion(Region region, Sort sort);

    List<Animal> findByRegionAndAnimalNameContaining(Region region, String animalName, Sort sort);

    List<Animal> findByCategory(Category category, Sort sort);

    List<Animal> findByRegionAndCategory(Region region, Category category, Sort sort);

    List<Animal> findByCategoryAndAnimalNameContaining(Category category, String animalName, Sort sort);

}
