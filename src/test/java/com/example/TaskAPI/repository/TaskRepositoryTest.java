package com.example.TaskAPI.repository;

import com.example.TaskAPI.AbstractIntegrationDBTest;
import com.example.TaskAPI.entities.Task;
import com.example.TaskAPI.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TaskRepositoryTest extends AbstractIntegrationDBTest {
    UserRepository userRepository;
    TaskRepository taskRepository;

    @Autowired
    public TaskRepositoryTest(UserRepository userRepository, TaskRepository taskRepository){
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }
    private User globalUser;
    private Task globalTask;
    private Task globalTask2;
    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        taskRepository.deleteAll();
        globalUser = User.builder()
                .name("Juan Bananero")
                .birthdate(LocalDate.of(2003,5,4))
                .userName("Juan7diaz")
                .password("juanbananero@120")
                .build();
        globalTask = Task.builder()
                .title("Estudiar para parcial puchoso")
                .description("El parcial va a ser el jueves y entra subredes y enrutamiento")
                .isCompleted(false)
                .user(globalUser)
                .build();
        globalTask2 = Task.builder()
                .title("Hacer el taller de cátedra")
                .description("Hacer y estudiar el taller de cátedra el jueves")
                .isCompleted(false)
                .user(globalUser)
                .build();
        userRepository.save(globalUser);
    }

    @Test
    @DisplayName("[Create] Given a new Task, when is saved, it persist in DB")
    void testCreate(){
        Task taskToSave = Task.builder()
                .title("Hacer las evaluaciones de preprácticas")
                .description("Revisar el chat con Gina, están las evaluaciones")
                .isCompleted(true)
                .user(globalUser)
                .build();
        Task taskSaved = taskRepository.save(taskToSave);
        assertThat(taskSaved).isNotNull();
        assertThat(taskSaved.getId()).isNotNull();
        assertThat(taskSaved.getTitle()).isEqualTo(taskToSave.getTitle());
    }
    @Test
    @DisplayName("[Read] Get all tasks of DB")
    void testRead(){
        taskRepository.save(globalTask);
        taskRepository.save(globalTask2);
        List<Task> tasks = taskRepository.findAll();
        assertThat(tasks.size()).isEqualTo(2);
        assertThat(tasks.get(0).getUser().getUserName()).isEqualTo(globalUser.getUserName());
        assertThat(tasks.get(0).getIsCompleted()).isEqualTo(false);
    }
    @Test
    @DisplayName("[Update] Update task by given id")
    void testUpdate(){
        Task taskToFind = taskRepository.save(globalTask);
        UUID idToFind = taskToFind.getId();
        Optional<Task> taskFound = taskRepository.findById(idToFind);
        //Verify if task is present
        assertThat(taskFound).isPresent();

        String titleUpdated = "Parcial extremadamente puchoso";
        taskFound.get().setTitle(titleUpdated);
        Task taskUpdated = taskRepository.save(taskFound.get());

        //Verify if user was updated
        assertThat(taskUpdated.getTitle()).isEqualTo(titleUpdated);
    }
    @Test
    @DisplayName("[Delete] Delete a Task by given id")
    void testDelete(){
        Task taskSaved = taskRepository.save(globalTask);
        Task taskSaved2 = taskRepository.save(globalTask2);

        assertThat(taskRepository.findAll().size()).isEqualTo(2);

        UUID idToDelete = taskSaved.getId();
        taskRepository.deleteById(idToDelete);

        List<Task> allTasks = taskRepository.findAll();
        assertThat(allTasks.size()).isEqualTo(1);
        assertThat(allTasks.get(0).getTitle()).isEqualTo(taskSaved2.getTitle());
    }
    @Test
    @DisplayName("Find tasks by User id")
    void testFindByUserId(){
        Task taskSaved = taskRepository.save(globalTask);
        Task taskSaved2 = taskRepository.save(globalTask2);
        UUID idToFind = globalUser.getId();

        List<Task> tasksFound = taskRepository.findByUserId(idToFind);
        assertThat(tasksFound.size()).isEqualTo(2);
        assertThat(tasksFound.get(0).getDescription()).isEqualTo(taskSaved.getDescription());
        assertThat(tasksFound.get(1).getIsCompleted()).isEqualTo(taskSaved2.getIsCompleted());
    }
}