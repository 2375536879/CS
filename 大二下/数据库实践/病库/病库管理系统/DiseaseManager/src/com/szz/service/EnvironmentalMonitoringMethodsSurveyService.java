package com.szz.service;

import com.szz.dao.EnvironmentalMonitoringMethodsSurveyDao;
import com.szz.model.EnvironmentalMonitoringMethodsSurvey;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentalMonitoringMethodsSurveyService {
    private EnvironmentalMonitoringMethodsSurveyDao environmentalMonitoringMethodsSurveyDao;

    public EnvironmentalMonitoringMethodsSurveyService() {
        this.environmentalMonitoringMethodsSurveyDao = new EnvironmentalMonitoringMethodsSurveyDao();
    }

    // 获取所有环境监测方法记录
    public List<EnvironmentalMonitoringMethodsSurvey> getAllEnvironmentalMonitoringMethodsSurveys() {
        try {
            return environmentalMonitoringMethodsSurveyDao.getAllEnvironmentalMonitoringMethodsSurveys();
        } catch (Exception e) {
            System.err.println("获取所有环境监测方法记录失败: " + e.getMessage());
            // 如果表不存在，返回空列表而不是抛出异常
            if (e.getMessage().contains("doesn't exist") || e.getMessage().contains("not found")) {
                return new ArrayList<>();
            }
            e.printStackTrace();
            throw new RuntimeException("获取所有环境监测方法记录失败", e);
        }
    }
}
