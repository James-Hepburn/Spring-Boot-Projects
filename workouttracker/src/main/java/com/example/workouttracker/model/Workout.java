package com.example.workouttracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String name;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <WorkoutExercise> exercises;

    private LocalDateTime scheduledDate;

    public Workout (User user, String name, List <WorkoutExercise> exercises, LocalDateTime scheduledDate) {
        this.user = user;
        this.name = name;
        this.exercises = exercises;
        this.scheduledDate = scheduledDate;
    }
}