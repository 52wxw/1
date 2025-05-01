package com.example.service;

import com.example.dto.response.StatsResponse;

public interface MonitoringService {

    StatsResponse getSystemStats();
}