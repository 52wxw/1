package com.example.dto.response;

import com.example.entity.Task;
import com.example.entity.User;
import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        Boolean completed,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime dueDate,
        UserResponse user // 保持用户响应字段
) {
    // 新增构造器：接收 Task 实体和当前用户 ID
    public TaskResponse(Task task, Long currentUserId) {
        this(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getCompleted(),
            task.getCreatedAt(),
            task.getUpdatedAt(),
            task.getDueDate(),
            new UserResponse(task.getUser(), currentUserId) // 传递 currentUserId
        );
    }

    // 可选：保留无参构造器（如需反序列化）
    public TaskResponse {}
}
