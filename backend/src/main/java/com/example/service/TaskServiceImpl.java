package com.example.service;

import com.example.dto.request.TaskRequest;
import com.example.dto.response.TaskResponse;
import com.example.entity.Task;
import com.example.entity.User;
import com.example.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskResponse createTask(TaskRequest request, User user) {
        Task task = Task.builder()
            .title(request.title())
            .description(request.description())
            .completed(request.completed())
            .dueDate(request.dueDate())
            .user(user)
            .build();

        Task savedTask = taskRepository.save(task);
        return new TaskResponse(savedTask);
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public TaskResponse getTaskResponseById(Long id) {
        return getTaskById(id)
            .map(TaskResponse::new)
            .orElseThrow(() -> new IllegalArgumentException("任务不存在"));
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
            .map(TaskResponse::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> getTasksByUser(User user) {
        return taskRepository.findByUser(user).stream()
            .map(TaskResponse::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> getCompletedTasks(User user) {
        return taskRepository.findByUserAndCompleted(user, true).stream()
            .map(TaskResponse::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> getIncompleteTasks(User user) {
        return taskRepository.findByUserAndCompleted(user, false).stream()
            .map(TaskResponse::new)
            .collect(Collectors.toList());
    }

    @Override
    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = getTaskById(id)
            .orElseThrow(() -> new IllegalArgumentException("任务不存在"));

        if (request.title() != null) {
            task.setTitle(request.title());
        }

        if (request.description() != null) {
            task.setDescription(request.description());
        }

        if (request.completed() != null) {
            task.setCompleted(request.completed());
        }

        if (request.dueDate() != null) {
            task.setDueDate(request.dueDate());
        }

        Task updatedTask = taskRepository.save(task);
        return new TaskResponse(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("任务不存在");
        }
        taskRepository.deleteById(id);
    }
}
