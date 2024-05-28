package com.hipet.domain.animal.entity;

import com.hipet.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalPhotos extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    private String photoUrl;

    @ManyToOne
    @JoinColumn(name = "animalId")
    private Animal animal;

    // 연관 관계 편의 메서드
    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
}
