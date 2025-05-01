package com.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TeamRequest(
    @NotBlank(message = "团队名称不能为空")
    @Size(min = 2, max = 50, message = "团队名称长度必须在2到50个字符之间")
    String name,
    
    @NotBlank(message = "团队描述不能为空")
    @Size(max = 255, message = "团队描述长度不能超过255个字符")
    String description
) {}
