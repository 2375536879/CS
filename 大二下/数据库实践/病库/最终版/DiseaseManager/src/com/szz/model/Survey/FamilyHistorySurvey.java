package com.szz.model.Survey;

import java.util.Date;

public class FamilyHistorySurvey {
    private int id;
    private int surveyParticipantId; // FK to survey_participants.id
    private String relativeDegree; // 亲属级别：一级、二级
    private String relativeRelationship; // 关系
    private Boolean diseaseAsthma; // 疾病名称：哮喘
    private Boolean diseaseEczema; // 疾病名称：湿疹
    private Boolean diseaseRhinitis; // 疾病名称：鼻炎
    private Boolean diseaseFoodAllergy; // 疾病名称：食物过敏
    private Boolean familyEnvSimilaritySmoking; // 家族环境居住相似性：共同暴露因素：吸烟有/无
    private Date createdAt;
    private Date updatedAt;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSurveyParticipantId() { return surveyParticipantId; }
    public void setSurveyParticipantId(int surveyParticipantId) { this.surveyParticipantId = surveyParticipantId; }

    public String getRelativeDegree() { return relativeDegree; }
    public void setRelativeDegree(String relativeDegree) { this.relativeDegree = relativeDegree; }

    public String getRelativeRelationship() { return relativeRelationship; }
    public void setRelativeRelationship(String relativeRelationship) { this.relativeRelationship = relativeRelationship; }

    public Boolean getDiseaseAsthma() { return diseaseAsthma; }
    public void setDiseaseAsthma(Boolean diseaseAsthma) { this.diseaseAsthma = diseaseAsthma; }

    public Boolean getDiseaseEczema() { return diseaseEczema; }
    public void setDiseaseEczema(Boolean diseaseEczema) { this.diseaseEczema = diseaseEczema; }

    public Boolean getDiseaseRhinitis() { return diseaseRhinitis; }
    public void setDiseaseRhinitis(Boolean diseaseRhinitis) { this.diseaseRhinitis = diseaseRhinitis; }

    public Boolean getDiseaseFoodAllergy() { return diseaseFoodAllergy; }
    public void setDiseaseFoodAllergy(Boolean diseaseFoodAllergy) { this.diseaseFoodAllergy = diseaseFoodAllergy; }

    public Boolean getFamilyEnvSimilaritySmoking() { return familyEnvSimilaritySmoking; }
    public void setFamilyEnvSimilaritySmoking(Boolean familyEnvSimilaritySmoking) { this.familyEnvSimilaritySmoking = familyEnvSimilaritySmoking; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    // 兼容性方法 - 为了保持与现有代码的兼容性
    public int getParticipantId() { return surveyParticipantId; }
    public void setParticipantId(int participantId) { this.surveyParticipantId = participantId; }

    public String getRelationshipToParticipant() { return relativeRelationship; }
    public void setRelationshipToParticipant(String relationshipToParticipant) { this.relativeRelationship = relationshipToParticipant; }

    public String getFamilyMemberName() { return relativeRelationship; }
    public void setFamilyMemberName(String familyMemberName) { /* 忽略 */ }

    public String getGender() { return null; }
    public void setGender(String gender) { /* 忽略 */ }

    public Integer getAgeAtDiagnosis() { return null; }
    public void setAgeAtDiagnosis(Integer ageAtDiagnosis) { /* 忽略 */ }

    public String getMedicalCondition() {
        // 返回疾病信息的组合
        StringBuilder sb = new StringBuilder();
        if (Boolean.TRUE.equals(diseaseAsthma)) sb.append("哮喘 ");
        if (Boolean.TRUE.equals(diseaseEczema)) sb.append("湿疹 ");
        if (Boolean.TRUE.equals(diseaseRhinitis)) sb.append("鼻炎 ");
        if (Boolean.TRUE.equals(diseaseFoodAllergy)) sb.append("食物过敏 ");
        return sb.toString().trim();
    }
    public void setMedicalCondition(String medicalCondition) { /* 忽略 */ }

    public String getCurrentStatus() { return null; }
    public void setCurrentStatus(String currentStatus) { /* 忽略 */ }

    // 更多兼容性方法
    public String getDiagnosisDate() { return null; }
    public void setDiagnosisDate(String diagnosisDate) { /* 忽略 */ }

    public String getTreatmentHistory() { return null; }
    public void setTreatmentHistory(String treatmentHistory) { /* 忽略 */ }

    public String getMedicationHistory() { return null; }
    public void setMedicationHistory(String medicationHistory) { /* 忽略 */ }

    public String getAllergyHistory() { return null; }
    public void setAllergyHistory(String allergyHistory) { /* 忽略 */ }

    public String getSmokingHistory() { return null; }
    public void setSmokingHistory(String smokingHistory) { /* 忽略 */ }

    public String getDrinkingHistory() { return null; }
    public void setDrinkingHistory(String drinkingHistory) { /* 忽略 */ }

    public String getOccupationalExposure() { return null; }
    public void setOccupationalExposure(String occupationalExposure) { /* 忽略 */ }

    public String getEnvironmentalFactors() { return null; }
    public void setEnvironmentalFactors(String environmentalFactors) { /* 忽略 */ }

    public String getGeneticTestingResults() { return null; }
    public void setGeneticTestingResults(String geneticTestingResults) { /* 忽略 */ }

    public String getNotes() { return null; }
    public void setNotes(String notes) { /* 忽略 */ }
}
