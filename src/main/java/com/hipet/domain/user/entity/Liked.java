package com.hipet.domain.User.entity;

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
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "animal")
    private Animal animal;

    public void setAnimal(Animal animal){
        this.animal = animal;
    }
}
