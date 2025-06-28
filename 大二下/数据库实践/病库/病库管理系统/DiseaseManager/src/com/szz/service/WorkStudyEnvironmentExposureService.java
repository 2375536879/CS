package com.szz.service;

import com.szz.dao.WorkStudyEnvironmentExposureDao;
import com.szz.model.WorkStudyEnvironmentExposure;

import java.util.ArrayList;
import java.util.List;

public class WorkStudyEnvironmentExposureService {
    private WorkStudyEnvironmentExposureDao workStudyEnvironmentExposureDao;

    public WorkStudyEnvironmentExposureService() {
        this.workStudyEnvironmentExposureDao = new WorkStudyEnvironmentExposureDao();
    }

    // 获取所有工作学习环境记录
    public List<WorkStudyEnvironmentExposure> getAllWorkStudyEnvironmentExposures() {
        try {
            return workStudyEnvironmentExposureDao.getAllWorkStudyEnvironmentExposures();
        } catch (Exception e) {
            System.err.println("获取所有工作学习环境记录失败: " + e.getMessage());
            // 如果表不存在，返回空列表而不是抛出异常
            if (e.getMessage().contains("doesn't exist") || e.getMessage().contains("not found")) {
                return new ArrayList<>();
            }
            e.printStackTrace();
            throw new RuntimeException("获取所有工作学习环境记录失败", e);
        }
    }
}
