package com.example.TaskAPI.repository;

import com.example.TaskAPI.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    // Find tasks by user id
    List<Task> findByUserId(UUID userId);
}
