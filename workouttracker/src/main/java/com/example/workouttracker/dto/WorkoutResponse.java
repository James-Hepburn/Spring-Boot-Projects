package com.example.workouttracker.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutResponse {
    private Long id;
    private String name;
    private LocalDateTime scheduledDate;
    private List <WorkoutExerciseResponseDTO> exercises;
}