package com.hipet.domain.animal.entity;

import com.hipet.domain.User.entity.User;
import com.hipet.domain.animal.enums.Gender;
import com.hipet.global.entity.BaseEntity;
import com.hipet.global.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Animal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long animalId;

    private String animalName;

    private String price;

    private Category category;

    private Gender gender;

    private String information;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnimalPhotos> animalPhotos;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HashTag> hashTag;

    // 연관관계 편의 메서드
    public void addAnimalPhoto(AnimalPhotos animalPhoto) {
        this.animalPhotos.add(animalPhoto);
        animalPhoto.setAnimal(this);
    }

    public void addHashtag(HashTag hashTag) {
        this.hashTag.add(hashTag);
        hashTag.setAnimal(this);
    }
}
