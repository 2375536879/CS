package com.szz.model;

import java.util.Date;

public class LabExamination {
    private int id;
    private int visitId;
    private String examinationName;
    private Date examinationDate;
    private String examinationValue;
    private String examinationUnit;
    private String referenceRange;
    private String notes;
    private String allergySkinPrickTestDetails;
    private String allergySerumSpecificIgeLevel;
    private String allergySerumTotalIgeLevel;
    private String allergyPeripheralEosinophilCount;
    private String asthmaChestXrayFindings;
    private String asthmaFenoTestValue;
    private String asthmaBronchodilatorResponseTestResult;
    private String asthmaPefVariabilityRate;
    private String asthmaExerciseChallengeTestResult;
    private String asthmaBronchialChallengeTestResult;
    private String arNasalMucosalChallengeTestResult;
    private String arRegulatoryTCellCount;
    private String arIgg4Level;
    private String arTh1CellCount;
    private String arTh2CellCount;
    private String arEosinophilCationicProteinLevel;
    private Date createdAt;
    private Date updatedAt;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getVisitId() { return visitId; }
    public void setVisitId(int visitId) { this.visitId = visitId; }

    public String getExaminationName() { return examinationName; }
    public void setExaminationName(String examinationName) { this.examinationName = examinationName; }

    public Date getExaminationDate() { return examinationDate; }
    public void setExaminationDate(Date examinationDate) { this.examinationDate = examinationDate; }

    public String getExaminationValue() { return examinationValue; }
    public void setExaminationValue(String examinationValue) { this.examinationValue = examinationValue; }

    public String getExaminationUnit() { return examinationUnit; }
    public void setExaminationUnit(String examinationUnit) { this.examinationUnit = examinationUnit; }

    public String getReferenceRange() { return referenceRange; }
    public void setReferenceRange(String referenceRange) { this.referenceRange = referenceRange; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getAllergySkinPrickTestDetails() { return allergySkinPrickTestDetails; }
    public void setAllergySkinPrickTestDetails(String allergySkinPrickTestDetails) { this.allergySkinPrickTestDetails = allergySkinPrickTestDetails; }

    public String getAllergySerumSpecificIgeLevel() { return allergySerumSpecificIgeLevel; }
    public void setAllergySerumSpecificIgeLevel(String allergySerumSpecificIgeLevel) { this.allergySerumSpecificIgeLevel = allergySerumSpecificIgeLevel; }

    public String getAllergySerumTotalIgeLevel() { return allergySerumTotalIgeLevel; }
    public void setAllergySerumTotalIgeLevel(String allergySerumTotalIgeLevel) { this.allergySerumTotalIgeLevel = allergySerumTotalIgeLevel; }

    public String getAllergyPeripheralEosinophilCount() { return allergyPeripheralEosinophilCount; }
    public void setAllergyPeripheralEosinophilCount(String allergyPeripheralEosinophilCount) { this.allergyPeripheralEosinophilCount = allergyPeripheralEosinophilCount; }

    public String getAsthmaChestXrayFindings() { return asthmaChestXrayFindings; }
    public void setAsthmaChestXrayFindings(String asthmaChestXrayFindings) { this.asthmaChestXrayFindings = asthmaChestXrayFindings; }

    public String getAsthmaFenoTestValue() { return asthmaFenoTestValue; }
    public void setAsthmaFenoTestValue(String asthmaFenoTestValue) { this.asthmaFenoTestValue = asthmaFenoTestValue; }

    public String getAsthmaBronchodilatorResponseTestResult() { return asthmaBronchodilatorResponseTestResult; }
    public void setAsthmaBronchodilatorResponseTestResult(String asthmaBronchodilatorResponseTestResult) { this.asthmaBronchodilatorResponseTestResult = asthmaBronchodilatorResponseTestResult; }

    public String getAsthmaPefVariabilityRate() { return asthmaPefVariabilityRate; }
    public void setAsthmaPefVariabilityRate(String asthmaPefVariabilityRate) { this.asthmaPefVariabilityRate = asthmaPefVariabilityRate; }

    public String getAsthmaExerciseChallengeTestResult() { return asthmaExerciseChallengeTestResult; }
    public void setAsthmaExerciseChallengeTestResult(String asthmaExerciseChallengeTestResult) { this.asthmaExerciseChallengeTestResult = asthmaExerciseChallengeTestResult; }

    public String getAsthmaBronchialChallengeTestResult() { return asthmaBronchialChallengeTestResult; }
    public void setAsthmaBronchialChallengeTestResult(String asthmaBronchialChallengeTestResult) { this.asthmaBronchialChallengeTestResult = asthmaBronchialChallengeTestResult; }

    public String getArNasalMucosalChallengeTestResult() { return arNasalMucosalChallengeTestResult; }
    public void setArNasalMucosalChallengeTestResult(String arNasalMucosalChallengeTestResult) { this.arNasalMucosalChallengeTestResult = arNasalMucosalChallengeTestResult; }

    public String getArRegulatoryTCellCount() { return arRegulatoryTCellCount; }
    public void setArRegulatoryTCellCount(String arRegulatoryTCellCount) { this.arRegulatoryTCellCount = arRegulatoryTCellCount; }

    public String getArIgg4Level() { return arIgg4Level; }
    public void setArIgg4Level(String arIgg4Level) { this.arIgg4Level = arIgg4Level; }

    public String getArTh1CellCount() { return arTh1CellCount; }
    public void setArTh1CellCount(String arTh1CellCount) { this.arTh1CellCount = arTh1CellCount; }

    public String getArTh2CellCount() { return arTh2CellCount; }
    public void setArTh2CellCount(String arTh2CellCount) { this.arTh2CellCount = arTh2CellCount; }

    public String getArEosinophilCationicProteinLevel() { return arEosinophilCationicProteinLevel; }
    public void setArEosinophilCationicProteinLevel(String arEosinophilCationicProteinLevel) { this.arEosinophilCationicProteinLevel = arEosinophilCationicProteinLevel; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}