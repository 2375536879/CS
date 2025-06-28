package com.szz.dao.Survey;

import com.szz.model.Survey.UrbanRuralEnvironmentMonitoring;
import com.szz.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UrbanRuralEnvironmentMonitoringDao {
    
    // 获取所有城乡环境监测记录
    public List<UrbanRuralEnvironmentMonitoring> getAllUrbanRuralEnvironmentMonitorings() throws Exception {
        String sql = "SELECT * FROM urban_rural_environment_monitoring ORDER BY created_at DESC";
        List<UrbanRuralEnvironmentMonitoring> monitorings = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                monitorings.add(mapResultSetToUrbanRuralEnvironmentMonitoring(rs));
            }
        }

        return monitorings;
    }

    // 映射ResultSet到UrbanRuralEnvironmentMonitoring对象
    private UrbanRuralEnvironmentMonitoring mapResultSetToUrbanRuralEnvironmentMonitoring(ResultSet rs) throws SQLException {
        UrbanRuralEnvironmentMonitoring monitoring = new UrbanRuralEnvironmentMonitoring();
        monitoring.setId(rs.getInt("id"));
        monitoring.setSurveyParticipantId(rs.getInt("survey_participant_id"));
        monitoring.setMonitoringLocationType(rs.getString("monitoring_location_type"));
        monitoring.setCityPm25AnnualAvgSeasonalChange(rs.getString("city_pm25_annual_avg_seasonal_change"));
        monitoring.setCityPollenMainTypesMonthlyDistPeak(rs.getString("city_pollen_main_types_monthly_dist_peak"));
        monitoring.setCityOtherPollutants(rs.getString("city_other_pollutants"));
        monitoring.setCityMonitoringPointLocation(rs.getString("city_monitoring_point_location"));
        monitoring.setRuralPm25StrawBurningAnnualAvg(rs.getString("rural_pm25_straw_burning_annual_avg"));
        monitoring.setRuralPollenCropRelated(rs.getString("rural_pollen_crop_related"));
        monitoring.setRuralBiomassFuelIndoorPollution(getBooleanFromTinyInt(rs, "rural_biomass_fuel_indoor_pollution"));
        monitoring.setDrinkingWaterSourceType(rs.getString("drinking_water_source_type"));
        monitoring.setCreatedAt(rs.getTimestamp("created_at"));
        monitoring.setUpdatedAt(rs.getTimestamp("updated_at"));
        return monitoring;
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
