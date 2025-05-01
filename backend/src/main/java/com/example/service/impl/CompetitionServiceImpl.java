package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Competition;
import com.example.mapper.CompetitionMapper;
import com.example.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    @Autowired
    private CompetitionMapper competitionMapper;

    @Override
    public List<Competition> listCompetitions() {
        return competitionMapper.selectList(null);
    }

    @Override
    public Competition getCompetitionById(Long id) {
        return competitionMapper.selectById(id);
    }

    @Override
    public boolean participateCompetition(Long competitionId, Long userId) {
        Competition competition = competitionMapper.selectById(competitionId);
        if (competition != null && competition.getStatus() == 1) { // 1表示进行中
            // 这里可以添加用户参与竞赛的逻辑
            return true;
        }
        return false;
    }
}    