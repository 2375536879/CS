package com.szz.model.Survey;

import java.math.BigDecimal;
import java.util.Date;

public class SurveyParticipant {
    private int id;
    private Integer patientId; // 可为null
    private String surveyIdentifier; // 调查编号
    private Date surveyDate; // 调查时间
    private String name; // 姓名
    private String gender; // 性别
    private Integer ageAtSurvey; // 年龄
    private String residenceType; // 居住地类型
    private Integer residenceDurationYears; // 居住时长（年）
    private String homeAddressSurvey; // 家庭住址
    private BigDecimal heightCmSurvey; // 身高
    private BigDecimal weightKgSurvey; // 体重

    // 既往过敏史
    private Boolean hasPreviousAllergyHistory; // 既往过敏史：有/无
    private Boolean previousAllergicRhinitis; // 既往:过敏性鼻炎
    private Boolean previousBronchialAsthma; // 既往:支气管哮喘
    private Boolean previousAtopicDermatitis; // 既往:特应性皮炎
    private Boolean previousAllergicConjunctivitis; // 既往:过敏性结膜炎
    private Boolean previousUrticaria; // 既往:荨麻疹

    // 本次调查诊断
    private Boolean currentSurveyDiagAr; // 本次调查诊断:过敏性鼻炎
    private Boolean currentSurveyDiagBa; // 本次调查诊断:支气管哮喘
    private Boolean currentSurveyDiagAd; // 本次调查诊断:特应性皮炎
    private Boolean currentSurveyDiagAc; // 本次调查诊断:过敏性结膜炎
    private Boolean currentSurveyDiagUrticaria; // 本次调查诊断:荨麻疹

    private Date createdAt;
    private Date updatedAt;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getPatientId() { return patientId; }
    public void setPatientId(Integer patientId) { this.patientId = patientId; }

    public String getSurveyIdentifier() { return surveyIdentifier; }
    public void setSurveyIdentifier(String surveyIdentifier) { this.surveyIdentifier = surveyIdentifier; }

