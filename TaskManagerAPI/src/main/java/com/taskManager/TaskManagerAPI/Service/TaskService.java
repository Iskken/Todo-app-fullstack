package com.taskManager.TaskManagerAPI.Service;

/* The class is responsible for managing the tasks for every user*/

import com.taskManager.TaskManagerAPI.Database.TaskSpringDataJpa;
import com.taskManager.TaskManagerAPI.Pojo.Task;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class  TaskService {
    private TaskSpringDataJpa taskSpringDataJpa;
    
    public TaskService(TaskSpringDataJpa taskSpringDataJpa)
    {
        this.taskSpringDataJpa = taskSpringDataJpa;
    }

    public void saveTask(Task task)
    {
        taskSpringDataJpa.saveAndFlush(task);
    }

    public void deleteTask(Long id)
    {
        taskSpringDataJpa.deleteById(id);
    }

    public List<Task> getTasks(String username) {
        return taskSpringDataJpa.findAllByAuthor(username);
    }

    public Task getTask(Long id) {
        return taskSpringDataJpa.findById(id).orElse(null);
    }

    public void changeStatus(Long id) {
        Task task = taskSpringDataJpa.getReferenceById(id);

        if (task == null)
        {
            throw new RuntimeException("Task with this label does not exist");
        }

        try
        {
            Boolean currentStatus = task.getDone();
            task.setDone(!currentStatus);
            taskSpringDataJpa.saveAndFlush(task);//TODO: check if it does not create a new instance of task
        }
        catch (Exception ex)
        {
            throw new RuntimeException("The status could not get loaded");
        }
    }
}
