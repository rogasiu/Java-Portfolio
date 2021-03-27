package com.example.stm.service;

import com.example.stm.model.Task;
import com.example.stm.model.User;
import com.example.stm.model.enums.Status;
import com.example.stm.model.enums.Types;
import com.example.stm.repository.TaskRepository;
import com.example.stm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;


    //utworzenie nowego zadania
    public Task addTask(Task task){
        Optional<User> userOptional = userRepository.findById(task.getAuthor().getUserId());
        return userOptional.map(user ->
                taskRepository.save(task)
        ).orElse(null);
    }

    //sortowanie po dacie dodania malejąco
    public List<Task> selectTasksByAddDate() {
        return taskRepository.findAll(Sort.by(Sort.Direction.DESC, "dateAdded"));
    }
    //wyszukanie po nazwie, statusie lub typie
    public List<Task> selectByTitleStatusOrTypes(String value){
        if(value.equalsIgnoreCase(Status.DONE.getStatusName()) || value.equalsIgnoreCase(Status.NEW.getStatusName()) || value.equalsIgnoreCase(Status.IN_PROGRESS.getStatusName())){
            value = value.toUpperCase();
            Status status = Status.valueOf(value);
            return taskRepository.findTasksByStatus(status);
        }else if(value.equalsIgnoreCase(Types.BUG.getTypeName()) || value.equalsIgnoreCase(Types.FEATURE.getTypeName()) ||
                value.equalsIgnoreCase(Types.TASK.getTypeName())){
            value = value.toUpperCase();
            Types types = Types.valueOf(value);
            return taskRepository.findTasksByTypes(types);
        }else{
            return taskRepository.findTasksByTitle(value);
        }
    }

    // zmiana statusu zadania na inny dozwolony status
    public boolean changeStatus(int taskId, Status status){
        boolean isChanged = false;
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if(taskOptional.isPresent()){
            Task taskToChange = taskOptional.get();
            if(taskToChange.getStatus() == (status)) {
                isChanged = false;
            } else {
                taskToChange.setStatus(status);
                taskRepository.save(taskToChange);
                isChanged = true;
            }
        }
        return isChanged;
    }


    //usuniecie tylko zadania
    public boolean deleteTaskById(int taskId){
        AtomicBoolean isDeleted = new AtomicBoolean(false);
        taskRepository.findById(taskId).ifPresent(task -> {
            taskRepository.deleteById(taskId);
            isDeleted.set(true);
        });
        return isDeleted.get();
    }
}
