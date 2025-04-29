package com.fitnessmicroservice.workout_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitnessmicroservice.workout_service.model.Workout;

public interface WorkRepository extends JpaRepository<Workout,Long> {

    List<Workout> findByUserId(Long userId);

}
