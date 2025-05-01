package com.example.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record CompetitionRequest(
    @NotBlank(message = "竞赛名称不能为空")
    @Size(min = 3, max = 100, message = "竞赛名称长度必须在3到100个字符之间")
    String name,
    
    @NotBlank(message = "竞赛描述不能为空")
    String description,
    
    @NotNull(message = "竞赛开始时间不能为空")
    LocalDateTime startTime,
    
    @NotNull(message = "竞赛结束时间不能为空")
    @Future(message = "竞赛结束时间必须晚于当前时间")
    LocalDateTime endTime,
    
    @NotNull(message = "参赛人数上限不能为空")
    @Min(value = 1, message = "参赛人数上限至少为1")
    Integer maxParticipants,
    
    String rules
) {
    // 自定义验证：结束时间必须晚于开始时间
    public CompetitionRequest {
        if (endTime != null && startTime != null && endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("竞赛结束时间必须晚于开始时间");
        }
    }
}
