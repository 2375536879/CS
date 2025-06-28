package com.szz.dao;

import com.szz.model.PotentialConfoundingFactors;
import com.szz.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PotentialConfoundingFactorsDao {
    
    // 获取所有潜在混杂因素记录
    public List<PotentialConfoundingFactors> getAllPotentialConfoundingFactors() throws Exception {
        String sql = "SELECT * FROM potential_confounding_factors ORDER BY created_at DESC";
        List<PotentialConfoundingFactors> factors = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                factors.add(mapResultSetToPotentialConfoundingFactors(rs));
            }
        }

        return factors;
    }

    // 映射ResultSet到PotentialConfoundingFactors对象
    private PotentialConfoundingFactors mapResultSetToPotentialConfoundingFactors(ResultSet rs) throws SQLException {
        PotentialConfoundingFactors factors = new PotentialConfoundingFactors();
        factors.setId(rs.getInt("id"));
        factors.setSurveyParticipantId(rs.getInt("survey_participant_id"));
        factors.setDietaryHabits(rs.getString("dietary_habits"));
        factors.setVitaminDDailyIu(rs.getObject("vitamin_d_daily_iu", Integer.class));
        factors.setVitaminDDurationYears(rs.getObject("vitamin_d_duration_years", Integer.class));
        factors.setOmega3IntakeLevel(rs.getString("omega3_intake_level"));
        factors.setLongTermStressLevelPss10(rs.getString("long_term_stress_level_pss10"));
        factors.setAnxietyDepressionPhq9Gad7(rs.getString("anxiety_depression_phq9_gad7"));
        factors.setVaccinationHistoryOnSchedule(getBooleanFromTinyInt(rs, "vaccination_history_on_schedule"));
        factors.setAntibioticUseFrequency(rs.getString("antibiotic_use_frequency"));
        factors.setEarlyLifeBreastfeeding(getBooleanFromTinyInt(rs, "early_life_breastfeeding"));
        factors.setEarlyLifeBreastfeedingMonths(rs.getObject("early_life_breastfeeding_months", Integer.class));
        factors.setEarlyLifeDeliveryMode(rs.getString("early_life_delivery_mode"));
        factors.setEarlyLifePetContactAge(rs.getString("early_life_pet_contact_age"));
        factors.setEarlyLifeFarmExposure(getBooleanFromTinyInt(rs, "early_life_farm_exposure"));
        factors.setEarlyLifeFarmExposureMonths(rs.getObject("early_life_farm_exposure_months", Integer.class));
        factors.setDiseaseBurdenAbsenteeismDaysPerYear(rs.getObject("disease_burden_absenteeism_days_per_year", Integer.class));
        factors.setDiseaseBurdenMedicalCostsPerYear(rs.getBigDecimal("disease_burden_medical_costs_per_year"));
        factors.setCreatedAt(rs.getTimestamp("created_at"));
        factors.setUpdatedAt(rs.getTimestamp("updated_at"));
        return factors;
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
