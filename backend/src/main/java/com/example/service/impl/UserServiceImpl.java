package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.example.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null && PasswordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public User register(User user) {
        // 检查用户名是否已存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        User existingUser = userMapper.selectOne(queryWrapper);
        if (existingUser != null) {
            return null;
        }

        // 加密密码
        user.setPassword(PasswordEncoder.encode(user.getPassword()));
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setStatus(1);
        user.setRole(2); // 默认普通用户

        userMapper.insert(user);
        return user;
    }

    @Override
    public User getUserById(Long userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public User updateUser(User user) {
        user.setUpdateTime(new Date());
        userMapper.updateById(user);
        return userMapper.selectById(user.getId());
    }

    @Override
    public List<User> listUsers() {
        return userMapper.selectList(null);
    }

    @Override
    public boolean banUser(Long userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            user.setStatus(0); // 0表示封禁
            userMapper.updateById(user);
            return true;
        }
        return false;
    }
}
