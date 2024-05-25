package com.hipet.domain.User.entity;

import com.hipet.domain.animal.entity.Animal;
import com.hipet.domain.review.entity.Review;
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

    private String userName;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Region region;

    private String profileInfo;

    private Double totalUserRate;

    private String profileImage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animal> animalList;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviewList;
}
