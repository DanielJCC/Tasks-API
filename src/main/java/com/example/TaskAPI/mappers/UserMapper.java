package com.example.TaskAPI.mappers;

import com.example.TaskAPI.dto.user.UserDto;
import com.example.TaskAPI.dto.user.UserToSaveDto;
import com.example.TaskAPI.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User DtoToEntity(UserDto userDto);
    UserDto EntityToDto(User user);
    @Mapping(target = "id",ignore = true)
    User ToSaveDtoToEntity(UserToSaveDto userToSaveDto);
}
