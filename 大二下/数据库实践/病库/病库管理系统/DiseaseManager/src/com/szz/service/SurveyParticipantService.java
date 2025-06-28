package com.szz.service;

import com.szz.dao.SurveyParticipantDao;
import com.szz.model.SurveyParticipant;

import java.util.List;

public class SurveyParticipantService {
    private SurveyParticipantDao surveyParticipantDao;

    public SurveyParticipantService() {
        this.surveyParticipantDao = new SurveyParticipantDao();
    }

    // 创建流调参与者
    public int createSurveyParticipant(SurveyParticipant participant) {
        try {
            return surveyParticipantDao.createSurveyParticipant(participant);
        } catch (Exception e) {
            System.err.println("创建流调参与者失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("创建流调参与者失败", e);
        }
    }

    // 根据ID获取流调参与者
    public SurveyParticipant getSurveyParticipantById(int id) {
        try {
            return surveyParticipantDao.getSurveyParticipantById(id);
        } catch (Exception e) {
            System.err.println("获取流调参与者失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("获取流调参与者失败", e);
        }
    }

    // 根据患者ID获取流调参与者列表
    public List<SurveyParticipant> getSurveyParticipantsByPatientId(int patientId) {
        try {
            return surveyParticipantDao.getSurveyParticipantsByPatientId(patientId);
        } catch (Exception e) {
            System.err.println("获取患者流调参与者列表失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("获取患者流调参与者列表失败", e);
        }
    }

    // 获取所有流调参与者
    public List<SurveyParticipant> getAllSurveyParticipants() {
        try {
            return surveyParticipantDao.getAllSurveyParticipants();
        } catch (Exception e) {
            System.err.println("获取所有流调参与者失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("获取所有流调参与者失败", e);
        }
    }

    // 更新流调参与者
    public boolean updateSurveyParticipant(SurveyParticipant participant) {
        try {
            return surveyParticipantDao.updateSurveyParticipant(participant);
        } catch (Exception e) {
            System.err.println("更新流调参与者失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("更新流调参与者失败", e);
        }
    }

    // 删除流调参与者
    public boolean deleteSurveyParticipant(int id) {
        try {
            return surveyParticipantDao.deleteSurveyParticipant(id);
        } catch (Exception e) {
            System.err.println("删除流调参与者失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("删除流调参与者失败", e);
        }
    }

    // 验证流调参与者数据
    public boolean validateSurveyParticipant(SurveyParticipant participant) {
        if (participant == null) {
            return false;
        }
        
        // 检查必填字段
        if (participant.getPatientId() <= 0) {
            return false;
        }
        
        if (participant.getParticipantName() == null || participant.getParticipantName().trim().isEmpty()) {
            return false;
        }
        
        return true;
    }

    // 搜索流调参与者
    public List<SurveyParticipant> searchSurveyParticipants(String keyword) {
        try {
            List<SurveyParticipant> allParticipants = surveyParticipantDao.getAllSurveyParticipants();
            return allParticipants.stream()
                    .filter(participant -> 
                        (participant.getParticipantName() != null && participant.getParticipantName().toLowerCase().contains(keyword.toLowerCase())) ||
                        (participant.getContactPhone() != null && participant.getContactPhone().contains(keyword)) ||
                        (participant.getHomeAddress() != null && participant.getHomeAddress().toLowerCase().contains(keyword.toLowerCase()))
                    )
                    .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            System.err.println("搜索流调参与者失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("搜索流调参与者失败", e);
        }
    }
}
