package com.szz.model.Clinical;

import java.util.Date;

public class VisitSymptomsSign {
    private int id;
    private int visitId;
    private String symptomDescription;
    
    // 哮喘症状
    private Boolean asthmaWheezing;
    private Boolean asthmaCoughing;
    private Boolean asthmaDyspnea;
    private Boolean asthmaActivityLimitation;
    private Boolean asthmaNightAwakening;
    private Boolean asthmaChestTightness;
    private Boolean asthmaNightMorningSymptoms;
    private String asthmaTriggers;
    
    // 过敏性鼻炎症状
    private Boolean arClearNasalDischarge;
    private Boolean arNasalItching;
    private Boolean arNasalCongestion;
    private Boolean arParoxysmalSneezing;
    private Boolean arOlfactoryDecline;
    private Boolean arThroatClearing;
    private Boolean arSniffing;
    private Boolean arIrritativeDryCough;
    private Boolean arGlobusSensation;
    private Boolean arEyeItching;
    private Boolean arBlinking;
    private Boolean arSleepDisturbance;
    private Boolean arDailyActivityLimitation;
    private Boolean arSchoolWorkPerformanceDecline;
    
    // 湿疹/AD症状
    private Boolean adChronicRecurrentPruriticRash;
    private Boolean adFlexuralOrFacialDermatitisHistory;
    private Boolean adDrySkin;
    private Boolean adXerosis;
    private Boolean adRetroauricularFissures;
    private Boolean adIchthyosis;
    private Boolean adPalmarHyperlinearity;
    private Boolean adKeratosisPilaris;
    private Boolean adSkinInfectionTendency;
    private Boolean adDennieMorganInfraorbitalFold;
    private Boolean adPeriorbitalDarkening;
    private Boolean adFacialPallor;
    private Boolean adPityriasisAlba;
    private Boolean adAnteriorNeckFolds;
    
    // 症状相关信息
    private String symptomSeverity;
    private String symptomFrequency;
    private Date symptomStartDate;
    private String symptomTriggersGeneral;
    
    // 体征描述
    private String signDescription;
    
    // 过敏性鼻炎体征
    private Boolean arNasalMucosaSwelling;
    private Boolean arNasalMucosaPaleOrCongested;
    private Boolean arNasalDischargeObserved;
    private Boolean arTurbinateHypertrophy;
    private Boolean arAllergicShiners;
    private Boolean arAllergicSalute;
    private Boolean arAllergicCrease;
    
    // 哮喘体征
    private Boolean asthmaAuscultationWheezing;
    
    // 环境因素
    private String environmentalFactorsNotes;
    
    private Date createdAt;
    private Date updatedAt;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getVisitId() { return visitId; }
    public void setVisitId(int visitId) { this.visitId = visitId; }

    public String getSymptomDescription() { return symptomDescription; }
    public void setSymptomDescription(String symptomDescription) { this.symptomDescription = symptomDescription; }

    public Boolean getAsthmaWheezing() { return asthmaWheezing; }
    public void setAsthmaWheezing(Boolean asthmaWheezing) { this.asthmaWheezing = asthmaWheezing; }

    public Boolean getAsthmaCoughing() { return asthmaCoughing; }
    public void setAsthmaCoughing(Boolean asthmaCoughing) { this.asthmaCoughing = asthmaCoughing; }

    public Boolean getAsthmaDyspnea() { return asthmaDyspnea; }
    public void setAsthmaDyspnea(Boolean asthmaDyspnea) { this.asthmaDyspnea = asthmaDyspnea; }

    public Boolean getAsthmaActivityLimitation() { return asthmaActivityLimitation; }
    public void setAsthmaActivityLimitation(Boolean asthmaActivityLimitation) { this.asthmaActivityLimitation = asthmaActivityLimitation; }

    public Boolean getAsthmaNightAwakening() { return asthmaNightAwakening; }
    public void setAsthmaNightAwakening(Boolean asthmaNightAwakening) { this.asthmaNightAwakening = asthmaNightAwakening; }

    public Boolean getAsthmaChestTightness() { return asthmaChestTightness; }
    public void setAsthmaChestTightness(Boolean asthmaChestTightness) { this.asthmaChestTightness = asthmaChestTightness; }

    public Boolean getAsthmaNightMorningSymptoms() { return asthmaNightMorningSymptoms; }
    public void setAsthmaNightMorningSymptoms(Boolean asthmaNightMorningSymptoms) { this.asthmaNightMorningSymptoms = asthmaNightMorningSymptoms; }

    public String getAsthmaTriggers() { return asthmaTriggers; }
    public void setAsthmaTriggers(String asthmaTriggers) { this.asthmaTriggers = asthmaTriggers; }

