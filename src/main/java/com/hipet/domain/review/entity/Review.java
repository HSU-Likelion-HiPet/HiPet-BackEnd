package com.hipet.domain.review.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hipet.domain.user.entity.User;
import com.hipet.domain.animal.entity.Animal;
import com.hipet.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "reviews", "user"})
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private Double rate;

    private String text;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonBackReference
    private User userId;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ReviewImage> reviewImages;

    @ManyToOne
    @JoinColumn(name = "animalId")
    @JsonBackReference
    private Animal animalId;

    public void setAnimal(Animal animal) {
        this.animalId = animal;
    }

    public void addReviewImage(ReviewImage reviewImage) {
        this.reviewImages.add(reviewImage);
        reviewImage.setReview(this);
    }

    public User getUser() {
        return userId;
    }

    public Animal getAnimal() {
        return animalId;
    }

}
