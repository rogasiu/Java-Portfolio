package com.example.stm.controller;

import com.example.stm.model.Task;
import com.example.stm.model.User;
import com.example.stm.model.enums.Status;
import com.example.stm.model.enums.Types;
import com.example.stm.service.TaskService;
import com.example.stm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api")
@RestController
public class STMController {
    UserService userService;
    TaskService taskService;

    @Autowired
    public STMController(UserService userService, TaskService taskService){
        this.userService = userService;
        this.taskService = taskService;
    }
    //utworzenie nowego użytkownika
    @PostMapping("/users/registration")
    public User registerUser(
            @RequestParam("name") String name,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ){
        return userService.insertUser(new User(name, lastName, email, password, false));
    }

    //zwraca wszystkich użytkowników
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.allUsers();
    }

    //wyszukanie po kluczu głównym lub po mailu
    @GetMapping("/users/search")
    public Optional<User> searchUser(
            @RequestParam("id lub adres email") String value
    ){
        return userService.getUserByIdOrEmail(value);
    }

    //zmiana statusu na przeciwny
    @PutMapping("/users/statusChange/id={userId}")
    public boolean chagneUserStatus(
            @PathVariable("userId") int userId
    ){
        return userService.changeStatusUser(userId);
    }

    //usunięcie użytkownika z jego relacjami
    @DeleteMapping("/users/delete")
    public boolean deleteUserById(
            @RequestParam("userId") int userId
    ){
        return userService.deleteUserById(userId);
    }

    //utworzenie nowego zadania
    @PostMapping("/tasks/add")
    public Task addTask(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("type") Types types,
            @RequestParam("status") Status status,
            @RequestParam("userId") User user
    ){
        return taskService.addTask(new Task(title, description, types, status, user));
    }

    //sortowanie po dacie dodania malejąco
    @GetMapping("/tasks")
    public List<Task> allTasks() {
        return taskService.selectTasksByAddDate();
    }

    //wyszukanie po nazwie, statusie lub typie
    @GetMapping("/tasks/search")
    public List<Task> searchTasks(
            @RequestParam("title, status or type") String value
    ){
        return taskService.selectByTitleStatusOrTypes(value);
    }

    // zmiana statusu zadania na inny dozwolony status
    @PutMapping("/tasks/changeStatus")
    public boolean changeStatus(
            @RequestParam("taskId") int taskId,
            @RequestParam("status") Status status
    ){
        return taskService.changeStatus(taskId, status);
    }

    //usuniecie tylko zadania
    @DeleteMapping("/tasks/delete")
    public boolean deleteTask(
            @RequestParam("taskId") int taskId
    ){
        return taskService.deleteTaskById(taskId);
    }
}
