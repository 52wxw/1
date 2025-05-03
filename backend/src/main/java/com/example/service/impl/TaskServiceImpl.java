package com.example.service.impl;

import com.example.entity.Task;
import com.example.repository.TaskRepository;
import com.example.service.TaskService;
import com.example.dto.TaskRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(TaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setCategory(taskRequest.getCategory());
        task.setLevel(taskRequest.getLevel());
        task.setPoints(taskRequest.getPoints());
        task.setActive(true);

        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setCategory(taskRequest.getCategory());
        task.setLevel(taskRequest.getLevel());
        task.setPoints(taskRequest.getPoints());

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        taskRepository.delete(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> getTasksByCategory(String category) {
        return taskRepository.findByCategory(category);
    }

    @Override
    public List<Task> getTasksByLevel(String level) {
        return taskRepository.findByLevel(level);
    }
}    