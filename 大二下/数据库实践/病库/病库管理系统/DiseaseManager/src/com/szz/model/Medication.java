package com.szz.model;

import java.util.Date;

public class Medication {
    private int id;
    private int visitId;
    private String drugName;
    private String drugSpecification;
    private String dosagePerAdministration;
    private Integer drugUseDays;
    private String administrationFrequency;
    private String administrationRoute;
    private String administrationSite;
    private Date treatmentStartDate;
    private Date treatmentEndDate;
    private String medicationGuidance;
    private String medicationPrecautions;
    
    // 哮喘相关药物标记
    private Boolean isIcs; // 吸入性糖皮质激素
    private Boolean isLaba; // 长效β2受体激动剂
    private Boolean isLtra; // 白三烯受体拮抗剂
    private Boolean isTheophylline; // 茶碱
    private Boolean isSaba; // 短效β2受体激动剂
    
    // 鼻炎/过敏相关药物标记
    private Boolean isAntihistamine; // 抗组胺药
    private Boolean isCorticosteroidNasalOral; // 糖皮质激素
    private Boolean isMastCellStabilizer; // 肥大细胞膜稳定剂
    private Boolean isAnticholinergic; // 抗胆碱能药
    
    // 湿疹相关药物标记
    private Boolean isTopicalCorticosteroid; // 外用糖皮质激素
    private Boolean isCalcineurinInhibitor; // 钙调神经磷酸酶抑制剂
    
    // 食物过敏相关药物标记
    private Boolean isFoodAllergyCorticosteroid; // 糖皮质激素
    
    private Date createdAt;
    private Date updatedAt;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getVisitId() { return visitId; }
    public void setVisitId(int visitId) { this.visitId = visitId; }

    public String getDrugName() { return drugName; }
    public void setDrugName(String drugName) { this.drugName = drugName; }

    public String getDrugSpecification() { return drugSpecification; }
    public void setDrugSpecification(String drugSpecification) { this.drugSpecification = drugSpecification; }

    public String getDosagePerAdministration() { return dosagePerAdministration; }
    public void setDosagePerAdministration(String dosagePerAdministration) { this.dosagePerAdministration = dosagePerAdministration; }

    public Integer getDrugUseDays() { return drugUseDays; }
    public void setDrugUseDays(Integer drugUseDays) { this.drugUseDays = drugUseDays; }

    public String getAdministrationFrequency() { return administrationFrequency; }
    public void setAdministrationFrequency(String administrationFrequency) { this.administrationFrequency = administrationFrequency; }

    public String getAdministrationRoute() { return administrationRoute; }
    public void setAdministrationRoute(String administrationRoute) { this.administrationRoute = administrationRoute; }

    public String getAdministrationSite() { return administrationSite; }
    public void setAdministrationSite(String administrationSite) { this.administrationSite = administrationSite; }

    public Date getTreatmentStartDate() { return treatmentStartDate; }
    public void setTreatmentStartDate(Date treatmentStartDate) { this.treatmentStartDate = treatmentStartDate; }

    public Date getTreatmentEndDate() { return treatmentEndDate; }
    public void setTreatmentEndDate(Date treatmentEndDate) { this.treatmentEndDate = treatmentEndDate; }

    public String getMedicationGuidance() { return medicationGuidance; }
    public void setMedicationGuidance(String medicationGuidance) { this.medicationGuidance = medicationGuidance; }

    public String getMedicationPrecautions() { return medicationPrecautions; }
    public void setMedicationPrecautions(String medicationPrecautions) { this.medicationPrecautions = medicationPrecautions; }

    public Boolean getIsIcs() { return isIcs; }
    public void setIsIcs(Boolean isIcs) { this.isIcs = isIcs; }

    public Boolean getIsLaba() { return isLaba; }
    public void setIsLaba(Boolean isLaba) { this.isLaba = isLaba; }

    public Boolean getIsLtra() { return isLtra; }
    public void setIsLtra(Boolean isLtra) { this.isLtra = isLtra; }

    public Boolean getIsTheophylline() { return isTheophylline; }
    public void setIsTheophylline(Boolean isTheophylline) { this.isTheophylline = isTheophylline; }

    public Boolean getIsSaba() { return isSaba; }
    public void setIsSaba(Boolean isSaba) { this.isSaba = isSaba; }

    public Boolean getIsAntihistamine() { return isAntihistamine; }
    public void setIsAntihistamine(Boolean isAntihistamine) { this.isAntihistamine = isAntihistamine; }

    public Boolean getIsCorticosteroidNasalOral() { return isCorticosteroidNasalOral; }
    public void setIsCorticosteroidNasalOral(Boolean isCorticosteroidNasalOral) { this.isCorticosteroidNasalOral = isCorticosteroidNasalOral; }

    public Boolean getIsMastCellStabilizer() { return isMastCellStabilizer; }
    public void setIsMastCellStabilizer(Boolean isMastCellStabilizer) { this.isMastCellStabilizer = isMastCellStabilizer; }

    public Boolean getIsAnticholinergic() { return isAnticholinergic; }
    public void setIsAnticholinergic(Boolean isAnticholinergic) { this.isAnticholinergic = isAnticholinergic; }

    public Boolean getIsTopicalCorticosteroid() { return isTopicalCorticosteroid; }
    public void setIsTopicalCorticosteroid(Boolean isTopicalCorticosteroid) { this.isTopicalCorticosteroid = isTopicalCorticosteroid; }

    public Boolean getIsCalcineurinInhibitor() { return isCalcineurinInhibitor; }
    public void setIsCalcineurinInhibitor(Boolean isCalcineurinInhibitor) { this.isCalcineurinInhibitor = isCalcineurinInhibitor; }

    public Boolean getIsFoodAllergyCorticosteroid() { return isFoodAllergyCorticosteroid; }
    public void setIsFoodAllergyCorticosteroid(Boolean isFoodAllergyCorticosteroid) { this.isFoodAllergyCorticosteroid = isFoodAllergyCorticosteroid; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
