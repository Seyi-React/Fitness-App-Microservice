package com.fitnessmicroservice.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitnessmicroservice.user_service.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>  {
    boolean existsByEmail(String email);

}