    public Boolean getArClearNasalDischarge() { return arClearNasalDischarge; }
    public void setArClearNasalDischarge(Boolean arClearNasalDischarge) { this.arClearNasalDischarge = arClearNasalDischarge; }

    public Boolean getArNasalItching() { return arNasalItching; }
    public void setArNasalItching(Boolean arNasalItching) { this.arNasalItching = arNasalItching; }

    public Boolean getArNasalCongestion() { return arNasalCongestion; }
    public void setArNasalCongestion(Boolean arNasalCongestion) { this.arNasalCongestion = arNasalCongestion; }

    public Boolean getArParoxysmalSneezing() { return arParoxysmalSneezing; }
    public void setArParoxysmalSneezing(Boolean arParoxysmalSneezing) { this.arParoxysmalSneezing = arParoxysmalSneezing; }

    public Boolean getArOlfactoryDecline() { return arOlfactoryDecline; }
    public void setArOlfactoryDecline(Boolean arOlfactoryDecline) { this.arOlfactoryDecline = arOlfactoryDecline; }

    public Boolean getArThroatClearing() { return arThroatClearing; }
    public void setArThroatClearing(Boolean arThroatClearing) { this.arThroatClearing = arThroatClearing; }

    public Boolean getArSniffing() { return arSniffing; }
    public void setArSniffing(Boolean arSniffing) { this.arSniffing = arSniffing; }

    public Boolean getArIrritativeDryCough() { return arIrritativeDryCough; }
    public void setArIrritativeDryCough(Boolean arIrritativeDryCough) { this.arIrritativeDryCough = arIrritativeDryCough; }

    public Boolean getArGlobusSensation() { return arGlobusSensation; }
    public void setArGlobusSensation(Boolean arGlobusSensation) { this.arGlobusSensation = arGlobusSensation; }

    public Boolean getArEyeItching() { return arEyeItching; }
    public void setArEyeItching(Boolean arEyeItching) { this.arEyeItching = arEyeItching; }

    public Boolean getArBlinking() { return arBlinking; }
    public void setArBlinking(Boolean arBlinking) { this.arBlinking = arBlinking; }

    public Boolean getArSleepDisturbance() { return arSleepDisturbance; }
    public void setArSleepDisturbance(Boolean arSleepDisturbance) { this.arSleepDisturbance = arSleepDisturbance; }

    public Boolean getArDailyActivityLimitation() { return arDailyActivityLimitation; }
    public void setArDailyActivityLimitation(Boolean arDailyActivityLimitation) { this.arDailyActivityLimitation = arDailyActivityLimitation; }

    public Boolean getArSchoolWorkPerformanceDecline() { return arSchoolWorkPerformanceDecline; }
    public void setArSchoolWorkPerformanceDecline(Boolean arSchoolWorkPerformanceDecline) { this.arSchoolWorkPerformanceDecline = arSchoolWorkPerformanceDecline; }

    public Boolean getAdChronicRecurrentPruriticRash() { return adChronicRecurrentPruriticRash; }
    public void setAdChronicRecurrentPruriticRash(Boolean adChronicRecurrentPruriticRash) { this.adChronicRecurrentPruriticRash = adChronicRecurrentPruriticRash; }

    public Boolean getAdFlexuralOrFacialDermatitisHistory() { return adFlexuralOrFacialDermatitisHistory; }
    public void setAdFlexuralOrFacialDermatitisHistory(Boolean adFlexuralOrFacialDermatitisHistory) { this.adFlexuralOrFacialDermatitisHistory = adFlexuralOrFacialDermatitisHistory; }

    public Boolean getAdDrySkin() { return adDrySkin; }
    public void setAdDrySkin(Boolean adDrySkin) { this.adDrySkin = adDrySkin; }

    public Boolean getAdXerosis() { return adXerosis; }
    public void setAdXerosis(Boolean adXerosis) { this.adXerosis = adXerosis; }

    public Boolean getAdRetroauricularFissures() { return adRetroauricularFissures; }
    public void setAdRetroauricularFissures(Boolean adRetroauricularFissures) { this.adRetroauricularFissures = adRetroauricularFissures; }

    public Boolean getAdIchthyosis() { return adIchthyosis; }
    public void setAdIchthyosis(Boolean adIchthyosis) { this.adIchthyosis = adIchthyosis; }

    public Boolean getAdPalmarHyperlinearity() { return adPalmarHyperlinearity; }
    public void setAdPalmarHyperlinearity(Boolean adPalmarHyperlinearity) { this.adPalmarHyperlinearity = adPalmarHyperlinearity; }

