package com.example.TaskAPI.repository;

import com.example.TaskAPI.AbstractIntegrationDBTest;
import com.example.TaskAPI.entities.Task;
import com.example.TaskAPI.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class UserRepositoryTest extends AbstractIntegrationDBTest {
    UserRepository userRepository;
    TaskRepository taskRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository,
                              TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    private User globalUser;

    @BeforeEach
    void setUp(){
        userRepository.deleteAll();
        taskRepository.deleteAll();
        globalUser = User.builder()
                .name("Gian Marco ToyStory")
                .birthdate(LocalDate.of(2002,12,12))
                .userName("GMStarworld")
                .password("gian123")
                .build();
    }

    @Test
    @DisplayName("[Create] Given a new User, when is saved, it persists in DB")
    void testSave(){
        User userSaved = userRepository.save(globalUser);
        assertThat(userSaved).isNotNull();
        assertThat(userSaved.getId()).isNotNull();
        assertThat(userSaved.getName()).isEqualTo(globalUser.getName());
    }

    @Test
    @DisplayName("[Read] Get all users of DB")
    void testRead(){
        userRepository.save(globalUser);
        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0).getUserName()).isEqualTo("GMStarworld");
    }

    @Test
    @DisplayName("[Update] Update user by given id")
    void testUpdate(){
        User userSaved = userRepository.save(globalUser);
        UUID idToFind = userSaved.getId();
        Optional<User> userFound = userRepository.findById(idToFind);
        //Verify if user is present
        assertThat(userFound).isPresent();

        String userNameUpdated = "GinAsotori";
        userFound.get().setUserName(userNameUpdated);
        User userUpdated = userRepository.save(userFound.get());

        //Verify if user was updated
        assertThat(userUpdated.getUserName()).isEqualTo(userNameUpdated);
    }

    @Test
    @DisplayName("[Delete] Delete a User by given id")
    void testDelete(){
        User userSaved = userRepository.save(globalUser);
        UUID idToDelete = userSaved.getId();
        userRepository.deleteById(idToDelete);

        assertThat(userRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("Find a User with Task id")
    void testFindByTaskId(){
        User userSaved = userRepository.save(globalUser);
        Task taskSaved = taskRepository.save(Task.builder()
                        .title("Hacer acorta link con Spring Boot")
                        .description("No olvidar hacer el acortador de links en Java")
                        .isCompleted(false)
                        .user(userSaved)
                        .build());
        Optional<User> userFound = userRepository.findByTasksId(taskSaved.getId());
        assertThat(userFound).isPresent();
        assertThat(userFound.get().getId()).isEqualTo(userSaved.getId());
    }

    @Test
    @DisplayName("Find Users by userName that contains a given string")
    void testFindByUserNameIgnoreCase(){
        User userSaved = userRepository.save(globalUser);
        List<User> usersFound = userRepository.findByUserNameContainingIgnoreCase("g");
        assertThat(usersFound).isNotEmpty();
        assertThat(usersFound.size()).isEqualTo(1);
        assertThat(usersFound.get(0).getId()).isEqualTo(userSaved.getId());
    }

    @Test
    @DisplayName("Find Users by age")
    void testFindByAge(){
        User userSaved = userRepository.save(globalUser);
        List<User> usersFound = userRepository.findByAge(22);
        assertThat(usersFound).isNotEmpty();
        assertThat(usersFound.size()).isEqualTo(1);
        assertThat(usersFound.get(0).getId()).isEqualTo(userSaved.getId());
        assertThat(usersFound.get(0).getBirthdate()).isEqualTo(userSaved.getBirthdate());
    }
}