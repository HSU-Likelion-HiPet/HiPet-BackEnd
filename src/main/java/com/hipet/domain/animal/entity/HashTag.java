package com.hipet.domain.animal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "reviews", "user"})
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagId;

    private String keyword;

    @ManyToOne
    @JoinColumn(name = "animalId")
    @JsonBackReference
    private Animal animal;

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
}
