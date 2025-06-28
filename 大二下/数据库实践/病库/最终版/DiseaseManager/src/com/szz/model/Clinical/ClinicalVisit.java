package com.szz.model.Clinical;

import java.util.Date;

public class ClinicalVisit {
    private int id;
    private int patientId;
    private Date visitDate;
    private double heightCm;
    private double weightKg;
    private String healthcareProviderName;
    private String healthcareProviderTitle;
    private String institutionName;
    private String institutionAddress;
    private Date createdAt;
    private Date updatedAt;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public Date getVisitDate() { return visitDate; }
    public void setVisitDate(Date visitDate) { this.visitDate = visitDate; }

    public double getHeightCm() { return heightCm; }
    public void setHeightCm(double heightCm) { this.heightCm = heightCm; }

    public double getWeightKg() { return weightKg; }
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }

    public String getHealthcareProviderName() { return healthcareProviderName; }
    public void setHealthcareProviderName(String healthcareProviderName) { this.healthcareProviderName = healthcareProviderName; }

    public String getHealthcareProviderTitle() { return healthcareProviderTitle; }
    public void setHealthcareProviderTitle(String healthcareProviderTitle) { this.healthcareProviderTitle = healthcareProviderTitle; }

    public String getInstitutionName() { return institutionName; }
    public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }

    public String getInstitutionAddress() { return institutionAddress; }
    public void setInstitutionAddress(String institutionAddress) { this.institutionAddress = institutionAddress; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    // 内部类：症状和体征
    public static class VisitSymptomSign {
        private int id;
        private int visitId;
        private String symptomDescription;
        // 哮喘症状
        private boolean asthmaWheezing;
        private boolean asthmaCoughing;
        private boolean asthmaDyspnea;
        private boolean asthmaActivityLimitation;
        private boolean asthmaNightAwakening;
        private boolean asthmaChestTightness;
        private boolean asthmaNightMorningSymptoms;
        private String asthmaTriggersDescription;
        // 过敏性鼻炎症状
        private boolean arClearNasalDischarge;
        private boolean arNasalItching;
        private boolean arNasalCongestion;
        private boolean arParoxysmalSneezing;
        private boolean arOlfactoryDecline;
        private boolean arThroatClearing;
        private boolean arSniffing;
        private boolean arIrritativeDryCough;
        private boolean arGlobusSensation;
        private boolean arEyeItching;
        private boolean arBlinking;
        private boolean arSleepDisturbance;
        private boolean arDailyActivityLimitation;
        private boolean arSchoolWorkPerformanceDecline;
        // 湿疹/AD症状
        private boolean adChronicRecurrentPruriticRash;
        private boolean adFlexuralOrFacialDermatitisHistory;
        private boolean adDrySkin;
        private boolean adXerosis;
        private boolean adRetroauricularFissures;
        private boolean adIchthyosis;
        private boolean adPalmarHyperlinearity;
        private boolean adKeratosisPilaris;
        private boolean adSkinInfectionTendency;
        private boolean adDennieMorganInfraorbitalFold;
        private boolean adPeriorbitalDarkening;
        private boolean adFacialPallor;
        private boolean adPityriasisAlba;
        private boolean adAnteriorNeckFolds;
        // 一般症状信息
        private String symptomSeverity;
        private String symptomFrequency;
        private Date symptomStartDate;
        private String symptomTriggersGeneral;
        private String signDescription;
        // 过敏性鼻炎体征
        private boolean arNasalMucosaSwelling;
        private boolean arNasalMucosaPaleOrCongested;
        private boolean arNasalDischargeObserved;
        private boolean arTurbinateHypertrophy;
        private boolean arAllergicShiners;

        // Getters and Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public int getVisitId() { return visitId; }
        public void setVisitId(int visitId) { this.visitId = visitId; }

        public String getSymptomDescription() { return symptomDescription; }
        public void setSymptomDescription(String symptomDescription) { this.symptomDescription = symptomDescription; }

        public boolean isAsthmaWheezing() { return asthmaWheezing; }
        public void setAsthmaWheezing(boolean asthmaWheezing) { this.asthmaWheezing = asthmaWheezing; }

        public boolean isAsthmaCoughing() { return asthmaCoughing; }
        public void setAsthmaCoughing(boolean asthmaCoughing) { this.asthmaCoughing = asthmaCoughing; }

        public boolean isAsthmaDyspnea() { return asthmaDyspnea; }
        public void setAsthmaDyspnea(boolean asthmaDyspnea) { this.asthmaDyspnea = asthmaDyspnea; }

        public boolean isAsthmaActivityLimitation() { return asthmaActivityLimitation; }
        public void setAsthmaActivityLimitation(boolean asthmaActivityLimitation) { this.asthmaActivityLimitation = asthmaActivityLimitation; }

        public boolean isAsthmaNightAwakening() { return asthmaNightAwakening; }
        public void setAsthmaNightAwakening(boolean asthmaNightAwakening) { this.asthmaNightAwakening = asthmaNightAwakening; }

        public boolean isAsthmaChestTightness() { return asthmaChestTightness; }
        public void setAsthmaChestTightness(boolean asthmaChestTightness) { this.asthmaChestTightness = asthmaChestTightness; }

        public boolean isAsthmaNightMorningSymptoms() { return asthmaNightMorningSymptoms; }
        public void setAsthmaNightMorningSymptoms(boolean asthmaNightMorningSymptoms) { this.asthmaNightMorningSymptoms = asthmaNightMorningSymptoms; }

        public String getAsthmaTriggersDescription() { return asthmaTriggersDescription; }
        public void setAsthmaTriggersDescription(String asthmaTriggersDescription) { this.asthmaTriggersDescription = asthmaTriggersDescription; }

        public boolean isArClearNasalDischarge() { return arClearNasalDischarge; }
        public void setArClearNasalDischarge(boolean arClearNasalDischarge) { this.arClearNasalDischarge = arClearNasalDischarge; }

        public boolean isArNasalItching() { return arNasalItching; }
        public void setArNasalItching(boolean arNasalItching) { this.arNasalItching = arNasalItching; }

        public boolean isArNasalCongestion() { return arNasalCongestion; }
        public void setArNasalCongestion(boolean arNasalCongestion) { this.arNasalCongestion = arNasalCongestion; }

        public boolean isArParoxysmalSneezing() { return arParoxysmalSneezing; }
        public void setArParoxysmalSneezing(boolean arParoxysmalSneezing) { this.arParoxysmalSneezing = arParoxysmalSneezing; }

        public boolean isArOlfactoryDecline() { return arOlfactoryDecline; }
        public void setArOlfactoryDecline(boolean arOlfactoryDecline) { this.arOlfactoryDecline = arOlfactoryDecline; }

        public boolean isArThroatClearing() { return arThroatClearing; }
        public void setArThroatClearing(boolean arThroatClearing) { this.arThroatClearing = arThroatClearing; }

        public boolean isArSniffing() { return arSniffing; }
        public void setArSniffing(boolean arSniffing) { this.arSniffing = arSniffing; }

        public boolean isArIrritativeDryCough() { return arIrritativeDryCough; }
        public void setArIrritativeDryCough(boolean arIrritativeDryCough) { this.arIrritativeDryCough = arIrritativeDryCough; }

        public boolean isArGlobusSensation() { return arGlobusSensation; }
        public void setArGlobusSensation(boolean arGlobusSensation) { this.arGlobusSensation = arGlobusSensation; }

        public boolean isArEyeItching() { return arEyeItching; }
        public void setArEyeItching(boolean arEyeItching) { this.arEyeItching = arEyeItching; }

        public boolean isArBlinking() { return arBlinking; }
        public void setArBlinking(boolean arBlinking) { this.arBlinking = arBlinking; }

        public boolean isArSleepDisturbance() { return arSleepDisturbance; }
        public void setArSleepDisturbance(boolean arSleepDisturbance) { this.arSleepDisturbance = arSleepDisturbance; }

        public boolean isArDailyActivityLimitation() { return arDailyActivityLimitation; }
        public void setArDailyActivityLimitation(boolean arDailyActivityLimitation) { this.arDailyActivityLimitation = arDailyActivityLimitation; }

        public boolean isArSchoolWorkPerformanceDecline() { return arSchoolWorkPerformanceDecline; }
        public void setArSchoolWorkPerformanceDecline(boolean arSchoolWorkPerformanceDecline) { this.arSchoolWorkPerformanceDecline = arSchoolWorkPerformanceDecline; }

        public boolean isAdChronicRecurrentPruriticRash() { return adChronicRecurrentPruriticRash; }
        public void setAdChronicRecurrentPruriticRash(boolean adChronicRecurrentPruriticRash) { this.adChronicRecurrentPruriticRash = adChronicRecurrentPruriticRash; }

        public boolean isAdFlexuralOrFacialDermatitisHistory() { return adFlexuralOrFacialDermatitisHistory; }
        public void setAdFlexuralOrFacialDermatitisHistory(boolean adFlexuralOrFacialDermatitisHistory) { this.adFlexuralOrFacialDermatitisHistory = adFlexuralOrFacialDermatitisHistory; }

        public boolean isAdDrySkin() { return adDrySkin; }
        public void setAdDrySkin(boolean adDrySkin) { this.adDrySkin = adDrySkin; }

        public boolean isAdXerosis() { return adXerosis; }
        public void setAdXerosis(boolean adXerosis) { this.adXerosis = adXerosis; }

        public boolean isAdRetroauricularFissures() { return adRetroauricularFissures; }
        public void setAdRetroauricularFissures(boolean adRetroauricularFissures) { this.adRetroauricularFissures = adRetroauricularFissures; }

        public boolean isAdIchthyosis() { return adIchthyosis; }
        public void setAdIchthyosis(boolean adIchthyosis) { this.adIchthyosis = adIchthyosis; }

        public boolean isAdPalmarHyperlinearity() { return adPalmarHyperlinearity; }
        public void setAdPalmarHyperlinearity(boolean adPalmarHyperlinearity) { this.adPalmarHyperlinearity = adPalmarHyperlinearity; }

        public boolean isAdKeratosisPilaris() { return adKeratosisPilaris; }
        public void setAdKeratosisPilaris(boolean adKeratosisPilaris) { this.adKeratosisPilaris = adKeratosisPilaris; }

        public boolean isAdSkinInfectionTendency() { return adSkinInfectionTendency; }
        public void setAdSkinInfectionTendency(boolean adSkinInfectionTendency) { this.adSkinInfectionTendency = adSkinInfectionTendency; }

        public boolean isAdDennieMorganInfraorbitalFold() { return adDennieMorganInfraorbitalFold; }
        public void setAdDennieMorganInfraorbitalFold(boolean adDennieMorganInfraorbitalFold) { this.adDennieMorganInfraorbitalFold = adDennieMorganInfraorbitalFold; }

        public boolean isAdPeriorbitalDarkening() { return adPeriorbitalDarkening; }
        public void setAdPeriorbitalDarkening(boolean adPeriorbitalDarkening) { this.adPeriorbitalDarkening = adPeriorbitalDarkening; }

        public boolean isAdFacialPallor() { return adFacialPallor; }
        public void setAdFacialPallor(boolean adFacialPallor) { this.adFacialPallor = adFacialPallor; }

        public boolean isAdPityriasisAlba() { return adPityriasisAlba; }
        public void setAdPityriasisAlba(boolean adPityriasisAlba) { this.adPityriasisAlba = adPityriasisAlba; }

        public boolean isAdAnteriorNeckFolds() { return adAnteriorNeckFolds; }
        public void setAdAnteriorNeckFolds(boolean adAnteriorNeckFolds) { this.adAnteriorNeckFolds = adAnteriorNeckFolds; }

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

        public boolean isArNasalMucosaSwelling() { return arNasalMucosaSwelling; }
        public void setArNasalMucosaSwelling(boolean arNasalMucosaSwelling) { this.arNasalMucosaSwelling = arNasalMucosaSwelling; }

        public boolean isArNasalMucosaPaleOrCongested() { return arNasalMucosaPaleOrCongested; }
        public void setArNasalMucosaPaleOrCongested(boolean arNasalMucosaPaleOrCongested) { this.arNasalMucosaPaleOrCongested = arNasalMucosaPaleOrCongested; }

        public boolean isArNasalDischargeObserved() { return arNasalDischargeObserved; }
        public void setArNasalDischargeObserved(boolean arNasalDischargeObserved) { this.arNasalDischargeObserved = arNasalDischargeObserved; }

        public boolean isArTurbinateHypertrophy() { return arTurbinateHypertrophy; }
        public void setArTurbinateHypertrophy(boolean arTurbinateHypertrophy) { this.arTurbinateHypertrophy = arTurbinateHypertrophy; }

        public boolean isArAllergicShiners() { return arAllergicShiners; }
        public void setArAllergicShiners(boolean arAllergicShiners) { this.arAllergicShiners = arAllergicShiners; }
    }
}