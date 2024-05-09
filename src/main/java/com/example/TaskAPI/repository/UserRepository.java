package com.example.TaskAPI.repository;

import com.example.TaskAPI.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    //Find a User by Task id
    Optional<User> findByTasksId(UUID taskId);
    // Find a User by userName
    List<User> findByUserNameContainingIgnoreCase(String userName);
    // Find a User by age
    @Query("select u from User u where diff(now(),u.birthdate) = ?1")
    List<User> findByAge(Integer age);
}
