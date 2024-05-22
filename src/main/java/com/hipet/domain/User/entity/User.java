package com.hipet.domain.User.entity;

import com.hipet.domain.animal.entity.Animal;
import com.hipet.domain.animal.entity.AnimalPhotos;
import com.hipet.global.entity.BaseEntity;
import com.hipet.global.enums.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String loginId;

    private String password;

    private String userNme;

    private String nickName;

    private String phoneNumber;

    private Region region;

    private String profileInfo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animal> animalList;
}
