package com.szz.service;

import com.szz.dao.UrbanRuralEnvironmentMonitoringDao;
import com.szz.model.UrbanRuralEnvironmentMonitoring;

import java.util.ArrayList;
import java.util.List;

public class UrbanRuralEnvironmentMonitoringService {
    private UrbanRuralEnvironmentMonitoringDao urbanRuralEnvironmentMonitoringDao;

    public UrbanRuralEnvironmentMonitoringService() {
        this.urbanRuralEnvironmentMonitoringDao = new UrbanRuralEnvironmentMonitoringDao();
    }

    // 获取所有城乡环境监测记录
    public List<UrbanRuralEnvironmentMonitoring> getAllUrbanRuralEnvironmentMonitorings() {
        try {
            return urbanRuralEnvironmentMonitoringDao.getAllUrbanRuralEnvironmentMonitorings();
        } catch (Exception e) {
            System.err.println("获取所有城乡环境监测记录失败: " + e.getMessage());
            // 如果表不存在，返回空列表而不是抛出异常
            if (e.getMessage().contains("doesn't exist") || e.getMessage().contains("not found")) {
                return new ArrayList<>();
            }
            e.printStackTrace();
            throw new RuntimeException("获取所有城乡环境监测记录失败", e);
        }
    }
}
