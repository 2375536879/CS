package com.szz.model.Survey;

import java.util.Date;

public class PastMedicationHistory {
    private int id;
    private int patientId;
    private String drugName;
    private Integer drugUseDays;
    private String reasonForUse;
    private Date approximateStartDate;
    private Date approximateEndDate;
    private Date createdAt;
    private Date updatedAt;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getDrugName() { return drugName; }
    public void setDrugName(String drugName) { this.drugName = drugName; }

    public Integer getDrugUseDays() { return drugUseDays; }
    public void setDrugUseDays(Integer drugUseDays) { this.drugUseDays = drugUseDays; }

    public String getReasonForUse() { return reasonForUse; }
    public void setReasonForUse(String reasonForUse) { this.reasonForUse = reasonForUse; }

    public Date getApproximateStartDate() { return approximateStartDate; }
    public void setApproximateStartDate(Date approximateStartDate) { this.approximateStartDate = approximateStartDate; }

    public Date getApproximateEndDate() { return approximateEndDate; }
    public void setApproximateEndDate(Date approximateEndDate) { this.approximateEndDate = approximateEndDate; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
