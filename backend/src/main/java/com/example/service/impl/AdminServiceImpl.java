package com.example.service.impl;

import com.example.entity.DashboardData;
import com.example.mapper.*;
import com.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private CompetitionMapper competitionMapper;

    @Override
    public DashboardData getDashboardData() {
        DashboardData dashboardData = new DashboardData();
        // selectCount 返回 Long，直接赋值（与 DashboardData 字段类型匹配）
        dashboardData.setUserCount(userMapper.selectCount(null));
        dashboardData.setTaskCount(taskMapper.selectCount(null));
        dashboardData.setTeamCount(teamMapper.selectCount(null));
        dashboardData.setCompetitionCount(competitionMapper.selectCount(null));
        dashboardData.setOnlineUserCount(100); // 保持 Integer 类型（模拟值）
        return dashboardData;
    }

    @Override
    public void updateUserRole(Long userId, Integer role) {
        userMapper.updateRole(userId, role);
    }
}
