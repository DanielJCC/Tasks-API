package com.example.TaskAPI.services.task;

import com.example.TaskAPI.dto.task.TaskDto;
import com.example.TaskAPI.dto.task.TaskToSaveDto;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    TaskDto create(TaskToSaveDto taskToSaveDto);
    List<TaskDto> findAll();
    TaskDto update(UUID idToUpdate, TaskToSaveDto taskToUpdate);
    void delete(UUID idToDelete);
    List<TaskDto> findByUserId(UUID userId);
}
