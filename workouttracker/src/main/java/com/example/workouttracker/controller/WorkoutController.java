package com.example.workouttracker.controller;

import com.example.workouttracker.dto.WorkoutRequest;
import com.example.workouttracker.dto.WorkoutResponse;
import com.example.workouttracker.dto.WorkoutExerciseResponseDTO;
import com.example.workouttracker.model.Workout;
import com.example.workouttracker.model.User;
import com.example.workouttracker.model.WorkoutExercise;
import com.example.workouttracker.model.Exercise;
import com.example.workouttracker.service.WorkoutService;
import com.example.workouttracker.service.UserService;
import com.example.workouttracker.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {
    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExerciseService exerciseService;

    @PostMapping
    public ResponseEntity <?> createWorkout (@RequestBody WorkoutRequest workoutRequest, Authentication authentication) {
        User user = this.userService.findByUsername(authentication.getName ()).orElseThrow (() -> new RuntimeException ("User not found"));
        List <WorkoutExercise> exercises = workoutRequest.getExercises ().stream ().map (dto -> {
            Exercise exercise = this.exerciseService.findById (dto.getExerciseId ()).orElseThrow (() -> new RuntimeException ("Exercise not found"));
            return new WorkoutExercise (exercise, null, dto.getSets (), dto.getReps ());
        }).collect (Collectors.toList ());
        Workout workout = this.workoutService.createWorkout (user, workoutRequest.getName (), workoutRequest.getScheduledDate (), exercises);
        exercises.forEach (exercise -> exercise.setWorkout (workout));
        this.workoutService.updateWorkout (workout);
        return ResponseEntity.ok ("Workout created successfully");
    }

    @GetMapping
    public ResponseEntity <List <WorkoutResponse>> getWorkouts (Authentication authentication) {
        User user = this.userService.findByUsername (authentication.getName ()).orElseThrow (() -> new RuntimeException ("User not found"));
        List <WorkoutResponse> response = workoutService.findByUserIdOrderByScheduledDate (user.getId ()).stream ().map (workout -> {
            List <WorkoutExerciseResponseDTO> exercises = workout.getExercises ().stream ().map (we -> new WorkoutExerciseResponseDTO (
                    we.getExercise ().getName (),
                    we.getExercise ().getCategory (),
                    we.getExercise ().getMuscleGroup (),
                    we.getSets (),
                    we.getReps ()
            )).collect (Collectors.toList ());
            return new WorkoutResponse (workout.getId (), workout.getName (), workout.getScheduledDate (), exercises);
        }).collect (Collectors.toList ());
        return ResponseEntity.ok (response);
    }

    @PutMapping("/{id}")
    public ResponseEntity <?> updateWorkout (@PathVariable Long id, @RequestBody WorkoutRequest workoutRequest, Authentication authentication) {
        User user = this.userService.findByUsername (authentication.getName ()).orElseThrow (() -> new RuntimeException ("User not found"));
        Workout workout = this.workoutService.findById (id).orElseThrow (() -> new RuntimeException ("Workout not found"));

        if (!workout.getUser ().getId ().equals (user.getId ())) {
            return ResponseEntity.status (403).body ("Unauthorized");
        }

        workout.setName (workoutRequest.getName ());
        workout.setScheduledDate (workoutRequest.getScheduledDate ());

        List <WorkoutExercise> exercises = workoutRequest.getExercises ().stream ().map (dto -> {
            Exercise exercise = this.exerciseService.findById (dto.getExerciseId ()).orElseThrow (() -> new RuntimeException ("Exercise not found"));
            return new WorkoutExercise (exercise, workout, dto.getSets (), dto.getReps ());
        }).collect (Collectors.toList ());

        workout.getExercises ().clear ();
        exercises.forEach (exercise -> {
            exercise.setWorkout (workout);
            workout.getExercises ().add (exercise);
        });

        this.workoutService.updateWorkout (workout);
        return ResponseEntity.ok ("Workout updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <?> deleteWorkout (@PathVariable Long id, Authentication authentication) {
        User user = this.userService.findByUsername (authentication.getName ()).orElseThrow (() -> new RuntimeException ("User not found"));
        Workout workout = this.workoutService.findById (id).orElseThrow (() -> new RuntimeException ("Workout not found"));

        if (!workout.getUser ().getId ().equals (user.getId ())) {
            return ResponseEntity.status (403).body ("Unauthorized");
        }

        this.workoutService.deleteWorkout (id);
        return ResponseEntity.ok ("Workout deleted successfully");
    }
}