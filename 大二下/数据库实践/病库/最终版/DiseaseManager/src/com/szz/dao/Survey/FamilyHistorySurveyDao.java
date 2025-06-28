package com.szz.dao.Survey;

import com.szz.model.Survey.FamilyHistorySurvey;
import com.szz.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FamilyHistorySurveyDao {
    
    // 创建家族史记录 - 使用真实数据库结构
    public int createFamilyHistorySurvey(FamilyHistorySurvey familyHistory) throws Exception {
        String sql = "INSERT INTO family_history_survey (survey_participant_id, relative_degree, " +
                    "relative_relationship, disease_asthma, disease_eczema, disease_rhinitis, " +
                    "disease_food_allergy, family_env_similarity_smoking) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, familyHistory.getSurveyParticipantId());
            stmt.setString(2, familyHistory.getRelativeDegree());
            stmt.setString(3, familyHistory.getRelativeRelationship());
            stmt.setObject(4, familyHistory.getDiseaseAsthma() != null ? (familyHistory.getDiseaseAsthma() ? 1 : 0) : null);
            stmt.setObject(5, familyHistory.getDiseaseEczema() != null ? (familyHistory.getDiseaseEczema() ? 1 : 0) : null);
            stmt.setObject(6, familyHistory.getDiseaseRhinitis() != null ? (familyHistory.getDiseaseRhinitis() ? 1 : 0) : null);
            stmt.setObject(7, familyHistory.getDiseaseFoodAllergy() != null ? (familyHistory.getDiseaseFoodAllergy() ? 1 : 0) : null);
            stmt.setObject(8, familyHistory.getFamilyEnvSimilaritySmoking() != null ? (familyHistory.getFamilyEnvSimilaritySmoking() ? 1 : 0) : null);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("创建家族史记录失败，没有行被影响。");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("创建家族史记录失败，无法获取ID。");
                }
            }
        }
    }

    // 根据ID获取家族史记录
    public FamilyHistorySurvey getFamilyHistorySurveyById(int id) throws Exception {
        String sql = "SELECT * FROM family_history_survey WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToFamilyHistorySurvey(rs);
            }
            return null;
        }
    }

    // 根据参与者ID获取家族史记录列表
    public List<FamilyHistorySurvey> getFamilyHistorySurveysByParticipantId(int participantId) throws Exception {
        List<FamilyHistorySurvey> familyHistories = new ArrayList<>();
        String sql = "SELECT * FROM family_history_survey WHERE survey_participant_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, participantId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                familyHistories.add(mapResultSetToFamilyHistorySurvey(rs));
            }
        }

        return familyHistories;
    }

    // 获取所有家族史记录
    public List<FamilyHistorySurvey> getAllFamilyHistorySurveys() throws Exception {
        List<FamilyHistorySurvey> familyHistories = new ArrayList<>();
        String sql = "SELECT * FROM family_history_survey ORDER BY created_at DESC";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                familyHistories.add(mapResultSetToFamilyHistorySurvey(rs));
            }
        }

        return familyHistories;
    }

    // 更新家族史记录
    public boolean updateFamilyHistorySurvey(FamilyHistorySurvey familyHistory) throws Exception {
        String sql = "UPDATE family_history_survey SET relative_degree = ?, " +
                    "relative_relationship = ?, disease_asthma = ?, disease_eczema = ?, " +
                    "disease_rhinitis = ?, disease_food_allergy = ?, family_env_similarity_smoking = ? " +
                    "WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, familyHistory.getRelativeDegree());
            stmt.setString(2, familyHistory.getRelativeRelationship());
            stmt.setObject(3, familyHistory.getDiseaseAsthma() != null ? (familyHistory.getDiseaseAsthma() ? 1 : 0) : null);
            stmt.setObject(4, familyHistory.getDiseaseEczema() != null ? (familyHistory.getDiseaseEczema() ? 1 : 0) : null);
            stmt.setObject(5, familyHistory.getDiseaseRhinitis() != null ? (familyHistory.getDiseaseRhinitis() ? 1 : 0) : null);
            stmt.setObject(6, familyHistory.getDiseaseFoodAllergy() != null ? (familyHistory.getDiseaseFoodAllergy() ? 1 : 0) : null);
            stmt.setObject(7, familyHistory.getFamilyEnvSimilaritySmoking() != null ? (familyHistory.getFamilyEnvSimilaritySmoking() ? 1 : 0) : null);
            stmt.setInt(8, familyHistory.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    // 删除家族史记录
    public boolean deleteFamilyHistorySurvey(int id) throws Exception {
        String sql = "DELETE FROM family_history_survey WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // 映射ResultSet到FamilyHistorySurvey对象
    private FamilyHistorySurvey mapResultSetToFamilyHistorySurvey(ResultSet rs) throws SQLException {
        FamilyHistorySurvey familyHistory = new FamilyHistorySurvey();
        familyHistory.setId(rs.getInt("id"));
        familyHistory.setSurveyParticipantId(rs.getInt("survey_participant_id"));
        familyHistory.setRelativeDegree(rs.getString("relative_degree"));
        familyHistory.setRelativeRelationship(rs.getString("relative_relationship"));

        // 疾病信息
        familyHistory.setDiseaseAsthma(getBooleanFromTinyInt(rs, "disease_asthma"));
        familyHistory.setDiseaseEczema(getBooleanFromTinyInt(rs, "disease_eczema"));
        familyHistory.setDiseaseRhinitis(getBooleanFromTinyInt(rs, "disease_rhinitis"));
        familyHistory.setDiseaseFoodAllergy(getBooleanFromTinyInt(rs, "disease_food_allergy"));
        familyHistory.setFamilyEnvSimilaritySmoking(getBooleanFromTinyInt(rs, "family_env_similarity_smoking"));

        familyHistory.setCreatedAt(rs.getTimestamp("created_at"));
        familyHistory.setUpdatedAt(rs.getTimestamp("updated_at"));
        return familyHistory;
    }

    //从TINYINT转换为Boolean
    private Boolean getBooleanFromTinyInt(ResultSet rs, String columnName) throws SQLException {
        Object value = rs.getObject(columnName);
        if (value == null) return null;
        if (value instanceof Integer) {
            return ((Integer) value) == 1;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return null;
    }

    // 按家族成员关系模糊查询
    public List<FamilyHistorySurvey> searchByRelationship(int participantId, String relationship) throws Exception {
        List<FamilyHistorySurvey> familyHistories = new ArrayList<>();
        String sql = "SELECT * FROM family_history_survey WHERE participant_id = ? AND relative_relationship LIKE ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, participantId);
            stmt.setString(2, "%" + relationship + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                familyHistories.add(mapResultSetToFamilyHistorySurvey(rs));
            }
        }
        return familyHistories;
    }

    // 按疾病字段模糊查询
    public List<FamilyHistorySurvey> searchByDisease(int participantId, String diseaseField) throws Exception {
        List<FamilyHistorySurvey> familyHistories = new ArrayList<>();
        // 只允许特定字段
        String[] allowedFields = {"disease_asthma", "disease_eczema", "disease_rhinitis", "disease_food_allergy"};
        boolean valid = false;
        for (String field : allowedFields) {
            if (field.equals(diseaseField)) {
                valid = true;
                break;
            }
        }
        if (!valid) throw new IllegalArgumentException("不支持的疾病字段: " + diseaseField);
        String sql = "SELECT * FROM family_history_survey WHERE participant_id = ? AND " + diseaseField + " = 1";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, participantId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                familyHistories.add(mapResultSetToFamilyHistorySurvey(rs));
            }
        }
        return familyHistories;
    }
}
