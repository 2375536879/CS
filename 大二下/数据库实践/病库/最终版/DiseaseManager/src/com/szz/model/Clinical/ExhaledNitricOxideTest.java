package com.szz.model.Clinical;

import java.util.Date;

public class ExhaledNitricOxideTest {
    private int id;
    private int visitId;
    private String testName;
    private Date testDate;
    private Integer fenoValuePpb;
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

    public Integer getFenoValuePpb() { return fenoValuePpb; }
    public void setFenoValuePpb(Integer fenoValuePpb) { this.fenoValuePpb = fenoValuePpb; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}