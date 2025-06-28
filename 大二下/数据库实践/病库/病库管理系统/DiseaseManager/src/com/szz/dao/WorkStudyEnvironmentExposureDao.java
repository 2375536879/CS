package com.szz.dao;

import com.szz.model.WorkStudyEnvironmentExposure;
import com.szz.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkStudyEnvironmentExposureDao {
    
    // 获取所有工作学习环境记录
    public List<WorkStudyEnvironmentExposure> getAllWorkStudyEnvironmentExposures() throws Exception {
        String sql = "SELECT * FROM work_study_environment_exposure ORDER BY created_at DESC";
        List<WorkStudyEnvironmentExposure> workStudyEnvironments = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                workStudyEnvironments.add(mapResultSetToWorkStudyEnvironmentExposure(rs));
            }
        }

        return workStudyEnvironments;
    }

    // 映射ResultSet到WorkStudyEnvironmentExposure对象
    private WorkStudyEnvironmentExposure mapResultSetToWorkStudyEnvironmentExposure(ResultSet rs) throws SQLException {
        WorkStudyEnvironmentExposure workStudyEnv = new WorkStudyEnvironmentExposure();
        workStudyEnv.setId(rs.getInt("id"));
        workStudyEnv.setSurveyParticipantId(rs.getInt("survey_participant_id"));
        workStudyEnv.setLocationType(rs.getString("location_type"));
        workStudyEnv.setRoomVentilationStatus(rs.getString("room_ventilation_status"));
        workStudyEnv.setPm25ExposureAnnualAvg(rs.getBigDecimal("pm25_exposure_annual_avg"));
        workStudyEnv.setPollenExposurePeakConcentration(rs.getString("pollen_exposure_peak_concentration"));
        workStudyEnv.setPollenTypesWorkStudy(rs.getString("pollen_types_work_study"));
        workStudyEnv.setFormaldehydeNewDecorValue(rs.getString("formaldehyde_new_decor_value"));
        workStudyEnv.setDustMiteExposureCarpet(getBooleanFromTinyInt(rs, "dust_mite_exposure_carpet"));
        workStudyEnv.setDustMiteExposureFabricFurniture(getBooleanFromTinyInt(rs, "dust_mite_exposure_fabric_furniture"));
        workStudyEnv.setCreatedAt(rs.getTimestamp("created_at"));
        workStudyEnv.setUpdatedAt(rs.getTimestamp("updated_at"));
        return workStudyEnv;
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
