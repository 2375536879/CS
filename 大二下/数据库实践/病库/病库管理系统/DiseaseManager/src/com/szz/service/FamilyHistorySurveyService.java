package com.szz.service;

import com.szz.dao.FamilyHistorySurveyDao;
import com.szz.model.FamilyHistorySurvey;

import java.util.ArrayList;
import java.util.List;

public class FamilyHistorySurveyService {
    private FamilyHistorySurveyDao familyHistorySurveyDao;

    public FamilyHistorySurveyService() {
        this.familyHistorySurveyDao = new FamilyHistorySurveyDao();
    }

    // 创建家族史记录
    public int createFamilyHistorySurvey(FamilyHistorySurvey familyHistory) {
        try {
            return familyHistorySurveyDao.createFamilyHistorySurvey(familyHistory);
        } catch (Exception e) {
            System.err.println("创建家族史记录失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("创建家族史记录失败", e);
        }
    }

    // 根据ID获取家族史记录
    public FamilyHistorySurvey getFamilyHistorySurveyById(int id) {
        try {
            return familyHistorySurveyDao.getFamilyHistorySurveyById(id);
        } catch (Exception e) {
            System.err.println("获取家族史记录失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("获取家族史记录失败", e);
        }
    }

    // 根据参与者ID获取家族史记录列表
    public List<FamilyHistorySurvey> getFamilyHistorySurveysByParticipantId(int participantId) {
        try {
            return familyHistorySurveyDao.getFamilyHistorySurveysByParticipantId(participantId);
        } catch (Exception e) {
            System.err.println("获取参与者家族史记录列表失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("获取参与者家族史记录列表失败", e);
        }
    }

    // 获取所有家族史记录
    public List<FamilyHistorySurvey> getAllFamilyHistorySurveys() {
        try {
            return familyHistorySurveyDao.getAllFamilyHistorySurveys();
        } catch (Exception e) {
            System.err.println("获取所有家族史记录失败: " + e.getMessage());
            // 如果表不存在，返回空列表而不是抛出异常
            if (e.getMessage().contains("doesn't exist") || e.getMessage().contains("not found")) {
                return new ArrayList<>();
            }
            e.printStackTrace();
            throw new RuntimeException("获取所有家族史记录失败", e);
        }
    }

    // 更新家族史记录
    public boolean updateFamilyHistorySurvey(FamilyHistorySurvey familyHistory) {
        try {
            return familyHistorySurveyDao.updateFamilyHistorySurvey(familyHistory);
        } catch (Exception e) {
            System.err.println("更新家族史记录失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("更新家族史记录失败", e);
        }
    }

    // 删除家族史记录
    public boolean deleteFamilyHistorySurvey(int id) {
        try {
            return familyHistorySurveyDao.deleteFamilyHistorySurvey(id);
        } catch (Exception e) {
            System.err.println("删除家族史记录失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("删除家族史记录失败", e);
        }
    }

    // 验证家族史记录数据
    public boolean validateFamilyHistorySurvey(FamilyHistorySurvey familyHistory) {
        if (familyHistory == null) {
            return false;
        }
        
        // 检查必填字段
        if (familyHistory.getParticipantId() <= 0) {
            return false;
        }
        
        if (familyHistory.getRelationshipToParticipant() == null || 
            familyHistory.getRelationshipToParticipant().trim().isEmpty()) {
            return false;
        }
        
        return true;
    }

    // 根据关系搜索家族史记录
    public List<FamilyHistorySurvey> searchByRelationship(String relationship) {
        try {
            List<FamilyHistorySurvey> allRecords = familyHistorySurveyDao.getAllFamilyHistorySurveys();
            return allRecords.stream()
                    .filter(record -> 
                        record.getRelationshipToParticipant() != null && 
                        record.getRelationshipToParticipant().toLowerCase().contains(relationship.toLowerCase())
                    )
                    .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            System.err.println("根据关系搜索家族史记录失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("根据关系搜索家族史记录失败", e);
        }
    }

    // 根据疾病搜索家族史记录
    public List<FamilyHistorySurvey> searchByMedicalCondition(String condition) {
        try {
            List<FamilyHistorySurvey> allRecords = familyHistorySurveyDao.getAllFamilyHistorySurveys();
            return allRecords.stream()
                    .filter(record -> 
                        record.getMedicalCondition() != null && 
                        record.getMedicalCondition().toLowerCase().contains(condition.toLowerCase())
                    )
                    .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            System.err.println("根据疾病搜索家族史记录失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("根据疾病搜索家族史记录失败", e);
        }
    }

    // 复合搜索：按参与者ID、家族成员关系和疾病字段
    public List<FamilyHistorySurvey> searchFamilyHistory(int participantId, String relationship, String diseaseField) {
        try {
            if (relationship != null && !relationship.trim().isEmpty()) {
                // 只按关系查
                return familyHistorySurveyDao.searchByRelationship(participantId, relationship);
            } else if (diseaseField != null && !diseaseField.trim().isEmpty()) {
                // 只按疾病查
                return familyHistorySurveyDao.searchByDisease(participantId, diseaseField);
            } else {
                // 全部
                return familyHistorySurveyDao.getFamilyHistorySurveysByParticipantId(participantId);
            }
        } catch (Exception e) {
            System.err.println("复合搜索家族史记录失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("复合搜索家族史记录失败", e);
        }
    }
}
