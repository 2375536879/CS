package com.szz.model;

import java.math.BigDecimal;
import java.util.Date;

public class WorkStudyEnvironmentExposure {
    private int id;
    private int surveyParticipantId;
    private String locationType; // 学校/单位位置
    private String roomVentilationStatus; // 教室/办公室通风情况
    private BigDecimal pm25ExposureAnnualAvg; // PM2.5暴露水平年均值
    private String pollenExposurePeakConcentration; // 花粉暴露季节性峰值浓度
    private String pollenTypesWorkStudy; // 花粉种类
    private String formaldehydeNewDecorValue; // 新装修教室/办公室甲醛检测值
    private Boolean dustMiteExposureCarpet; // 尘螨暴露:地毯有/无
    private Boolean dustMiteExposureFabricFurniture; // 布艺家具使用情况：有/无
    private Date createdAt;
    private Date updatedAt;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSurveyParticipantId() { return surveyParticipantId; }
    public void setSurveyParticipantId(int surveyParticipantId) { this.surveyParticipantId = surveyParticipantId; }

    public String getLocationType() { return locationType; }
    public void setLocationType(String locationType) { this.locationType = locationType; }

    public String getRoomVentilationStatus() { return roomVentilationStatus; }
    public void setRoomVentilationStatus(String roomVentilationStatus) { this.roomVentilationStatus = roomVentilationStatus; }

    public BigDecimal getPm25ExposureAnnualAvg() { return pm25ExposureAnnualAvg; }
    public void setPm25ExposureAnnualAvg(BigDecimal pm25ExposureAnnualAvg) { this.pm25ExposureAnnualAvg = pm25ExposureAnnualAvg; }

    public String getPollenExposurePeakConcentration() { return pollenExposurePeakConcentration; }
    public void setPollenExposurePeakConcentration(String pollenExposurePeakConcentration) { this.pollenExposurePeakConcentration = pollenExposurePeakConcentration; }

    public String getPollenTypesWorkStudy() { return pollenTypesWorkStudy; }
    public void setPollenTypesWorkStudy(String pollenTypesWorkStudy) { this.pollenTypesWorkStudy = pollenTypesWorkStudy; }

    public String getFormaldehydeNewDecorValue() { return formaldehydeNewDecorValue; }
    public void setFormaldehydeNewDecorValue(String formaldehydeNewDecorValue) { this.formaldehydeNewDecorValue = formaldehydeNewDecorValue; }

    public Boolean getDustMiteExposureCarpet() { return dustMiteExposureCarpet; }
    public void setDustMiteExposureCarpet(Boolean dustMiteExposureCarpet) { this.dustMiteExposureCarpet = dustMiteExposureCarpet; }

    public Boolean getDustMiteExposureFabricFurniture() { return dustMiteExposureFabricFurniture; }
    public void setDustMiteExposureFabricFurniture(Boolean dustMiteExposureFabricFurniture) { this.dustMiteExposureFabricFurniture = dustMiteExposureFabricFurniture; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
