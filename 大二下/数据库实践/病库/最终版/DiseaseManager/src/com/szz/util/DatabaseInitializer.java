package com.szz.util;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {
    
    public static void initializeSurveyTables() {
        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            
            System.out.println("开始创建流调数据库表...");
            
            // 创建流调参与者表
            String createSurveyParticipantsTable = """
                CREATE TABLE IF NOT EXISTS `survey_participants` (
                  `id` INT NOT NULL AUTO_INCREMENT,
                  `patient_id` INT NOT NULL COMMENT 'FK to patients.id',
                  `participant_name` VARCHAR(255) COMMENT '参与者姓名',
                  `gender` ENUM('男', '女', '其他') COMMENT '性别',
                  `date_of_birth` DATE COMMENT '出生日期',
                  `contact_phone` VARCHAR(50) COMMENT '联系电话',
                  `home_address` TEXT COMMENT '家庭住址',
                  `education_level` VARCHAR(100) COMMENT '教育水平',
                  `occupation` VARCHAR(255) COMMENT '职业',
                  `marital_status` VARCHAR(50) COMMENT '婚姻状况',
                  `household_income` VARCHAR(100) COMMENT '家庭收入',
                  `smoking_status` VARCHAR(100) COMMENT '吸烟状况',
                  `drinking_status` VARCHAR(100) COMMENT '饮酒状况',
                  `exercise_frequency` VARCHAR(255) COMMENT '运动频率',
                  `diet_habits` TEXT COMMENT '饮食习惯',
                  `sleep_quality` VARCHAR(100) COMMENT '睡眠质量',
                  `stress_level` VARCHAR(100) COMMENT '压力水平',
                  `chronic_diseases` TEXT COMMENT '慢性疾病',
                  `medication_history` TEXT COMMENT '用药史',
                  `allergy_history` TEXT COMMENT '过敏史',
                  `family_medical_history` TEXT COMMENT '家族病史',
                  `environmental_exposure` TEXT COMMENT '环境暴露',
                  `occupational_exposure` TEXT COMMENT '职业暴露',
                  `notes` TEXT COMMENT '备注',
                  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                  PRIMARY KEY (`id`),
                  INDEX `idx_patient_id` (`patient_id`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='流调参与者'
                """;
            
            stmt.executeUpdate(createSurveyParticipantsTable);
            System.out.println("✓ 流调参与者表创建成功");
            
            // 创建家族史调查表
            String createFamilyHistoryTable = """
                CREATE TABLE IF NOT EXISTS `family_history_survey` (
                  `id` INT NOT NULL AUTO_INCREMENT,
                  `participant_id` INT NOT NULL COMMENT 'FK to survey_participants.id',
                  `relationship_to_participant` VARCHAR(100) COMMENT '与参与者关系',
                  `family_member_name` VARCHAR(255) COMMENT '家族成员姓名',
                  `gender` ENUM('男', '女', '其他') COMMENT '性别',
                  `age_at_diagnosis` INT COMMENT '诊断时年龄',
                  `medical_condition` TEXT COMMENT '疾病状况',
                  `diagnosis_date` VARCHAR(100) COMMENT '诊断日期',
                  `current_status` VARCHAR(100) COMMENT '当前状态',
                  `treatment_history` TEXT COMMENT '治疗史',
                  `medication_history` TEXT COMMENT '用药史',
                  `allergy_history` TEXT COMMENT '过敏史',
                  `smoking_history` TEXT COMMENT '吸烟史',
                  `drinking_history` TEXT COMMENT '饮酒史',
                  `occupational_exposure` TEXT COMMENT '职业暴露',
                  `environmental_factors` TEXT COMMENT '环境因素',
                  `genetic_testing_results` TEXT COMMENT '基因检测结果',
                  `notes` TEXT COMMENT '备注',
                  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                  PRIMARY KEY (`id`),
                  INDEX `idx_participant_id` (`participant_id`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='家族史调查'
                """;
            
            stmt.executeUpdate(createFamilyHistoryTable);
            System.out.println("✓ 家族史调查表创建成功");
            
            // 创建家庭环境暴露表
            String createHomeEnvironmentTable = """
                CREATE TABLE IF NOT EXISTS `home_environment_exposure` (
                  `id` INT NOT NULL AUTO_INCREMENT,
                  `participant_id` INT NOT NULL COMMENT 'FK to survey_participants.id',
                  `housing_type` VARCHAR(100) COMMENT '住房类型',
                  `building_age` INT COMMENT '建筑年龄',
                  `heating_type` VARCHAR(100) COMMENT '供暖类型',
                  `cooking_fuel_type` VARCHAR(100) COMMENT '烹饪燃料类型',
                  `has_air_conditioning` TINYINT(1) COMMENT '有空调',
                  `has_ventilation_system` TINYINT(1) COMMENT '有通风系统',
                  `humidity_level` VARCHAR(100) COMMENT '湿度水平',
                  `temperature_control` VARCHAR(100) COMMENT '温度控制',
                  `has_pets` TINYINT(1) COMMENT '有宠物',
                  `pet_types` TEXT COMMENT '宠物类型',
                  `has_indoor_plants` TINYINT(1) COMMENT '有室内植物',
                  `plant_types` TEXT COMMENT '植物类型',
                  `has_smoking_indoors` TINYINT(1) COMMENT '室内吸烟',
                  `smoking_frequency` VARCHAR(100) COMMENT '吸烟频率',
                  `has_chemical_exposure` TINYINT(1) COMMENT '化学物质暴露',
                  `chemical_types` TEXT COMMENT '化学物质类型',
                  `has_mold_or_dampness` TINYINT(1) COMMENT '霉菌或潮湿',
                  `mold_location` TEXT COMMENT '霉菌位置',
                  `has_dust_mites` TINYINT(1) COMMENT '尘螨',
                  `dust_mite_location` TEXT COMMENT '尘螨位置',
                  `has_carpets` TINYINT(1) COMMENT '有地毯',
                  `carpet_type` VARCHAR(100) COMMENT '地毯类型',
                  `cleaning_frequency` VARCHAR(100) COMMENT '清洁频率',
                  `cleaning_products` TEXT COMMENT '清洁产品',
                  `has_air_purifier` TINYINT(1) COMMENT '有空气净化器',
                  `air_purifier_type` VARCHAR(100) COMMENT '空气净化器类型',
                  `water_source` VARCHAR(100) COMMENT '水源',
                  `water_quality` VARCHAR(100) COMMENT '水质',
                  `noise_level` VARCHAR(100) COMMENT '噪音水平',
                  `lighting_conditions` VARCHAR(100) COMMENT '照明条件',
                  `nearby_pollution_sources` TEXT COMMENT '附近污染源',
                  `neighborhood_environment` TEXT COMMENT '社区环境',
                  `notes` TEXT COMMENT '备注',
                  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                  PRIMARY KEY (`id`),
                  INDEX `idx_participant_id` (`participant_id`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='家庭环境暴露'
                """;
            
            stmt.executeUpdate(createHomeEnvironmentTable);
            System.out.println("✓ 家庭环境暴露表创建成功");
            
            System.out.println("流调数据库表创建完成！");
            
        } catch (Exception e) {
            System.err.println("创建流调数据库表失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== 流调数据库初始化工具 ===");
        initializeSurveyTables();
    }
}
