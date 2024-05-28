package com.hipet.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Liked{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likedId;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "animal")
    @JsonIgnoreProperties("likedList")
    private Animal animal;

    public void setAnimal(Animal animal){
        this.animal = animal;
    }

    public void setUser(User user){
        this.user = user;
    }
}
