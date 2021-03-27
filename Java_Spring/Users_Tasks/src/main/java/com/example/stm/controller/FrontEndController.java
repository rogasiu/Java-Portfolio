package com.example.stm.controller;

import com.example.stm.model.Task;
import com.example.stm.model.User;
import com.example.stm.model.enums.Status;
import com.example.stm.service.TaskService;
import com.example.stm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class FrontEndController {
    private UserService userService;
    private TaskService taskService;
    @Autowired
    public FrontEndController(UserService userService, TaskService taskService){
        this.userService = userService;
        this.taskService = taskService;
    }

    //tworzenie użytkownika
    @GetMapping("/registration")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "registrationPage";
    }
    @PostMapping("/registration")
    public String createUser(@ModelAttribute User user){
        userService.insertUser(user);
        return "redirect:/registration";
    }

    //zwracanie użytkowników
    @GetMapping("/users")
    public String allUsers(Model model){
        model.addAttribute("allUsers", userService.allUsers());
        return "usersList";
    }

    //zmień status
    @GetMapping("/userStatusChange")
    public String userStatus(@RequestParam (required = false) Integer userId, Model model){
        model.addAttribute("userId", userId);
        return "changeStatusUser";
    }
    @PostMapping("/userStatusChange")
    public String changeStatus(@RequestParam (value = "id",required = true) Integer id){
            userService.changeStatusUser(id);
        return "redirect:/users";
    }
    //usun uzytkownika
    @GetMapping("/userDelete")
    public String userDelete(@RequestParam (required = false) Integer userId, Model model){
        model.addAttribute("userId", userId);
        return "deleteUser";
    }
    @PostMapping("/userDelete")
    public String deleteUser(@RequestParam (value="id", required = true) Integer id){
            userService.deleteUserById(id);
        return "redirect:/users";
    }

    //znajdz uzytkownika
    @GetMapping("/searchUser")
    public String findUser(@RequestParam(value = "data", required = false) String data, Model model){
        if(data != null){
            model.addAttribute("userr", userService.getUserByIdOrEmail(data));
        }
        return "findUser";
    }

    //utworzenie zadania przez użytkownika
    @GetMapping("/addTask")
    public String addTask(Model model){
        model.addAttribute("task", new Task());
        return "addTaskPage";
    }
    @PostMapping("/addTask")
    public String createTask(@ModelAttribute Task task){
        taskService.addTask(task);
        return "redirect:/addTask";
    }

    //wszystkie zadania uporządkowane malejąco
    @GetMapping("/tasksList")
    public String tasksList(Model model){
            model.addAttribute("tasks", taskService.selectTasksByAddDate());
            return "taskList";
    }

    //wyszukanie zadania po nazwie, statusie lub typie
    @GetMapping("/findTask")
    public String findTask(@RequestParam(value = "data", required = false) String data, Model model){
        if(data != null){
            model.addAttribute("taskk", taskService.selectByTitleStatusOrTypes(data));
        }
        return "findTaskPage";
    }

    //zmiana statusu zadania na inny dozwolony status
    @GetMapping("/taskChange")
    public String taskChange(@RequestParam(required = true) Integer taskId, Model model){
        model.addAttribute("taskId", taskId);
        return "changeTask";
    }
    @PostMapping("/taskChange")
    public String taskChange(@RequestParam(value="id",required = true) Integer id, @RequestParam(value="status",required = true) Status status){
        taskService.changeStatus(id, status);
        return "redirect:/tasksList";
    }

    //usunięcie zadania
    @GetMapping("/taskDelete")
    public String taskDelete(@RequestParam (required = false) Integer taskId, Model model){
        model.addAttribute("taskId", taskId);
        return "deleteTask";
    }
    @PostMapping("/taskDelete")
    public String deleteTask(@RequestParam (value="id", required = true) Integer id){
        taskService.deleteTaskById(id);
        return "redirect:/tasksList";
    }



}
