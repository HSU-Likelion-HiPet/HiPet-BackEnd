package com.hipet.domain.user.entity;

import com.hipet.domain.animal.entity.Animal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Liked{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likedId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "animalId")
    private Animal animalId;

    public void setAnimalId(Animal animal){
        this.animalId = animal;
    }

    public void setUserId(User user){
        this.userId = user;
    }
}
