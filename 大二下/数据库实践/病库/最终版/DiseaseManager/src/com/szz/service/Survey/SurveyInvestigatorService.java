package com.szz.service.Survey;

import com.szz.dao.Survey.SurveyInvestigatorDao;
import com.szz.model.Survey.SurveyInvestigator;

import java.util.ArrayList;
import java.util.List;

public class SurveyInvestigatorService {
    private SurveyInvestigatorDao surveyInvestigatorDao;

    public SurveyInvestigatorService() {
        this.surveyInvestigatorDao = new SurveyInvestigatorDao();
    }

    // 获取所有流调员信息
    public List<SurveyInvestigator> getAllSurveyInvestigators() {
        try {
            return surveyInvestigatorDao.getAllSurveyInvestigators();
        } catch (Exception e) {
            System.err.println("获取所有流调员信息失败: " + e.getMessage());
            // 如果表不存在，返回空列表而不是抛出异常
            if (e.getMessage().contains("doesn't exist") || e.getMessage().contains("not found")) {
                return new ArrayList<>();
            }
            e.printStackTrace();
            throw new RuntimeException("获取所有流调员信息失败", e);
        }
    }
}
