package com.example.service;

import com.example.entity.Task;
import com.example.dto.TaskRequest;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(TaskRequest taskRequest);
    Task updateTask(Long id, TaskRequest taskRequest);
    void deleteTask(Long id);
    List<Task> getAllTasks();
    Optional<Task> getTaskById(Long id);
    List<Task> getTasksByCategory(String category);
    List<Task> getTasksByLevel(String level);
}    