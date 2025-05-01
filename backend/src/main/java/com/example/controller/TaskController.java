package com.example.controller;

import com.example.common.Result;
import com.example.entity.Task;
import com.example.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/list")
    public Result listTasks(@RequestParam(required = false) Integer status) {
        List<Task> tasks = taskService.listTasks(status);
        return Result.success(tasks);
    }

    @PostMapping("/submit")
    public Result submitTask(@RequestBody Task task) {
        Task newTask = taskService.submitTask(task);
        if (newTask != null) {
            return Result.success(newTask);
        } else {
            return Result.error("提交失败");
        }
    }

    @PutMapping("/update")
    public Result updateTask(@RequestBody Task task) {
        Task updatedTask = taskService.updateTask(task);
        if (updatedTask != null) {
            return Result.success(updatedTask);
        } else {
            return Result.error("更新失败");
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteTask(@PathVariable Long id) {
        boolean success = taskService.deleteTask(id);
        if (success) {
            return Result.success();
        } else {
            return Result.error("删除失败");
        }
    }
}
