package com.example.workouttracker.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExerciseResponseDTO {
    private String exerciseName;
    private String category;
    private String muscleGroup;
    private int sets;
    private int reps;
}