package com.example.entity;

import lombok.Data;

@Data
public class DashboardData {

    private Long userCount;         // 原为 Integer，改为 Long
    private Long taskCount;         // 同上
    private Long teamCount;         // 同上
    private Long competitionCount;  // 同上
    private Integer onlineUserCount; // 在线用户数保留 Integer（数值通常较小）
}
