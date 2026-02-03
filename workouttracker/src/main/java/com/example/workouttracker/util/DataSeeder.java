package com.example.workouttracker.util;

import com.example.workouttracker.model.Exercise;
import com.example.workouttracker.model.Role;
import com.example.workouttracker.repository.RoleRepository;
import com.example.workouttracker.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run (String... args) throws Exception {
        seedExercises ();

        if (this.roleRepository.findByName ("ROLE_USER").isEmpty ()) {
            this.roleRepository.save(new Role ("ROLE_USER"));
        }
        if (this.roleRepository.findByName ("ROLE_ADMIN").isEmpty ()) {
            this.roleRepository.save(new Role ("ROLE_ADMIN"));
        }
    }

    private void seedExercises () {
        if (this.exerciseService.findAll ().isEmpty ()) {
            this.exerciseService.createExercise ("Push-Up", "Push-ups for chest and arms", "Strength", "Chest");
            this.exerciseService.createExercise ("Squat", "Bodyweight squats for legs", "Strength", "Legs");
            this.exerciseService.createExercise ("Plank", "Hold plank for core strength", "Strength", "Core");
            this.exerciseService.createExercise ("Jumping Jacks", "Full body cardio exercise", "Cardio", "Full Body");
            this.exerciseService.createExercise ("Lunges", "Forward lunges for legs", "Strength", "Legs");
            this.exerciseService.createExercise ("Burpees", "Full body cardio with push-up", "Cardio", "Full Body");
            this.exerciseService.createExercise ("Bicep Curl", "Dumbbell curls for biceps", "Strength", "Arms");
            this.exerciseService.createExercise ("Tricep Dip", "Bodyweight dip for triceps", "Strength", "Arms");
            this.exerciseService.createExercise ("Mountain Climbers", "Cardio for core and legs", "Cardio", "Core");
            this.exerciseService.createExercise ("Yoga Stretch", "Flexibility and stretching", "Flexibility", "Full Body");
        }
    }
}