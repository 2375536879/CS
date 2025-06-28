package com.szz.dao;

import com.szz.model.SurveyParticipant;
import com.szz.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SurveyParticipantDao {
    
    // 创建流调参与者 - 使用真实数据库表结构
    public int createSurveyParticipant(SurveyParticipant participant) throws Exception {
        String sql = "INSERT INTO survey_participants (patient_id, survey_identifier, survey_date, name, gender, " +
                    "age_at_survey, residence_type, residence_duration_years, home_address_survey, " +
                    "height_cm_survey, weight_kg_survey) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setObject(1, participant.getPatientId()); // 可为null
            stmt.setString(2, participant.getSurveyIdentifier());
            stmt.setDate(3, participant.getSurveyDate() != null ? new java.sql.Date(participant.getSurveyDate().getTime()) : null);
            stmt.setString(4, participant.getName());
            stmt.setString(5, participant.getGender());
            stmt.setObject(6, participant.getAgeAtSurvey());
            stmt.setString(7, participant.getResidenceType());
            stmt.setObject(8, participant.getResidenceDurationYears());
            stmt.setString(9, participant.getHomeAddressSurvey());
            stmt.setBigDecimal(10, participant.getHeightCmSurvey());
            stmt.setBigDecimal(11, participant.getWeightKgSurvey());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("创建流调参与者失败，没有行被影响。");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("创建流调参与者失败，无法获取ID。");
                }
            }
        }
    }

    // 根据ID获取流调参与者
    public SurveyParticipant getSurveyParticipantById(int id) throws Exception {
        String sql = "SELECT * FROM survey_participants WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToSurveyParticipant(rs);
            }
            return null;
        }
    }

    // 根据患者ID获取流调参与者列表
    public List<SurveyParticipant> getSurveyParticipantsByPatientId(int patientId) throws Exception {
        List<SurveyParticipant> participants = new ArrayList<>();
        String sql = "SELECT * FROM survey_participants WHERE patient_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                participants.add(mapResultSetToSurveyParticipant(rs));
            }
        }

        return participants;
    }

    // 获取所有流调参与者
    public List<SurveyParticipant> getAllSurveyParticipants() throws Exception {
        List<SurveyParticipant> participants = new ArrayList<>();
        String sql = "SELECT * FROM survey_participants ORDER BY created_at DESC";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                participants.add(mapResultSetToSurveyParticipant(rs));
            }
        }

        return participants;
    }

    // 更新流调参与者 - 使用真实数据库表结构
    public boolean updateSurveyParticipant(SurveyParticipant participant) throws Exception {
        String sql = "UPDATE survey_participants SET patient_id = ?, survey_identifier = ?, survey_date = ?, " +
                    "name = ?, gender = ?, age_at_survey = ?, residence_type = ?, residence_duration_years = ?, " +
                    "home_address_survey = ?, height_cm_survey = ?, weight_kg_survey = ? WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, participant.getPatientId());
            stmt.setString(2, participant.getSurveyIdentifier());
            stmt.setDate(3, participant.getSurveyDate() != null ? new java.sql.Date(participant.getSurveyDate().getTime()) : null);
            stmt.setString(4, participant.getName());
            stmt.setString(5, participant.getGender());
            stmt.setObject(6, participant.getAgeAtSurvey());
            stmt.setString(7, participant.getResidenceType());
            stmt.setObject(8, participant.getResidenceDurationYears());
            stmt.setString(9, participant.getHomeAddressSurvey());
            stmt.setBigDecimal(10, participant.getHeightCmSurvey());
            stmt.setBigDecimal(11, participant.getWeightKgSurvey());
            stmt.setInt(12, participant.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    // 删除流调参与者
    public boolean deleteSurveyParticipant(int id) throws Exception {
        String sql = "DELETE FROM survey_participants WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // 映射ResultSet到SurveyParticipant对象 - 使用真实数据库结构
    private SurveyParticipant mapResultSetToSurveyParticipant(ResultSet rs) throws SQLException {
        SurveyParticipant participant = new SurveyParticipant();
        participant.setId(rs.getInt("id"));

        // 基本信息
        Object patientId = rs.getObject("patient_id");
        participant.setPatientId(patientId != null ? (Integer) patientId : null);

        participant.setSurveyIdentifier(rs.getString("survey_identifier"));
        participant.setSurveyDate(rs.getDate("survey_date"));
        participant.setName(rs.getString("name"));
        participant.setGender(rs.getString("gender"));

        Object ageAtSurvey = rs.getObject("age_at_survey");
        participant.setAgeAtSurvey(ageAtSurvey != null ? (Integer) ageAtSurvey : null);

        participant.setResidenceType(rs.getString("residence_type"));

        Object residenceDuration = rs.getObject("residence_duration_years");
        participant.setResidenceDurationYears(residenceDuration != null ? (Integer) residenceDuration : null);

        participant.setHomeAddressSurvey(rs.getString("home_address_survey"));
        participant.setHeightCmSurvey(rs.getBigDecimal("height_cm_survey"));
        participant.setWeightKgSurvey(rs.getBigDecimal("weight_kg_survey"));

        // 既往过敏史
        participant.setHasPreviousAllergyHistory(getBooleanFromTinyInt(rs, "has_previous_allergy_history"));
        participant.setPreviousAllergicRhinitis(getBooleanFromTinyInt(rs, "previous_allergic_rhinitis"));
        participant.setPreviousBronchialAsthma(getBooleanFromTinyInt(rs, "previous_bronchial_asthma"));
        participant.setPreviousAtopicDermatitis(getBooleanFromTinyInt(rs, "previous_atopic_dermatitis"));
        participant.setPreviousAllergicConjunctivitis(getBooleanFromTinyInt(rs, "previous_allergic_conjunctivitis"));
        participant.setPreviousUrticaria(getBooleanFromTinyInt(rs, "previous_urticaria"));

        // 本次调查诊断
        participant.setCurrentSurveyDiagAr(getBooleanFromTinyInt(rs, "current_survey_diag_ar"));
        participant.setCurrentSurveyDiagBa(getBooleanFromTinyInt(rs, "current_survey_diag_ba"));
        participant.setCurrentSurveyDiagAd(getBooleanFromTinyInt(rs, "current_survey_diag_ad"));
        participant.setCurrentSurveyDiagAc(getBooleanFromTinyInt(rs, "current_survey_diag_ac"));
        participant.setCurrentSurveyDiagUrticaria(getBooleanFromTinyInt(rs, "current_survey_diag_urticaria"));

        participant.setCreatedAt(rs.getTimestamp("created_at"));
        participant.setUpdatedAt(rs.getTimestamp("updated_at"));
        return participant;
    }

    // 辅助方法：从TINYINT转换为Boolean
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
}
