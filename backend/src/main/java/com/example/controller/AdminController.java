package com.example.controller;

import com.example.common.Result;
import com.example.entity.DashboardData;
import com.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public Result getDashboardData() {
        try {
            DashboardData data = adminService.getDashboardData();
            return Result.success(data);
        } catch (Exception e) {
            // 添加返回语句，处理异常情况
            return Result.error("获取仪表盘数据失败：" + e.getMessage());
        }
    }

    @PostMapping("/user/{userId}/role")
    public Result updateUserRole(@PathVariable Long userId, @RequestBody Integer role) {
        try {
            adminService.updateUserRole(userId, role);
            return Result.success();
        } catch (Exception e) {
            // 添加返回语句，处理异常情况
            return Result.error("更新用户角色失败：" + e.getMessage());
        }
    }
}
