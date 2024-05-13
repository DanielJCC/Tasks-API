package com.example.TaskAPI.dto.user;

import java.util.UUID;

public record UserDto(
        UUID id,
        String name,
        String birthdate,
        String userName
) {
}
