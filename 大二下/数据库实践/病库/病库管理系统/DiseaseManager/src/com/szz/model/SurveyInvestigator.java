package com.szz.model;

import java.util.Date;

public class SurveyInvestigator {
    private int id;
    private int surveyParticipantId;
    private String investigatorName;
    private String investigatorTitle;
    private Date createdAt;
    private Date updatedAt;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSurveyParticipantId() { return surveyParticipantId; }
    public void setSurveyParticipantId(int surveyParticipantId) { this.surveyParticipantId = surveyParticipantId; }

    public String getInvestigatorName() { return investigatorName; }
    public void setInvestigatorName(String investigatorName) { this.investigatorName = investigatorName; }

    public String getInvestigatorTitle() { return investigatorTitle; }
    public void setInvestigatorTitle(String investigatorTitle) { this.investigatorTitle = investigatorTitle; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
