package com.szz.model;

import java.util.Date;

public class OtherAuxiliaryTreatment {
    private int id;
    private int visitId;
    private String treatmentMethod;
    private Date startDate;
    private Date endDate;
    private String precautions;
    private Boolean isAllergenSpecificImmunotherapy; // 过敏原特异性免疫治疗
    private Boolean isAntiIgeAntibodyTherapy; // 抗免疫球蛋白E抗体治疗
    private Date createdAt;
    private Date updatedAt;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getVisitId() { return visitId; }
    public void setVisitId(int visitId) { this.visitId = visitId; }

    public String getTreatmentMethod() { return treatmentMethod; }
    public void setTreatmentMethod(String treatmentMethod) { this.treatmentMethod = treatmentMethod; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public String getPrecautions() { return precautions; }
    public void setPrecautions(String precautions) { this.precautions = precautions; }

    public Boolean getIsAllergenSpecificImmunotherapy() { return isAllergenSpecificImmunotherapy; }
    public void setIsAllergenSpecificImmunotherapy(Boolean isAllergenSpecificImmunotherapy) { this.isAllergenSpecificImmunotherapy = isAllergenSpecificImmunotherapy; }

    public Boolean getIsAntiIgeAntibodyTherapy() { return isAntiIgeAntibodyTherapy; }
    public void setIsAntiIgeAntibodyTherapy(Boolean isAntiIgeAntibodyTherapy) { this.isAntiIgeAntibodyTherapy = isAntiIgeAntibodyTherapy; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
