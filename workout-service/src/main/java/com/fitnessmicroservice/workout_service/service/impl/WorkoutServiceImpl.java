package com.fitnessmicroservice.workout_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitnessmicroservice.workout_service.exceptions.ResourceNotFoundException;
import com.fitnessmicroservice.workout_service.model.Workout;
import com.fitnessmicroservice.workout_service.repository.WorkRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkRepository workRepository;

 
  @Override
  public Workout createWorkout(Workout workout) {
    return workRepository.save(workout);
  }

  @Override
  public Workout getWorkById(Long workoutId) {
    return workRepository.findById(workoutId)
            .orElseThrow(() -> new ResourceNotFoundException("Workout not found with id: " + workoutId));
  }

  @Override
  public List<Workout> getUserById(Long userId) {
    return workRepository.findByUserId(userId);
  }

  @Override
  public Workout updateWorkout(Long workoutId, Workout workout) {
    Workout existingWorkout = workRepository.findById(workoutId)
        .orElseThrow(() -> new ResourceNotFoundException("Workout not found with id: " + workoutId));
    
    existingWorkout.setName(workout.getName());
    existingWorkout.setDescription(workout.getDescription());
    existingWorkout.setUserId(workout.getUserId());
    existingWorkout.setDuration(workout.getDuration());
    existingWorkout.setDifficulty(workout.getDifficulty());
    
    
    return workRepository.save(existingWorkout);
  }

  @Override
  public void deleteWorkout(Long workoutId) {
    workRepository.deleteById(workoutId);
  }

  @Override
  public List<Workout> getAllWorkouts() {
    return workRepository.findAll();
  }

}
