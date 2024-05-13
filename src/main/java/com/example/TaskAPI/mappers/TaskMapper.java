package com.example.TaskAPI.mappers;

import com.example.TaskAPI.dto.task.TaskDto;
import com.example.TaskAPI.dto.task.TaskToSaveDto;
import com.example.TaskAPI.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task DtoToEntity(TaskDto taskDto);
    TaskDto EntityToDto(Task task);
    @Mapping(target = "id",ignore = true)
    Task ToSaveDtoToEntity(TaskToSaveDto taskToSaveDto);
}
