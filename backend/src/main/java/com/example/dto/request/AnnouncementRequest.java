package com.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnnouncementRequest(
    @NotBlank(message = "公告标题不能为空")
    String title,
    
    @NotBlank(message = "公告内容不能为空")
    String content,
    
    @NotNull(message = "公告状态不能为空")
    Boolean isPublic
) {}
