package com.example.TaskAPI.services.task;

import com.example.TaskAPI.dto.task.TaskDto;
import com.example.TaskAPI.dto.task.TaskToSaveDto;
import com.example.TaskAPI.entities.Task;
import com.example.TaskAPI.mappers.TaskMapper;
import com.example.TaskAPI.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService{
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper){
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }
    @Override
    public TaskDto create(TaskToSaveDto taskToSaveDto) {
        Task taskToSave = taskMapper.ToSaveDtoToEntity(taskToSaveDto);
        Task taskSaved = taskRepository.save(taskToSave);
        return taskMapper.EntityToDto(taskSaved);
    }

    @Override
    public List<TaskDto> findAll() {
        List<Task> allTasks = taskRepository.findAll();
        return allTasks.stream().parallel().map(taskMapper::EntityToDto).toList();
    }

    @Override
    public TaskDto update(UUID idToUpdate, TaskToSaveDto taskToUpdate) {
        Task taskFound = taskRepository.findById(idToUpdate)
                .orElseThrow(()->new RuntimeException("Task not found"));
        taskFound.setTitle(taskToUpdate.title() != null ? taskToUpdate.title() : taskFound.getTitle());
        taskFound.setDescription(taskToUpdate.description() != null ? taskToUpdate.description() : taskFound.getDescription());
        taskFound.setIsCompleted(taskToUpdate.isCompleted() != null ? taskToUpdate.isCompleted() : taskFound.getIsCompleted());
        Task taskUpdated = taskRepository.save(taskFound);
        return taskMapper.EntityToDto(taskUpdated);
    }

    @Override
    public void delete(UUID idToDelete) {
        Task taskFound = taskRepository.findById(idToDelete)
                .orElseThrow(()->new RuntimeException("Task not found"));
        taskRepository.delete(taskFound);
    }

    @Override
    public List<TaskDto> findByUserId(UUID userId) {
        List<Task> tasksFound = taskRepository.findByUserId(userId);
        return tasksFound.stream().parallel().map(taskMapper::EntityToDto).toList();
    }
}