    public Boolean getAdKeratosisPilaris() { return adKeratosisPilaris; }
    public void setAdKeratosisPilaris(Boolean adKeratosisPilaris) { this.adKeratosisPilaris = adKeratosisPilaris; }

    public Boolean getAdSkinInfectionTendency() { return adSkinInfectionTendency; }
    public void setAdSkinInfectionTendency(Boolean adSkinInfectionTendency) { this.adSkinInfectionTendency = adSkinInfectionTendency; }

    public Boolean getAdDennieMorganInfraorbitalFold() { return adDennieMorganInfraorbitalFold; }
    public void setAdDennieMorganInfraorbitalFold(Boolean adDennieMorganInfraorbitalFold) { this.adDennieMorganInfraorbitalFold = adDennieMorganInfraorbitalFold; }

    public Boolean getAdPeriorbitalDarkening() { return adPeriorbitalDarkening; }
    public void setAdPeriorbitalDarkening(Boolean adPeriorbitalDarkening) { this.adPeriorbitalDarkening = adPeriorbitalDarkening; }

    public Boolean getAdFacialPallor() { return adFacialPallor; }
    public void setAdFacialPallor(Boolean adFacialPallor) { this.adFacialPallor = adFacialPallor; }

    public Boolean getAdPityriasisAlba() { return adPityriasisAlba; }
    public void setAdPityriasisAlba(Boolean adPityriasisAlba) { this.adPityriasisAlba = adPityriasisAlba; }

    public Boolean getAdAnteriorNeckFolds() { return adAnteriorNeckFolds; }
    public void setAdAnteriorNeckFolds(Boolean adAnteriorNeckFolds) { this.adAnteriorNeckFolds = adAnteriorNeckFolds; }

    public String getSymptomSeverity() { return symptomSeverity; }
    public void setSymptomSeverity(String symptomSeverity) { this.symptomSeverity = symptomSeverity; }

    public String getSymptomFrequency() { return symptomFrequency; }
    public void setSymptomFrequency(String symptomFrequency) { this.symptomFrequency = symptomFrequency; }

    public Date getSymptomStartDate() { return symptomStartDate; }
    public void setSymptomStartDate(Date symptomStartDate) { this.symptomStartDate = symptomStartDate; }

    public String getSymptomTriggersGeneral() { return symptomTriggersGeneral; }
    public void setSymptomTriggersGeneral(String symptomTriggersGeneral) { this.symptomTriggersGeneral = symptomTriggersGeneral; }

    public String getSignDescription() { return signDescription; }
    public void setSignDescription(String signDescription) { this.signDescription = signDescription; }

    public Boolean getArNasalMucosaSwelling() { return arNasalMucosaSwelling; }
    public void setArNasalMucosaSwelling(Boolean arNasalMucosaSwelling) { this.arNasalMucosaSwelling = arNasalMucosaSwelling; }

    public Boolean getArNasalMucosaPaleOrCongested() { return arNasalMucosaPaleOrCongested; }
    public void setArNasalMucosaPaleOrCongested(Boolean arNasalMucosaPaleOrCongested) { this.arNasalMucosaPaleOrCongested = arNasalMucosaPaleOrCongested; }

    public Boolean getArNasalDischargeObserved() { return arNasalDischargeObserved; }
    public void setArNasalDischargeObserved(Boolean arNasalDischargeObserved) { this.arNasalDischargeObserved = arNasalDischargeObserved; }

    public Boolean getArTurbinateHypertrophy() { return arTurbinateHypertrophy; }
    public void setArTurbinateHypertrophy(Boolean arTurbinateHypertrophy) { this.arTurbinateHypertrophy = arTurbinateHypertrophy; }

    public Boolean getArAllergicShiners() { return arAllergicShiners; }
    public void setArAllergicShiners(Boolean arAllergicShiners) { this.arAllergicShiners = arAllergicShiners; }

    public Boolean getArAllergicSalute() { return arAllergicSalute; }
    public void setArAllergicSalute(Boolean arAllergicSalute) { this.arAllergicSalute = arAllergicSalute; }

    public Boolean getArAllergicCrease() { return arAllergicCrease; }
    public void setArAllergicCrease(Boolean arAllergicCrease) { this.arAllergicCrease = arAllergicCrease; }

    public Boolean getAsthmaAuscultationWheezing() { return asthmaAuscultationWheezing; }
    public void setAsthmaAuscultationWheezing(Boolean asthmaAuscultationWheezing) { this.asthmaAuscultationWheezing = asthmaAuscultationWheezing; }

    public String getEnvironmentalFactorsNotes() { return environmentalFactorsNotes; }
    public void setEnvironmentalFactorsNotes(String environmentalFactorsNotes) { this.environmentalFactorsNotes = environmentalFactorsNotes; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
