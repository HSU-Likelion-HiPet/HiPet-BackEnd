package com.hipet.domain.animal.repository;

import com.hipet.domain.animal.entity.Animal;
import com.hipet.domain.animal.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    List<HashTag> findALLByAnimal(Animal animal);
}
