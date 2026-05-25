package com.gautam.taskmanager.controller;

import com.gautam.taskmanager.model.Task;
import com.gautam.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import com.gautam.taskmanager.dto.TaskDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.gautam.taskmanager.repository.UserRepository;
import com.gautam.taskmanager.model.User;

@RestController
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    public TaskController(TaskService taskService,
                          UserRepository userRepository) {

        this.taskService = taskService;
        this.userRepository = userRepository;
    }

    @GetMapping("/tasks")
    public List<Task> getTasks() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String username =
                authentication.getName();

        User user =
                userRepository.findByUsername(username)
                        .orElseThrow();

        return taskService.getTasksByUser(user);
    }



    @PostMapping("/tasks")
    public Task createTask(@Valid @RequestBody TaskDTO taskDTO) {

        Task task = new Task();

        task.setTitle(taskDTO.getTitle());
        task.setCompleted(taskDTO.isCompleted());
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String username =
                authentication.getName();

        User user =
                userRepository.findByUsername(username)
                        .orElseThrow();
        task.setUser(user);

        return taskService.createTask(task);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
    }

    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable int id,
                           @RequestBody Task updatedTask) {

        return taskService.updateTask(id, updatedTask);
    }

    @GetMapping("/admin/tasks")
    public List<Task> getAllTasksForAdmin() {

        return taskService.getAllTasks();
    }

}