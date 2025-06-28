package com.szz.model;

import java.util.Date;

public class UrbanRuralEnvironmentMonitoring {
    private int id;
    private int surveyParticipantId;
    private String monitoringLocationType; // 监测地点类型
    private String cityPm25AnnualAvgSeasonalChange; // 城市PM2.5年均浓度、季节性变化
    private String cityPollenMainTypesMonthlyDistPeak; // 城市花粉主要种类、月度分布、峰值浓度
    private String cityOtherPollutants; // 城市其他污染物 NO₂, SO₂, 臭氧等
    private String cityMonitoringPointLocation; // 城市监测点位置
    private String ruralPm25StrawBurningAnnualAvg; // 农村PM2.5秸秆焚烧期浓度、年均值
    private String ruralPollenCropRelated; // 农村农作物相关花粉
    private Boolean ruralBiomassFuelIndoorPollution; // 农村生物质燃料使用导致的室内污染：有/无
    private String drinkingWaterSourceType; // 饮用水源类型
    private Date createdAt;
    private Date updatedAt;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSurveyParticipantId() { return surveyParticipantId; }
    public void setSurveyParticipantId(int surveyParticipantId) { this.surveyParticipantId = surveyParticipantId; }

    public String getMonitoringLocationType() { return monitoringLocationType; }
    public void setMonitoringLocationType(String monitoringLocationType) { this.monitoringLocationType = monitoringLocationType; }

    public String getCityPm25AnnualAvgSeasonalChange() { return cityPm25AnnualAvgSeasonalChange; }
    public void setCityPm25AnnualAvgSeasonalChange(String cityPm25AnnualAvgSeasonalChange) { this.cityPm25AnnualAvgSeasonalChange = cityPm25AnnualAvgSeasonalChange; }

    public String getCityPollenMainTypesMonthlyDistPeak() { return cityPollenMainTypesMonthlyDistPeak; }
    public void setCityPollenMainTypesMonthlyDistPeak(String cityPollenMainTypesMonthlyDistPeak) { this.cityPollenMainTypesMonthlyDistPeak = cityPollenMainTypesMonthlyDistPeak; }

    public String getCityOtherPollutants() { return cityOtherPollutants; }
    public void setCityOtherPollutants(String cityOtherPollutants) { this.cityOtherPollutants = cityOtherPollutants; }

    public String getCityMonitoringPointLocation() { return cityMonitoringPointLocation; }
    public void setCityMonitoringPointLocation(String cityMonitoringPointLocation) { this.cityMonitoringPointLocation = cityMonitoringPointLocation; }

    public String getRuralPm25StrawBurningAnnualAvg() { return ruralPm25StrawBurningAnnualAvg; }
    public void setRuralPm25StrawBurningAnnualAvg(String ruralPm25StrawBurningAnnualAvg) { this.ruralPm25StrawBurningAnnualAvg = ruralPm25StrawBurningAnnualAvg; }

    public String getRuralPollenCropRelated() { return ruralPollenCropRelated; }
    public void setRuralPollenCropRelated(String ruralPollenCropRelated) { this.ruralPollenCropRelated = ruralPollenCropRelated; }

    public Boolean getRuralBiomassFuelIndoorPollution() { return ruralBiomassFuelIndoorPollution; }
    public void setRuralBiomassFuelIndoorPollution(Boolean ruralBiomassFuelIndoorPollution) { this.ruralBiomassFuelIndoorPollution = ruralBiomassFuelIndoorPollution; }

    public String getDrinkingWaterSourceType() { return drinkingWaterSourceType; }
    public void setDrinkingWaterSourceType(String drinkingWaterSourceType) { this.drinkingWaterSourceType = drinkingWaterSourceType; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
