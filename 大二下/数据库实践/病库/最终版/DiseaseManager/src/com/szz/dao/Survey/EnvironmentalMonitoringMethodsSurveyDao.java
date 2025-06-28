package com.szz.dao.Survey;

import com.szz.model.Survey.EnvironmentalMonitoringMethodsSurvey;
import com.szz.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnvironmentalMonitoringMethodsSurveyDao {
    
    // 获取所有环境监测方法记录
    public List<EnvironmentalMonitoringMethodsSurvey> getAllEnvironmentalMonitoringMethodsSurveys() throws Exception {
        String sql = "SELECT * FROM environmental_monitoring_methods_survey ORDER BY created_at DESC";
        List<EnvironmentalMonitoringMethodsSurvey> methods = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                methods.add(mapResultSetToEnvironmentalMonitoringMethodsSurvey(rs));
            }
        }

        return methods;
    }

    // 映射ResultSet到EnvironmentalMonitoringMethodsSurvey对象
    private EnvironmentalMonitoringMethodsSurvey mapResultSetToEnvironmentalMonitoringMethodsSurvey(ResultSet rs) throws SQLException {
        EnvironmentalMonitoringMethodsSurvey methods = new EnvironmentalMonitoringMethodsSurvey();
        methods.setId(rs.getInt("id"));
        methods.setSurveyParticipantId(rs.getInt("survey_participant_id"));
        methods.setPm25DetectorModel(rs.getString("pm25_detector_model"));
        methods.setPollenSamplingMethod(rs.getString("pollen_sampling_method"));
        methods.setDustMiteDetectionMethod(rs.getString("dust_mite_detection_method"));
        methods.setEnvironmentalSensorTechnologyNotes(rs.getString("environmental_sensor_technology_notes"));
        methods.setCreatedAt(rs.getTimestamp("created_at"));
        methods.setUpdatedAt(rs.getTimestamp("updated_at"));
        return methods;
    }
}
