package com.fitnessmicroservice.workout_service.service.impl;

import java.util.List;

import com.fitnessmicroservice.workout_service.model.Workout;

public interface WorkoutService {
  
    Workout createWorkout(Workout workout);

    Workout getWorkById(Long workoutId);

    List<Workout> getUserById(Long userId);

    List<Workout> getAllWorkouts();

    Workout updateWorkout(Long workoutId, Workout workout);

    void deleteWorkout(Long workoutId);
    
} 
