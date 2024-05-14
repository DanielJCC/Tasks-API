package com.example.TaskAPI.services.user;

import com.example.TaskAPI.dto.user.UserDto;
import com.example.TaskAPI.dto.user.UserToSaveDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDto create(UserToSaveDto userToSaveDto);
    List<UserDto> findAll();
    UserDto update(UUID idToUpdate, UserToSaveDto userToUpdate);
    void delete(UUID idToDelete);
    UserDto findByTaskId(UUID taskId);
    List<UserDto> findByUserNameContaining(String userName);
    List<UserDto> findByAge(Integer age);
}
