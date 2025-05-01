package com.example.service.impl;

import com.example.dto.response.StatsResponse;
import com.example.service.MonitoringService;
import com.example.util.MonitoringUtil;
import org.springframework.stereotype.Service;

@Service
public class MonitoringServiceImpl implements MonitoringService {

    @Override
    public StatsResponse getSystemStats() {
        StatsResponse stats = new StatsResponse();
        stats.setCpu(MonitoringUtil.getCpuUsage());
        stats.setMemory(MonitoringUtil.getMemoryUsage());
        stats.setDisk(MonitoringUtil.getDiskUsage());
        return stats;
    }
}