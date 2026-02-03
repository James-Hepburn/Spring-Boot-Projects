package com.example.workouttracker.service;

import com.example.workouttracker.model.Workout;
import com.example.workouttracker.model.User;
import com.example.workouttracker.model.WorkoutExercise;
import com.example.workouttracker.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service

public class WorkoutService {
    @Autowired
    private WorkoutRepository workoutRepository;

    @Transactional
    public Workout createWorkout (User user, String name, LocalDateTime scheduledDate, List <WorkoutExercise> exercises) {
        Workout workout = new Workout (user, name, exercises, scheduledDate);
        return this.workoutRepository.save (workout);
    }

    public Optional <Workout> findById (Long id) {
        return this.workoutRepository.findById (id);
    }

    public List <Workout> findByUserId (Long userId) {
        return this.workoutRepository.findByUserId (userId);
    }

    public List <Workout> findByUserIdOrderByScheduledDate (Long userId) {
        return this.workoutRepository.findByUserIdOrderByScheduledDateAsc (userId);
    }

    public Workout updateWorkout (Workout workout) {
        return this.workoutRepository.save (workout);
    }

    public void deleteWorkout (Long id) {
        Optional <Workout> workoutOptional = findById (id);
        workoutOptional.ifPresent (workout -> this.workoutRepository.delete (workout));
    }
}