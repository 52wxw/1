package com.example.service;

import com.example.entity.DashboardData;

public interface AdminService {

    DashboardData getDashboardData();
    void updateUserRole(Long userId, Integer role);
}    
