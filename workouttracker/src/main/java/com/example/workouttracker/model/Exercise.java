package com.example.workouttracker.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String category;
    private String muscleGroup;

    public Exercise (String name, String description, String category, String muscleGroup) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.muscleGroup = muscleGroup;
    }
}