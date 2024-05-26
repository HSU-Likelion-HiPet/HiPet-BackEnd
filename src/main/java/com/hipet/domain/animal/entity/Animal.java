package com.hipet.domain.animal.entity;

import com.hipet.domain.User.entity.Liked;
import com.hipet.domain.User.entity.User;
import com.hipet.domain.animal.enums.Gender;
import com.hipet.domain.review.entity.Review;
import com.hipet.global.entity.BaseEntity;
import com.hipet.global.enums.Category;
import com.hipet.global.enums.Region;
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

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Region region;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String information;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnimalPhotos> animalPhotos;

    @ManyToOne
    @JoinColumn(name = "loginId")
    private User user;

    // 양방향 연관관계 매핑을 할 때, 연관관계의 주인을 설정해주어야 한다.
    // 연관관계의 주인은 1:N 의 경우 N쪽에 해주면 된다.
    // mappedBy의 값은 반대쪽에 자신이 매핑되어있는 필드명을 써주면 된다.
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HashTag> hashTag;

    @OneToMany(mappedBy = "animalId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Liked> liked;

    // 연관관계 편의 메서드
    public void addAnimalPhoto(AnimalPhotos animalPhoto) {
        this.animalPhotos.add(animalPhoto);
        animalPhoto.setAnimal(this);
    }

    public void addHashtag(HashTag hashTag) {
        this.hashTag.add(hashTag);
        hashTag.setAnimal(this);
    }

    public void addLiked(Liked liked) {
        this.liked.add(liked);
        liked.setAnimal(this);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
        review.setAnimal(this);
    }
}
