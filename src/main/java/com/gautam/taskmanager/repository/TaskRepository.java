package com.gautam.taskmanager.repository;

import com.gautam.taskmanager.model.Task;
import com.gautam.taskmanager.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository
        extends JpaRepository<Task, Integer> {

    List<Task> findByUser(User user);
}