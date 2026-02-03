package com.example.workouttracker.service;

import com.example.workouttracker.model.Exercise;
import com.example.workouttracker.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;

    public Exercise createExercise (String name, String description, String category, String muscleGroup) {
        Exercise exercise = new Exercise (name, description, category, muscleGroup);
        return this.exerciseRepository.save (exercise);
    }

    public Optional <Exercise> findById (Long id) {
        return this.exerciseRepository.findById (id);
    }

    public Optional <Exercise> findByName (String name) {
        return this.exerciseRepository.findByName (name);
    }

    public List <Exercise> findAll () {
        return this.exerciseRepository.findAll ();
    }
}