    public Date getSurveyDate() { return surveyDate; }
    public void setSurveyDate(Date surveyDate) { this.surveyDate = surveyDate; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Integer getAgeAtSurvey() { return ageAtSurvey; }
    public void setAgeAtSurvey(Integer ageAtSurvey) { this.ageAtSurvey = ageAtSurvey; }

    public String getResidenceType() { return residenceType; }
    public void setResidenceType(String residenceType) { this.residenceType = residenceType; }

    public Integer getResidenceDurationYears() { return residenceDurationYears; }
    public void setResidenceDurationYears(Integer residenceDurationYears) { this.residenceDurationYears = residenceDurationYears; }

    public String getHomeAddressSurvey() { return homeAddressSurvey; }
    public void setHomeAddressSurvey(String homeAddressSurvey) { this.homeAddressSurvey = homeAddressSurvey; }

    public BigDecimal getHeightCmSurvey() { return heightCmSurvey; }
    public void setHeightCmSurvey(BigDecimal heightCmSurvey) { this.heightCmSurvey = heightCmSurvey; }

    public BigDecimal getWeightKgSurvey() { return weightKgSurvey; }
    public void setWeightKgSurvey(BigDecimal weightKgSurvey) { this.weightKgSurvey = weightKgSurvey; }

    public Boolean getHasPreviousAllergyHistory() { return hasPreviousAllergyHistory; }
    public void setHasPreviousAllergyHistory(Boolean hasPreviousAllergyHistory) { this.hasPreviousAllergyHistory = hasPreviousAllergyHistory; }

    public Boolean getPreviousAllergicRhinitis() { return previousAllergicRhinitis; }
    public void setPreviousAllergicRhinitis(Boolean previousAllergicRhinitis) { this.previousAllergicRhinitis = previousAllergicRhinitis; }

    public Boolean getPreviousBronchialAsthma() { return previousBronchialAsthma; }
    public void setPreviousBronchialAsthma(Boolean previousBronchialAsthma) { this.previousBronchialAsthma = previousBronchialAsthma; }

    public Boolean getPreviousAtopicDermatitis() { return previousAtopicDermatitis; }
    public void setPreviousAtopicDermatitis(Boolean previousAtopicDermatitis) { this.previousAtopicDermatitis = previousAtopicDermatitis; }

    public Boolean getPreviousAllergicConjunctivitis() { return previousAllergicConjunctivitis; }
    public void setPreviousAllergicConjunctivitis(Boolean previousAllergicConjunctivitis) { this.previousAllergicConjunctivitis = previousAllergicConjunctivitis; }

    public Boolean getPreviousUrticaria() { return previousUrticaria; }
    public void setPreviousUrticaria(Boolean previousUrticaria) { this.previousUrticaria = previousUrticaria; }

    public Boolean getCurrentSurveyDiagAr() { return currentSurveyDiagAr; }
    public void setCurrentSurveyDiagAr(Boolean currentSurveyDiagAr) { this.currentSurveyDiagAr = currentSurveyDiagAr; }

    public Boolean getCurrentSurveyDiagBa() { return currentSurveyDiagBa; }
    public void setCurrentSurveyDiagBa(Boolean currentSurveyDiagBa) { this.currentSurveyDiagBa = currentSurveyDiagBa; }

    public Boolean getCurrentSurveyDiagAd() { return currentSurveyDiagAd; }
    public void setCurrentSurveyDiagAd(Boolean currentSurveyDiagAd) { this.currentSurveyDiagAd = currentSurveyDiagAd; }

    public Boolean getCurrentSurveyDiagAc() { return currentSurveyDiagAc; }
    public void setCurrentSurveyDiagAc(Boolean currentSurveyDiagAc) { this.currentSurveyDiagAc = currentSurveyDiagAc; }

    public Boolean getCurrentSurveyDiagUrticaria() { return currentSurveyDiagUrticaria; }
    public void setCurrentSurveyDiagUrticaria(Boolean currentSurveyDiagUrticaria) { this.currentSurveyDiagUrticaria = currentSurveyDiagUrticaria; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    // 兼容性方法 - 为了保持与现有代码的兼容性
    public String getParticipantName() { return name; }
    public void setParticipantName(String name) { this.name = name; }

    public String getHomeAddress() { return homeAddressSurvey; }
    public void setHomeAddress(String homeAddress) { this.homeAddressSurvey = homeAddress; }

    // 为了兼容性，添加一些旧字段的getter/setter方法，返回null或空值
    public Date getDateOfBirth() { return null; }
    public void setDateOfBirth(Date dateOfBirth) { /* 忽略，使用ageAtSurvey */ }

    public String getContactPhone() { return null; }
    public void setContactPhone(String contactPhone) { /* 忽略 */ }

    public String getEducationLevel() { return null; }
    public void setEducationLevel(String educationLevel) { /* 忽略 */ }

    public String getOccupation() { return null; }
    public void setOccupation(String occupation) { /* 忽略 */ }

    public String getMaritalStatus() { return null; }
    public void setMaritalStatus(String maritalStatus) { /* 忽略 */ }

    public String getHouseholdIncome() { return null; }
    public void setHouseholdIncome(String householdIncome) { /* 忽略 */ }

    public String getSmokingStatus() { return null; }
    public void setSmokingStatus(String smokingStatus) { /* 忽略 */ }

    public String getDrinkingStatus() { return null; }
    public void setDrinkingStatus(String drinkingStatus) { /* 忽略 */ }

    public String getExerciseFrequency() { return null; }
    public void setExerciseFrequency(String exerciseFrequency) { /* 忽略 */ }

    public String getDietHabits() { return null; }
    public void setDietHabits(String dietHabits) { /* 忽略 */ }

    public String getSleepQuality() { return null; }
    public void setSleepQuality(String sleepQuality) { /* 忽略 */ }

    public String getStressLevel() { return null; }
    public void setStressLevel(String stressLevel) { /* 忽略 */ }

    public String getChronicDiseases() { return null; }
    public void setChronicDiseases(String chronicDiseases) { /* 忽略 */ }

    public String getMedicationHistory() { return null; }
    public void setMedicationHistory(String medicationHistory) { /* 忽略 */ }

    public String getAllergyHistory() { return null; }
    public void setAllergyHistory(String allergyHistory) { /* 忽略 */ }

    public String getFamilyMedicalHistory() { return null; }
    public void setFamilyMedicalHistory(String familyMedicalHistory) { /* 忽略 */ }

    public String getEnvironmentalExposure() { return null; }
    public void setEnvironmentalExposure(String environmentalExposure) { /* 忽略 */ }

    public String getOccupationalExposure() { return null; }
    public void setOccupationalExposure(String occupationalExposure) { /* 忽略 */ }

    public String getNotes() { return null; }
    public void setNotes(String notes) { /* 忽略 */ }
}
