package com.hipet.domain.review.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "reviews", "user"})
public class ReviewImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewImageId;

    private String imageURL;

    @ManyToOne
    @JoinColumn(name = "reviewId")
    @JsonBackReference
    private Review review;

    public void setReview(Review reviewId) {
        this.review = reviewId;
    }
}
