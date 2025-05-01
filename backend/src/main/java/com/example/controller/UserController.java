package com.example.controller;

import com.example.common.Result;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public Result getUserInfo(@RequestParam Long userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error("用户不存在");
        }
    }

    @PutMapping("/update")
    public Result updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        if (updatedUser != null) {
            return Result.success(updatedUser);
        } else {
            return Result.error("更新失败");
        }
    }

    @GetMapping("/list")
    public Result listUsers() {
        List<User> users = userService.listUsers();
        return Result.success(users);
    }
}
