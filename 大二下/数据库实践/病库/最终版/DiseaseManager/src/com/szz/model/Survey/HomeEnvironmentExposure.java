package com.szz.model.Survey;

import java.util.Date;

public class HomeEnvironmentExposure {
    private int id;
    private int participantId;
    private String housingType;
    private Integer buildingAge;
    private String heatingType;
    private String cookingFuelType;
    private Boolean hasAirConditioning;
    private Boolean hasVentilationSystem;
    private String humidityLevel;
    private String temperatureControl;
    private Boolean hasPets;
    private String petTypes;
    private Boolean hasIndoorPlants;
    private String plantTypes;
    private Boolean hasSmokingIndoors;
    private String smokingFrequency;
    private Boolean hasChemicalExposure;
    private String chemicalTypes;
    private Boolean hasMoldOrDampness;
    private String moldLocation;
    private Boolean hasDustMites;
    private String dustMiteLocation;
    private Boolean hasCarpets;
    private String carpetType;
    private String cleaningFrequency;
    private String cleaningProducts;
    private Boolean hasAirPurifier;
    private String airPurifierType;
    private String waterSource;
    private String waterQuality;
    private String noiseLevel;
    private String lightingConditions;
    private String nearbyPollutionSources;
    private String neighborhoodEnvironment;
    private String notes;
    private Date createdAt;
    private Date updatedAt;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getParticipantId() { return participantId; }
    public void setParticipantId(int participantId) { this.participantId = participantId; }

    public String getHousingType() { return housingType; }
    public void setHousingType(String housingType) { this.housingType = housingType; }

    public Integer getBuildingAge() { return buildingAge; }
    public void setBuildingAge(Integer buildingAge) { this.buildingAge = buildingAge; }

    public String getHeatingType() { return heatingType; }
    public void setHeatingType(String heatingType) { this.heatingType = heatingType; }

    public String getCookingFuelType() { return cookingFuelType; }
    public void setCookingFuelType(String cookingFuelType) { this.cookingFuelType = cookingFuelType; }

    public Boolean getHasAirConditioning() { return hasAirConditioning; }
    public void setHasAirConditioning(Boolean hasAirConditioning) { this.hasAirConditioning = hasAirConditioning; }

    public Boolean getHasVentilationSystem() { return hasVentilationSystem; }
    public void setHasVentilationSystem(Boolean hasVentilationSystem) { this.hasVentilationSystem = hasVentilationSystem; }

    public String getHumidityLevel() { return humidityLevel; }
    public void setHumidityLevel(String humidityLevel) { this.humidityLevel = humidityLevel; }

    public String getTemperatureControl() { return temperatureControl; }
    public void setTemperatureControl(String temperatureControl) { this.temperatureControl = temperatureControl; }

    public Boolean getHasPets() { return hasPets; }
    public void setHasPets(Boolean hasPets) { this.hasPets = hasPets; }

    public String getPetTypes() { return petTypes; }
    public void setPetTypes(String petTypes) { this.petTypes = petTypes; }

    public Boolean getHasIndoorPlants() { return hasIndoorPlants; }
    public void setHasIndoorPlants(Boolean hasIndoorPlants) { this.hasIndoorPlants = hasIndoorPlants; }

    public String getPlantTypes() { return plantTypes; }
    public void setPlantTypes(String plantTypes) { this.plantTypes = plantTypes; }

    public Boolean getHasSmokingIndoors() { return hasSmokingIndoors; }
    public void setHasSmokingIndoors(Boolean hasSmokingIndoors) { this.hasSmokingIndoors = hasSmokingIndoors; }

    public String getSmokingFrequency() { return smokingFrequency; }
    public void setSmokingFrequency(String smokingFrequency) { this.smokingFrequency = smokingFrequency; }

    public Boolean getHasChemicalExposure() { return hasChemicalExposure; }
    public void setHasChemicalExposure(Boolean hasChemicalExposure) { this.hasChemicalExposure = hasChemicalExposure; }

    public String getChemicalTypes() { return chemicalTypes; }
    public void setChemicalTypes(String chemicalTypes) { this.chemicalTypes = chemicalTypes; }

    public Boolean getHasMoldOrDampness() { return hasMoldOrDampness; }
    public void setHasMoldOrDampness(Boolean hasMoldOrDampness) { this.hasMoldOrDampness = hasMoldOrDampness; }

    public String getMoldLocation() { return moldLocation; }
    public void setMoldLocation(String moldLocation) { this.moldLocation = moldLocation; }

    public Boolean getHasDustMites() { return hasDustMites; }
    public void setHasDustMites(Boolean hasDustMites) { this.hasDustMites = hasDustMites; }

    public String getDustMiteLocation() { return dustMiteLocation; }
    public void setDustMiteLocation(String dustMiteLocation) { this.dustMiteLocation = dustMiteLocation; }

    public Boolean getHasCarpets() { return hasCarpets; }
    public void setHasCarpets(Boolean hasCarpets) { this.hasCarpets = hasCarpets; }

    public String getCarpetType() { return carpetType; }
    public void setCarpetType(String carpetType) { this.carpetType = carpetType; }

    public String getCleaningFrequency() { return cleaningFrequency; }
    public void setCleaningFrequency(String cleaningFrequency) { this.cleaningFrequency = cleaningFrequency; }

    public String getCleaningProducts() { return cleaningProducts; }
    public void setCleaningProducts(String cleaningProducts) { this.cleaningProducts = cleaningProducts; }

    public Boolean getHasAirPurifier() { return hasAirPurifier; }
    public void setHasAirPurifier(Boolean hasAirPurifier) { this.hasAirPurifier = hasAirPurifier; }

    public String getAirPurifierType() { return airPurifierType; }
    public void setAirPurifierType(String airPurifierType) { this.airPurifierType = airPurifierType; }

    public String getWaterSource() { return waterSource; }
    public void setWaterSource(String waterSource) { this.waterSource = waterSource; }

    public String getWaterQuality() { return waterQuality; }
    public void setWaterQuality(String waterQuality) { this.waterQuality = waterQuality; }

    public String getNoiseLevel() { return noiseLevel; }
    public void setNoiseLevel(String noiseLevel) { this.noiseLevel = noiseLevel; }

    public String getLightingConditions() { return lightingConditions; }
    public void setLightingConditions(String lightingConditions) { this.lightingConditions = lightingConditions; }

    public String getNearbyPollutionSources() { return nearbyPollutionSources; }
    public void setNearbyPollutionSources(String nearbyPollutionSources) { this.nearbyPollutionSources = nearbyPollutionSources; }

    public String getNeighborhoodEnvironment() { return neighborhoodEnvironment; }
    public void setNeighborhoodEnvironment(String neighborhoodEnvironment) { this.neighborhoodEnvironment = neighborhoodEnvironment; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }


}
