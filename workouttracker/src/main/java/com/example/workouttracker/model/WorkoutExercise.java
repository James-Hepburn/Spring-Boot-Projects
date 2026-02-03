package com.example.workouttracker.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class WorkoutExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Exercise exercise;

    @ManyToOne
    private Workout workout;

    private int sets;
    private int reps;

    public WorkoutExercise (Exercise exercise, Workout workout, int sets, int reps) {
        this.exercise = exercise;
        this.workout = workout;
        this.sets = sets;
        this.reps = reps;
    }
}