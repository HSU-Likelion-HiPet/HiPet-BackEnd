package com.hipet.domain.review.entity;

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
public class ReviewImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewImageId;

    private String imageURL;

    @ManyToOne
    @JoinColumn(name = "reviewId")
    private Review review;

    public void setReview(Review reviewId) {
        this.review = reviewId;
    }
}
