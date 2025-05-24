package com.fitnessmicroservice.workout_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fitnessmicroservice.workout_service.model.Workout;
import com.fitnessmicroservice.workout_service.service.impl.WorkoutService;

import lombok.*;

@RestController
@RequestMapping("/api/v1/workouts")
@AllArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Workout Service!????";
    }


    @PostMapping
    public ResponseEntity<Workout> createWorkout(@RequestBody Workout workout) {
        return new ResponseEntity<>(workoutService.createWorkout(workout), HttpStatus.CREATED);
    }
    
    @GetMapping("/{workoutId}")
    public ResponseEntity<Workout> getWorkoutById(@PathVariable Long workoutId) {
        Workout workout = workoutService.getWorkById(workoutId);
        if (workout != null) {
            return new ResponseEntity<>(workout, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Workout>> getWorkoutsByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(workoutService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Workout>> getAllWorkouts() {
        return new ResponseEntity<>(workoutService.getAllWorkouts(), HttpStatus.OK);
    }

    @PutMapping("/{workoutId}")
    public ResponseEntity<Workout> updateWorkout(@PathVariable Long workoutId, @RequestBody Workout workout) {
        return new ResponseEntity<>(workoutService.updateWorkout(workoutId, workout), HttpStatus.OK);

    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long workoutId) {
        workoutService.deleteWorkout(workoutId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}