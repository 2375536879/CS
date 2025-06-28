package com.szz.model;

import java.util.Date;

public class ImagingStudy {
    private int id;
    private int visitId;
    private String studyName;
    private Date studyDate;
    private String reportSummary;
    private String imagePathOrIdentifier;
    private Date createdAt;
    private Date updatedAt;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getVisitId() { return visitId; }
    public void setVisitId(int visitId) { this.visitId = visitId; }

    public String getStudyName() { return studyName; }
    public void setStudyName(String studyName) { this.studyName = studyName; }

    public Date getStudyDate() { return studyDate; }
    public void setStudyDate(Date studyDate) { this.studyDate = studyDate; }

    public String getReportSummary() { return reportSummary; }
    public void setReportSummary(String reportSummary) { this.reportSummary = reportSummary; }

    public String getImagePathOrIdentifier() { return imagePathOrIdentifier; }
    public void setImagePathOrIdentifier(String imagePathOrIdentifier) { this.imagePathOrIdentifier = imagePathOrIdentifier; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}