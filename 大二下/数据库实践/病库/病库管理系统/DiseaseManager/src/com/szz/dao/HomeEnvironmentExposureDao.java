package com.szz.dao;

import com.szz.model.HomeEnvironmentExposure;
import com.szz.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomeEnvironmentExposureDao {
    
    // 创建家庭环境记录 - 使用真实数据库结构
    public int createHomeEnvironmentExposure(HomeEnvironmentExposure homeEnvironment) throws Exception {
        String sql = "INSERT INTO home_environment_exposure (survey_participant_id, housing_type, " +
                    "heating_type, cooking_fuel_type, has_carpet, pet_cat, pet_dog, pet_bird, " +
                    "pet_other, cohabiting_smoker, uses_air_purifier, cleaning_frequency) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, homeEnvironment.getParticipantId());
            stmt.setString(2, homeEnvironment.getHousingType());
            stmt.setString(3, homeEnvironment.getHeatingType());
            stmt.setString(4, homeEnvironment.getCookingFuelType());
            stmt.setObject(5, homeEnvironment.getHasCarpets() != null ? (homeEnvironment.getHasCarpets() ? 1 : 0) : null);

            // 解析宠物类型
            String petTypes = homeEnvironment.getPetTypes();
            boolean hasCat = petTypes != null && petTypes.contains("猫");
            boolean hasDog = petTypes != null && petTypes.contains("狗");
            boolean hasBird = petTypes != null && petTypes.contains("鸟");
            String otherPets = "";
            if (petTypes != null) {
                otherPets = petTypes.replaceAll("猫|狗|鸟", "").trim();
            }

            stmt.setObject(6, hasCat ? 1 : 0);
            stmt.setObject(7, hasDog ? 1 : 0);
            stmt.setObject(8, hasBird ? 1 : 0);
            stmt.setString(9, otherPets.isEmpty() ? null : otherPets);
            stmt.setObject(10, homeEnvironment.getHasSmokingIndoors() != null ? (homeEnvironment.getHasSmokingIndoors() ? 1 : 0) : null);
            stmt.setObject(11, homeEnvironment.getHasAirPurifier() != null ? (homeEnvironment.getHasAirPurifier() ? 1 : 0) : null);
            stmt.setString(12, homeEnvironment.getCleaningFrequency());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("创建家庭环境记录失败，没有行被影响。");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("创建家庭环境记录失败，无法获取ID。");
                }
            }
        }
    }

    // 根据ID获取家庭环境记录
    public HomeEnvironmentExposure getHomeEnvironmentExposureById(int id) throws Exception {
        String sql = "SELECT * FROM home_environment_exposure WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToHomeEnvironmentExposure(rs);
            }
            return null;
        }
    }

    // 根据参与者ID获取家庭环境记录列表
    public List<HomeEnvironmentExposure> getHomeEnvironmentExposuresByParticipantId(int participantId) throws Exception {
        List<HomeEnvironmentExposure> homeEnvironments = new ArrayList<>();
        String sql = "SELECT * FROM home_environment_exposure WHERE survey_participant_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, participantId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                homeEnvironments.add(mapResultSetToHomeEnvironmentExposure(rs));
            }
        }

        return homeEnvironments;
    }

    // 获取所有家庭环境记录
    public List<HomeEnvironmentExposure> getAllHomeEnvironmentExposures() throws Exception {
        List<HomeEnvironmentExposure> homeEnvironments = new ArrayList<>();
        String sql = "SELECT * FROM home_environment_exposure ORDER BY created_at DESC";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                homeEnvironments.add(mapResultSetToHomeEnvironmentExposure(rs));
            }
        }

        return homeEnvironments;
    }

    // 更新家庭环境记录 - 使用真实数据库结构
    public boolean updateHomeEnvironmentExposure(HomeEnvironmentExposure homeEnvironment) throws Exception {
        String sql = "UPDATE home_environment_exposure SET housing_type = ?, " +
                    "heating_type = ?, cooking_fuel_type = ?, has_carpet = ?, pet_cat = ?, pet_dog = ?, " +
                    "pet_bird = ?, pet_other = ?, cohabiting_smoker = ?, uses_air_purifier = ?, " +
                    "cleaning_frequency = ? WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, homeEnvironment.getHousingType());
            stmt.setString(2, homeEnvironment.getHeatingType());
            stmt.setString(3, homeEnvironment.getCookingFuelType());
            stmt.setObject(4, homeEnvironment.getHasCarpets() != null ? (homeEnvironment.getHasCarpets() ? 1 : 0) : null);

            // 解析宠物类型
            String petTypes = homeEnvironment.getPetTypes();
            boolean hasCat = petTypes != null && petTypes.contains("猫");
            boolean hasDog = petTypes != null && petTypes.contains("狗");
            boolean hasBird = petTypes != null && petTypes.contains("鸟");
            String otherPets = "";
            if (petTypes != null) {
                otherPets = petTypes.replaceAll("猫|狗|鸟", "").trim();
            }

            stmt.setObject(5, hasCat ? 1 : 0);
            stmt.setObject(6, hasDog ? 1 : 0);
            stmt.setObject(7, hasBird ? 1 : 0);
            stmt.setString(8, otherPets.isEmpty() ? null : otherPets);
            stmt.setObject(9, homeEnvironment.getHasSmokingIndoors() != null ? (homeEnvironment.getHasSmokingIndoors() ? 1 : 0) : null);
            stmt.setObject(10, homeEnvironment.getHasAirPurifier() != null ? (homeEnvironment.getHasAirPurifier() ? 1 : 0) : null);
            stmt.setString(11, homeEnvironment.getCleaningFrequency());
            stmt.setInt(12, homeEnvironment.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    // 删除家庭环境记录
    public boolean deleteHomeEnvironmentExposure(int id) throws Exception {
        String sql = "DELETE FROM home_environment_exposure WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // 映射ResultSet到HomeEnvironmentExposure对象 - 使用真实数据库结构
    private HomeEnvironmentExposure mapResultSetToHomeEnvironmentExposure(ResultSet rs) throws SQLException {
        HomeEnvironmentExposure homeEnvironment = new HomeEnvironmentExposure();
        homeEnvironment.setId(rs.getInt("id"));
        homeEnvironment.setParticipantId(rs.getInt("survey_participant_id"));

        // 基本住房信息
        homeEnvironment.setHousingType(rs.getString("housing_type"));
        homeEnvironment.setBuildingAge(null); // building_age字段不存在，设为null
        homeEnvironment.setHeatingType(rs.getString("heating_type"));
        homeEnvironment.setCookingFuelType(rs.getString("cooking_fuel_type"));

        // 空调和通风 - 根据真实字段映射
        homeEnvironment.setHasAirConditioning(true); // 假设有空调使用相关字段
        homeEnvironment.setHasVentilationSystem(true); // 假设有通风相关字段
        homeEnvironment.setHumidityLevel(null);
        homeEnvironment.setTemperatureControl(rs.getString("ac_temp_setting"));

        // 宠物信息 - 根据真实字段组合
        Boolean hasPets = getBooleanFromTinyInt(rs, "pet_cat") ||
                         getBooleanFromTinyInt(rs, "pet_dog") ||
                         getBooleanFromTinyInt(rs, "pet_bird");
        homeEnvironment.setHasPets(hasPets);

        String petTypes = "";
        if (getBooleanFromTinyInt(rs, "pet_cat")) petTypes += "猫 ";
        if (getBooleanFromTinyInt(rs, "pet_dog")) petTypes += "狗 ";
        if (getBooleanFromTinyInt(rs, "pet_bird")) petTypes += "鸟 ";
        String otherPet = rs.getString("pet_other");
        if (otherPet != null && !otherPet.trim().isEmpty()) {
            petTypes += otherPet;
        }
        homeEnvironment.setPetTypes(petTypes.trim());

        homeEnvironment.setHasIndoorPlants(null);
        homeEnvironment.setPlantTypes(null);
        homeEnvironment.setHasSmokingIndoors(getBooleanFromTinyInt(rs, "cohabiting_smoker"));
        homeEnvironment.setSmokingFrequency(rs.getString("smoking_alcohol_e_cigarette_use"));
        homeEnvironment.setHasChemicalExposure(null); // 可以根据需要设置
        homeEnvironment.setChemicalTypes(null);
        homeEnvironment.setHasMoldOrDampness(null);
        homeEnvironment.setMoldLocation(null);
        homeEnvironment.setHasDustMites(null);
        homeEnvironment.setDustMiteLocation(rs.getString("home_dust_mite_concentration")); // 使用真实字段
        homeEnvironment.setHasCarpets(getBooleanFromTinyInt(rs, "has_carpet")); // 使用真实字段
        homeEnvironment.setCarpetType(null);
        homeEnvironment.setCleaningFrequency(rs.getString("cleaning_frequency"));
        homeEnvironment.setCleaningProducts(null);
        homeEnvironment.setHasAirPurifier(getBooleanFromTinyInt(rs, "uses_air_purifier")); // 使用真实字段
        homeEnvironment.setAirPurifierType(null);
        homeEnvironment.setWaterSource(null);
        homeEnvironment.setWaterQuality(null);
        homeEnvironment.setNoiseLevel(null);
        homeEnvironment.setLightingConditions(null);
        homeEnvironment.setNearbyPollutionSources(null);
        homeEnvironment.setNeighborhoodEnvironment(null);
        homeEnvironment.setNotes(null);
        homeEnvironment.setCreatedAt(rs.getTimestamp("created_at"));
        homeEnvironment.setUpdatedAt(rs.getTimestamp("updated_at"));
        return homeEnvironment;
    }

    // 辅助方法：从TINYINT转换为Boolean
    private Boolean getBooleanFromTinyInt(ResultSet rs, String columnName) throws SQLException {
        Object value = rs.getObject(columnName);
        if (value == null) return false;
        if (value instanceof Integer) {
            return ((Integer) value) == 1;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return false;
    }
}
