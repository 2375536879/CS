package com.szz.model;

import java.util.Date;

public class PulmonaryFunctionTest {
    private int id;
    private int visitId;
    private String testName;
    private Date testDate;
    private Double fev1Value;
    private Double fvcValue;
    private Double fev1FvcRatio;
    private String reportDetails;
    private Date createdAt;
    private Date updatedAt;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getVisitId() { return visitId; }
    public void setVisitId(int visitId) { this.visitId = visitId; }

    public String getTestName() { return testName; }
    public void setTestName(String testName) { this.testName = testName; }

    public Date getTestDate() { return testDate; }
    public void setTestDate(Date testDate) { this.testDate = testDate; }

    public Double getFev1Value() { return fev1Value; }
    public void setFev1Value(Double fev1Value) { this.fev1Value = fev1Value; }

    public Double getFvcValue() { return fvcValue; }
    public void setFvcValue(Double fvcValue) { this.fvcValue = fvcValue; }

    public Double getFev1FvcRatio() { return fev1FvcRatio; }
    public void setFev1FvcRatio(Double fev1FvcRatio) { this.fev1FvcRatio = fev1FvcRatio; }

    public String getReportDetails() { return reportDetails; }
    public void setReportDetails(String reportDetails) { this.reportDetails = reportDetails; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}