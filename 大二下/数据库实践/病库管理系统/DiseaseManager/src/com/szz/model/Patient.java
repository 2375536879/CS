package com.szz.model;

import java.util.Date;

public class Patient {


    private int id;
    private String hospitalPatientId;
    private String name;
    private String gender; // 可选：枚举类型处理
    private Date dateOfBirth;
    private String homeAddress;
    private Double birthWeightKg;
    private String lifestyleNotes;
    private String positiveFoodAllergenHistory;
    private String positiveInhaledAllergenHistory;
    private String allergicDiseaseHistory;
    private String familyAllergyHistoryDegree1;
    private String familyAllergicDiseaseHistoryDegree1;
    private String familyAllergyHistoryDegree2;
    private String familyAllergicDiseaseHistoryDegree2;
    private Date createdAt;
    private Date updatedAt;


    public Double getBirthWeightKg() {
        return birthWeightKg;
    }

    public void setBirthWeightKg(Double birthWeightKg) {
        this.birthWeightKg = birthWeightKg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHospitalPatientId() {
        return hospitalPatientId;
    }

    public void setHospitalPatientId(String hospitalPatientId) {
        this.hospitalPatientId = hospitalPatientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getLifestyleNotes() {
        return lifestyleNotes;
    }

    public void setLifestyleNotes(String lifestyleNotes) {
        this.lifestyleNotes = lifestyleNotes;
    }

    public String getPositiveFoodAllergenHistory() {
        return positiveFoodAllergenHistory;
    }

    public void setPositiveFoodAllergenHistory(String positiveFoodAllergenHistory) {
        this.positiveFoodAllergenHistory = positiveFoodAllergenHistory;
    }

    public String getPositiveInhaledAllergenHistory() {
        return positiveInhaledAllergenHistory;
    }

    public void setPositiveInhaledAllergenHistory(String positiveInhaledAllergenHistory) {
        this.positiveInhaledAllergenHistory = positiveInhaledAllergenHistory;
    }

    public String getAllergicDiseaseHistory() {
        return allergicDiseaseHistory;
    }

    public void setAllergicDiseaseHistory(String allergicDiseaseHistory) {
        this.allergicDiseaseHistory = allergicDiseaseHistory;
    }

    public String getFamilyAllergyHistoryDegree1() {
        return familyAllergyHistoryDegree1;
    }

    public void setFamilyAllergyHistoryDegree1(String familyAllergyHistoryDegree1) {
        this.familyAllergyHistoryDegree1 = familyAllergyHistoryDegree1;
    }

    public String getFamilyAllergicDiseaseHistoryDegree1() {
        return familyAllergicDiseaseHistoryDegree1;
    }

    public void setFamilyAllergicDiseaseHistoryDegree1(String familyAllergicDiseaseHistoryDegree1) {
        this.familyAllergicDiseaseHistoryDegree1 = familyAllergicDiseaseHistoryDegree1;
    }

    public String getFamilyAllergyHistoryDegree2() {
        return familyAllergyHistoryDegree2;
    }

    public void setFamilyAllergyHistoryDegree2(String familyAllergyHistoryDegree2) {
        this.familyAllergyHistoryDegree2 = familyAllergyHistoryDegree2;
    }

    public String getFamilyAllergicDiseaseHistoryDegree2() {
        return familyAllergicDiseaseHistoryDegree2;
    }

    public void setFamilyAllergicDiseaseHistoryDegree2(String familyAllergicDiseaseHistoryDegree2) {
        this.familyAllergicDiseaseHistoryDegree2 = familyAllergicDiseaseHistoryDegree2;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
