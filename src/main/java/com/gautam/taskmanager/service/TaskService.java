package com.gautam.taskmanager.service;

import com.gautam.taskmanager.model.Task;
import com.gautam.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import com.gautam.taskmanager.exception.TaskNotFoundException;
import java.util.List;
import java.util.Optional;
import com.gautam.taskmanager.model.User;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }

    public Task updateTask(int id, Task updatedTask) {

        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setCompleted(updatedTask.isCompleted());

        return taskRepository.save(existingTask);
    }

}
