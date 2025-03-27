package com.taskManager.TaskManagerAPI.Controller;

import com.nimbusds.jwt.JWT;
import com.taskManager.TaskManagerAPI.DTO.TaskDTO;
import com.taskManager.TaskManagerAPI.Database.TaskSpringDataJpa;
import com.taskManager.TaskManagerAPI.Pojo.Task;
import com.taskManager.TaskManagerAPI.Pojo.User;
import com.taskManager.TaskManagerAPI.Service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/* The class is responsible for handling http requests */

@RestController
public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService)
    {
        this.taskService = taskService;
    }

    @GetMapping(path="/getTasks")
    public List<Task> retreiveTasks(@AuthenticationPrincipal Jwt jwt)
    {
        return taskService.getTasks(jwt.getClaim("sub"));
    }

    @GetMapping(path="/getTasks/{id}")
    public ResponseEntity<?> retreiveTaskByLabel(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id)
    {
        Task task = taskService.getTask(id);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        return ResponseEntity.ok(task);
    }

    @PostMapping(path="/addTask")
        public ResponseEntity<?> addNewTask(@AuthenticationPrincipal Jwt jwt, @RequestBody TaskDTO taskDTO)
    {
        Task newTask = new Task();
        newTask.setAuthor(jwt.getClaim("sub"));
        newTask.setDescription(taskDTO.getDescription());
        newTask.setDueDate(taskDTO.getDueDate());
        newTask.setCreationDate(LocalDate.now());
        newTask.setLabel(taskDTO.getLabel());

        taskService.saveTask(newTask);

        return ResponseEntity.ok(newTask);
    }

    @DeleteMapping(path="/deleteTask/{id}")
    public ResponseEntity<?> deleteTask(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id)
    {
        taskService.deleteTask(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping(path ="/changeStatus/{id}")
    public ResponseEntity<?> changeStatus(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id)
    {
        taskService.changeStatus(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping(path="/updateTask/{id}")
    public ResponseEntity<?> updateTask(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @RequestBody TaskDTO taskDTO)
    {
        Task task = taskService.getTask(id);
        //TODO: add check for if task is null
//        if (task)
//        {
//
//        }
        task.setLabel(taskDTO.getLabel());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());

        taskService.saveTask(task); //TODO: check is not new instance of task appears with the previous version of it remaining

        return ResponseEntity.ok(task);
    }
}
