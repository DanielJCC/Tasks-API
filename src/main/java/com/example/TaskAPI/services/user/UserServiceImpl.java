package com.example.TaskAPI.services.user;

import com.example.TaskAPI.dto.user.UserDto;
import com.example.TaskAPI.dto.user.UserToSaveDto;
import com.example.TaskAPI.entities.User;
import com.example.TaskAPI.mappers.UserMapper;
import com.example.TaskAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    @Override
    public UserDto create(UserToSaveDto userToSaveDto) {
        User userToSave = userMapper.ToSaveDtoToEntity(userToSaveDto);
        User userSaved = userRepository.save(userToSave);
        return userMapper.EntityToDto(userSaved);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream().parallel().map(userMapper::EntityToDto).toList();
    }

    @Override
    public UserDto update(UUID idToUpdate, UserToSaveDto userToUpdate) {
        User userFound = userRepository.findById(idToUpdate)
                .orElseThrow(()->new RuntimeException("User not found"));
        userFound.setName(userToUpdate.name() != null ? userToUpdate.name() : userFound.getName());
        userFound.setUserName(userToUpdate.userName() != null ? userToUpdate.userName() : userFound.getUserName());
        userFound.setBirthdate(userToUpdate.birthdate() != null ? userToUpdate.birthdate() : userFound.getBirthdate());
        userFound.setPassword(userToUpdate.password() != null ? userToUpdate.password() : userFound.getPassword());
        User userUpdated = userRepository.save(userFound);
        return userMapper.EntityToDto(userUpdated);
    }

    @Override
    public void delete(UUID idToDelete) {
        User userFound = userRepository.findById(idToDelete)
                .orElseThrow(()->new RuntimeException("User not found"));
        userRepository.delete(userFound);
    }

    @Override
    public UserDto findByTaskId(UUID taskId) {
        User userFound = userRepository.findByTasksId(taskId)
                .orElseThrow(()->new RuntimeException("User not found"));
        return userMapper.EntityToDto(userFound);
    }

    @Override
    public List<UserDto> findByUserNameContaining(String userName) {
        List<User> usersFound = userRepository.findByUserNameContainingIgnoreCase(userName);
        return usersFound.stream().parallel().map(userMapper::EntityToDto).toList();
    }

    @Override
    public List<UserDto> findByAge(Integer age) {
        List<User> usersFound = userRepository.findByAge(age);
        return usersFound.stream().parallel().map(userMapper::EntityToDto).toList();
    }
}
