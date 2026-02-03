package com.example.workouttracker.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutRequest {
    private String name;
    private LocalDateTime scheduledDate;
    private List <WorkoutExerciseDTO> exercises;
}