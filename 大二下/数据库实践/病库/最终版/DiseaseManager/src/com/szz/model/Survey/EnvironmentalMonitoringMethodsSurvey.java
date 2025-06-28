package com.szz.model.Survey;

import java.util.Date;

public class EnvironmentalMonitoringMethodsSurvey {
    private int id;
    private int surveyParticipantId;
    private String pm25DetectorModel; // PM2.5检测仪型号
    private String pollenSamplingMethod; // 花粉采样方法
    private String dustMiteDetectionMethod; // 尘螨检测方法
    private String environmentalSensorTechnologyNotes; // 环境传感器技术备注
    private Date createdAt;
    private Date updatedAt;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSurveyParticipantId() { return surveyParticipantId; }
    public void setSurveyParticipantId(int surveyParticipantId) { this.surveyParticipantId = surveyParticipantId; }

    public String getPm25DetectorModel() { return pm25DetectorModel; }
    public void setPm25DetectorModel(String pm25DetectorModel) { this.pm25DetectorModel = pm25DetectorModel; }

    public String getPollenSamplingMethod() { return pollenSamplingMethod; }
    public void setPollenSamplingMethod(String pollenSamplingMethod) { this.pollenSamplingMethod = pollenSamplingMethod; }

    public String getDustMiteDetectionMethod() { return dustMiteDetectionMethod; }
    public void setDustMiteDetectionMethod(String dustMiteDetectionMethod) { this.dustMiteDetectionMethod = dustMiteDetectionMethod; }

    public String getEnvironmentalSensorTechnologyNotes() { return environmentalSensorTechnologyNotes; }
    public void setEnvironmentalSensorTechnologyNotes(String environmentalSensorTechnologyNotes) { this.environmentalSensorTechnologyNotes = environmentalSensorTechnologyNotes; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
