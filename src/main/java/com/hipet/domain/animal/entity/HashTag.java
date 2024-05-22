package com.hipet.domain.animal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagId;

    private String keyword;

    @ManyToOne
    @JoinColumn(name = "animalId")
    private Animal animal;

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
}
