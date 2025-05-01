package com.example.controller;

import com.example.dto.request.TaskRequest;
import com.example.exception.ResourceNotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.example.security.UserPrincipal;
import com.example.dto.response.ApiResponse;
import com.example.dto.response.TaskResponse;
import com.example.entity.Task;
import com.example.entity.User;
import com.example.service.TaskService;
import com.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    // 创建任务
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TaskResponse> createTask(
	    @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody TaskRequest request
    ) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("用户不存在"));
        
        Task task = taskService.createTask(user, request);
        return ResponseEntity.ok(new TaskResponse(task, currentUser.getId()));
    }

    // 获取任务列表
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasksByUser(@RequestParam Long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("用户不存在"));
        
        List<Task> tasks = taskService.getTasksByUser(user);
        List<TaskResponse> responses = tasks.stream()
                .map(task -> new TaskResponse(task, currentUser.getId()))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(responses);
    }

    // 获取单个任务
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable Long taskId,
	    @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        Task task = taskService.getTaskById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("任务不存在"));
        
        return ResponseEntity.ok(new TaskResponse(task, currentUser.getId()));
    }

    // 更新任务
    @PutMapping("/{taskId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long taskId,
	    @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody TaskRequest request
    ) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("用户不存在"));
        
        Task task = taskService.updateTask(taskId, user, request);
        return ResponseEntity.ok(new TaskResponse(task, currentUser.getId()));
    }

    // 删除任务
    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> deleteTask(
            @PathVariable Long taskId,
	    @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("用户不存在"));
        
        taskService.deleteTask(taskId, user);
        return ResponseEntity.ok(new ApiResponse(true, "任务已删除"));
    }
}
