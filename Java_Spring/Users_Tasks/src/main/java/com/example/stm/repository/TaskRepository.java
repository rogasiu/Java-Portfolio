package com.example.stm.repository;

import com.example.stm.model.Task;
import com.example.stm.model.enums.Status;
import com.example.stm.model.enums.Types;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("SELECT t FROM Task t WHERE t.author.userId = :userId")
    List<Task> findTasksUser(@Param("userId") Integer userId);

    //SELECT * FROM tasks WHERE title = ?;
    List<Task> findTasksByTitle(String title);

    //SELECT * FROM tasks WHERE status =?;
    List<Task> findTasksByStatus(Status status);

    //SELECT * FROM tasks WHERE types =?;
    List<Task> findTasksByTypes(Types type);

}
