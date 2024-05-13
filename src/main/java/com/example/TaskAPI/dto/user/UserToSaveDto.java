package com.example.TaskAPI.dto.user;

import java.time.LocalDate;
import java.util.UUID;

public record UserToSaveDto(
        String name,
        LocalDate birthdate,
        String userName,
        String password
) {
}
