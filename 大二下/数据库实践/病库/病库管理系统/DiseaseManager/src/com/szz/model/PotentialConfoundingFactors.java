package com.szz.model;

import java.util.Date;

public class PotentialConfoundingFactors {
    private int id;
    private int surveyParticipantId;
    private String dietaryHabits; // 饮食习惯
    private Integer vitaminDDailyIu; // 维生素D每日剂量 IU
    private Integer vitaminDDurationYears; // 维生素D口服周期（年）
    private String omega3IntakeLevel; // Omega-3脂肪酸摄入量
    private String longTermStressLevelPss10; // 长期压力水平 PSS-10评分
    private String anxietyDepressionPhq9Gad7; // 焦虑或抑郁状态 PHQ-9/GAD-7评分
    private Boolean vaccinationHistoryOnSchedule; // 疫苗接种史：按计划接种 是/否
    private String antibioticUseFrequency; // 抗生素使用频率
    private Boolean earlyLifeBreastfeeding; // 母乳喂养：是/否
    private Integer earlyLifeBreastfeedingMonths; // 母乳喂养具体月数
    private String earlyLifeDeliveryMode; // 分娩情况
    private String earlyLifePetContactAge; // 宠物接触年龄
    private Boolean earlyLifeFarmExposure; // 农场环境暴露：有/无
    private Integer earlyLifeFarmExposureMonths; // 农场环境暴露具体月数
    private Integer diseaseBurdenAbsenteeismDaysPerYear; // 因过敏导致的缺勤或缺课天数/年
    private java.math.BigDecimal diseaseBurdenMedicalCostsPerYear; // 医疗费用支出：年人均
    private Date createdAt;
    private Date updatedAt;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSurveyParticipantId() { return surveyParticipantId; }
    public void setSurveyParticipantId(int surveyParticipantId) { this.surveyParticipantId = surveyParticipantId; }

    public String getDietaryHabits() { return dietaryHabits; }
    public void setDietaryHabits(String dietaryHabits) { this.dietaryHabits = dietaryHabits; }

    public Integer getVitaminDDailyIu() { return vitaminDDailyIu; }
    public void setVitaminDDailyIu(Integer vitaminDDailyIu) { this.vitaminDDailyIu = vitaminDDailyIu; }

    public Integer getVitaminDDurationYears() { return vitaminDDurationYears; }
    public void setVitaminDDurationYears(Integer vitaminDDurationYears) { this.vitaminDDurationYears = vitaminDDurationYears; }

    public String getOmega3IntakeLevel() { return omega3IntakeLevel; }
    public void setOmega3IntakeLevel(String omega3IntakeLevel) { this.omega3IntakeLevel = omega3IntakeLevel; }

    public String getLongTermStressLevelPss10() { return longTermStressLevelPss10; }
    public void setLongTermStressLevelPss10(String longTermStressLevelPss10) { this.longTermStressLevelPss10 = longTermStressLevelPss10; }

    public String getAnxietyDepressionPhq9Gad7() { return anxietyDepressionPhq9Gad7; }
    public void setAnxietyDepressionPhq9Gad7(String anxietyDepressionPhq9Gad7) { this.anxietyDepressionPhq9Gad7 = anxietyDepressionPhq9Gad7; }

    public Boolean getVaccinationHistoryOnSchedule() { return vaccinationHistoryOnSchedule; }
    public void setVaccinationHistoryOnSchedule(Boolean vaccinationHistoryOnSchedule) { this.vaccinationHistoryOnSchedule = vaccinationHistoryOnSchedule; }

    public String getAntibioticUseFrequency() { return antibioticUseFrequency; }
    public void setAntibioticUseFrequency(String antibioticUseFrequency) { this.antibioticUseFrequency = antibioticUseFrequency; }

    public Boolean getEarlyLifeBreastfeeding() { return earlyLifeBreastfeeding; }
    public void setEarlyLifeBreastfeeding(Boolean earlyLifeBreastfeeding) { this.earlyLifeBreastfeeding = earlyLifeBreastfeeding; }

    public Integer getEarlyLifeBreastfeedingMonths() { return earlyLifeBreastfeedingMonths; }
    public void setEarlyLifeBreastfeedingMonths(Integer earlyLifeBreastfeedingMonths) { this.earlyLifeBreastfeedingMonths = earlyLifeBreastfeedingMonths; }

    public String getEarlyLifeDeliveryMode() { return earlyLifeDeliveryMode; }
    public void setEarlyLifeDeliveryMode(String earlyLifeDeliveryMode) { this.earlyLifeDeliveryMode = earlyLifeDeliveryMode; }

    public String getEarlyLifePetContactAge() { return earlyLifePetContactAge; }
    public void setEarlyLifePetContactAge(String earlyLifePetContactAge) { this.earlyLifePetContactAge = earlyLifePetContactAge; }

    public Boolean getEarlyLifeFarmExposure() { return earlyLifeFarmExposure; }
    public void setEarlyLifeFarmExposure(Boolean earlyLifeFarmExposure) { this.earlyLifeFarmExposure = earlyLifeFarmExposure; }

    public Integer getEarlyLifeFarmExposureMonths() { return earlyLifeFarmExposureMonths; }
    public void setEarlyLifeFarmExposureMonths(Integer earlyLifeFarmExposureMonths) { this.earlyLifeFarmExposureMonths = earlyLifeFarmExposureMonths; }

    public Integer getDiseaseBurdenAbsenteeismDaysPerYear() { return diseaseBurdenAbsenteeismDaysPerYear; }
    public void setDiseaseBurdenAbsenteeismDaysPerYear(Integer diseaseBurdenAbsenteeismDaysPerYear) { this.diseaseBurdenAbsenteeismDaysPerYear = diseaseBurdenAbsenteeismDaysPerYear; }

    public java.math.BigDecimal getDiseaseBurdenMedicalCostsPerYear() { return diseaseBurdenMedicalCostsPerYear; }
    public void setDiseaseBurdenMedicalCostsPerYear(java.math.BigDecimal diseaseBurdenMedicalCostsPerYear) { this.diseaseBurdenMedicalCostsPerYear = diseaseBurdenMedicalCostsPerYear; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
