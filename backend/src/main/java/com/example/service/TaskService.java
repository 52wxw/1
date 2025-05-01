package com.example.service;

import com.example.entity.Task;

import java.util.List;

public interface TaskService {

    List<Task> listTasks(Integer status);

    Task submitTask(Task task);

    Task updateTask(Task task);

    boolean deleteTask(Long id);
}
