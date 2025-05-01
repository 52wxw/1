package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Team;
import com.example.mapper.TeamMapper;
import com.example.mapper.UserMapper;
import com.example.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Team> listTeams() {
        return teamMapper.selectList(null);
    }

    @Override
    public Team createTeam(Team team) {
        team.setCreateTime(new Date());
        team.setUpdateTime(new Date());
        team.setMemberCount(1); // 初始成员数为1
        teamMapper.insert(team);
        return teamMapper.selectById(team.getId());
    }

    @Override
    public boolean joinTeam(Long teamId, Long userId) {
        Team team = teamMapper.selectById(teamId);
        if (team != null) {
            // 检查用户是否存在
            if (userMapper.selectById(userId) != null) {
                // 增加团队成员数
                team.setMemberCount(team.getMemberCount() + 1);
                team.setUpdateTime(new Date());
                teamMapper.updateById(team);
                // 这里可以添加用户与团队的关联关系
                return true;
            }
        }
        return false;
    }
}    