package com.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record TaskRequest(
    @NotBlank(message = "任务标题不能为空")
    String title,
    
    String description,
    
    @NotNull(message = "任务状态不能为空")
    Boolean completed,
    
    LocalDateTime dueDate
) {}
