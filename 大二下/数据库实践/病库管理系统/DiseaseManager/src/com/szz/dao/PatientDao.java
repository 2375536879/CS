package com.szz.dao;

import com.szz.model.Patient;
import com.szz.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PatientDao {

    public void addPatient(Patient patient) throws Exception {
        String sql = "INSERT INTO patients (hospital_patient_id, name, gender, date_of_birth, home_address, birth_weight_kg, lifestyle_notes, positive_food_allergen_history, positive_inhaled_allergen_history, allergic_disease_history, family_allergy_history_degree1, family_allergic_disease_history_degree1, family_allergy_history_degree2, family_allergic_disease_history_degree2) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patient.getHospitalPatientId());
            ps.setString(2, patient.getName());
            ps.setString(3, patient.getGender());
            ps.setDate(4, new java.sql.Date(patient.getDateOfBirth().getTime()));
            ps.setString(5, patient.getHomeAddress());
            ps.setDouble(6, patient.getBirthWeightKg());
            ps.setString(7, patient.getLifestyleNotes());
            ps.setString(8, patient.getPositiveFoodAllergenHistory());
            ps.setString(9, patient.getPositiveInhaledAllergenHistory());
            ps.setString(10, patient.getAllergicDiseaseHistory());
            ps.setString(11, patient.getFamilyAllergyHistoryDegree1());
            ps.setString(12, patient.getFamilyAllergicDiseaseHistoryDegree1());
            ps.setString(13, patient.getFamilyAllergyHistoryDegree2());
            ps.setString(14, patient.getFamilyAllergicDiseaseHistoryDegree2());
            ps.executeUpdate();
        }
    }


    public List<Patient> getAllPatients() throws Exception {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Patient p = new Patient();
                p.setId(rs.getInt("id"));
                p.setHospitalPatientId(rs.getString("hospital_patient_id"));
                p.setName(rs.getString("name"));
                p.setGender(rs.getString("gender"));
                p.setDateOfBirth(rs.getDate("date_of_birth"));
                p.setHomeAddress(rs.getString("home_address"));
                p.setBirthWeightKg(rs.getDouble("birth_weight_kg"));
                p.setLifestyleNotes(rs.getString("lifestyle_notes"));
                p.setPositiveFoodAllergenHistory(rs.getString("positive_food_allergen_history"));
                p.setPositiveInhaledAllergenHistory(rs.getString("positive_inhaled_allergen_history"));
                p.setAllergicDiseaseHistory(rs.getString("allergic_disease_history"));
                p.setFamilyAllergyHistoryDegree1(rs.getString("family_allergy_history_degree1"));
                p.setFamilyAllergicDiseaseHistoryDegree1(rs.getString("family_allergic_disease_history_degree1"));
                p.setFamilyAllergyHistoryDegree2(rs.getString("family_allergy_history_degree2"));
                p.setFamilyAllergicDiseaseHistoryDegree2(rs.getString("family_allergic_disease_history_degree2"));
                p.setCreatedAt(rs.getTimestamp("created_at"));
                p.setUpdatedAt(rs.getTimestamp("updated_at"));
                patients.add(p);
            }
        }
        return patients;
    }




}
