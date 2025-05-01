package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Task;
import com.example.mapper.TaskMapper;
import com.example.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<Task> listTasks(Integer status) {
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        return taskMapper.selectList(queryWrapper);
    }

    @Override
    public Task submitTask(Task task) {
        task.setCreateTime(new Date());
        task.setUpdateTime(new Date());
        task.setStatus(1); // 默认待处理
        taskMapper.insert(task);
        return taskMapper.selectById(task.getId());
    }

    @Override
    public Task updateTask(Task task) {
        task.setUpdateTime(new Date());
        taskMapper.updateById(task);
        return taskMapper.selectById(task.getId());
    }

    @Override
    public boolean deleteTask(Long id) {
        int result = taskMapper.deleteById(id);
        return result > 0;
    }
}
