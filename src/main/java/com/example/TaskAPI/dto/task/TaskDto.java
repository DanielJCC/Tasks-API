package com.example.TaskAPI.dto.task;

import com.example.TaskAPI.entities.User;

import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        Boolean isCompleted,
        User user
) {
}
