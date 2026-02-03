package com.example.workouttracker.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExerciseDTO {
    private Long exerciseId;
    private int sets;
    private int reps;
}