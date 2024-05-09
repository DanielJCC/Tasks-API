package com.example.TaskAPI.repository;

import com.example.TaskAPI.AbstractIntegrationDBTest;
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

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest extends AbstractIntegrationDBTest {
    UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    private User globalUser;

    @BeforeEach
    void setUp(){
        userRepository.deleteAll();
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
        Optional<User> userFinded = userRepository.findById(idToFind);
        //Verify if user is present
        assertThat(userFinded).isPresent();

        String userNameUpdated = "GinAsotori";
        userFinded.get().setUserName(userNameUpdated);
        User userUpdated = userRepository.save(userFinded.get());

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
}