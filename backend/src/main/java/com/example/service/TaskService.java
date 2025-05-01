package com.example.service;

import com.example.dto.request.TaskRequest;
import com.example.dto.response.TaskResponse;
import com.example.entity.Task;
import com.example.entity.User;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    TaskResponse createTask(TaskRequest request, User user);

    Optional<Task> getTaskById(Long id);

    TaskResponse getTaskResponseById(Long id);

    List<TaskResponse> getAllTasks();

    List<TaskResponse> getTasksByUser(User user);

    List<TaskResponse> getCompletedTasks(User user);

    List<TaskResponse> getIncompleteTasks(User user);

    TaskResponse updateTask(Long id, TaskRequest request);

    void deleteTask(Long id);
}
