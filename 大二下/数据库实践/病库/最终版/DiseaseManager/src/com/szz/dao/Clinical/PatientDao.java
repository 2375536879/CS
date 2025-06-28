package com.szz.dao.Clinical;

import com.szz.model.Clinical.Patient;
import com.szz.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDao {

    // 添加患者
    public int addPatient(Patient patient) throws Exception {
        String sql = "INSERT INTO patients (hospital_patient_id, name, gender, date_of_birth, home_address, " +
                "birth_weight_kg, lifestyle_notes, positive_food_allergen_history, positive_inhaled_allergen_history, " +
                "allergic_disease_history, family_allergy_history_degree1, family_allergic_disease_history_degree1, " +
                "family_allergy_history_degree2, family_allergic_disease_history_degree2) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, patient.getHospitalPatientId());
            ps.setString(2, patient.getName());
            ps.setString(3, patient.getGender());
            ps.setDate(4, patient.getDateOfBirth() != null ? new java.sql.Date(patient.getDateOfBirth().getTime()) : null);
            ps.setString(5, patient.getHomeAddress());
            ps.setObject(6, patient.getBirthWeightKg());
            ps.setString(7, patient.getLifestyleNotes());
            ps.setString(8, patient.getPositiveFoodAllergenHistory());
            ps.setString(9, patient.getPositiveInhaledAllergenHistory());
            ps.setString(10, patient.getAllergicDiseaseHistory());
            ps.setString(11, patient.getFamilyAllergyHistoryDegree1());
            ps.setString(12, patient.getFamilyAllergicDiseaseHistoryDegree1());
            ps.setString(13, patient.getFamilyAllergyHistoryDegree2());
            ps.setString(14, patient.getFamilyAllergicDiseaseHistoryDegree2());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("创建患者失败，没有行被影响。");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("创建患者失败，无法获取ID。");
                }
            }
        }
    }

    // 更新患者信息
    public boolean updatePatient(Patient patient) throws Exception {
        String sql = "UPDATE patients SET hospital_patient_id = ?, name = ?, gender = ?, date_of_birth = ?, " +
                "home_address = ?, birth_weight_kg = ?, lifestyle_notes = ?, positive_food_allergen_history = ?, " +
                "positive_inhaled_allergen_history = ?, allergic_disease_history = ?, family_allergy_history_degree1 = ?, " +
                "family_allergic_disease_history_degree1 = ?, family_allergy_history_degree2 = ?, " +
                "family_allergic_disease_history_degree2 = ? WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, patient.getHospitalPatientId());
            ps.setString(2, patient.getName());
            ps.setString(3, patient.getGender());
            ps.setDate(4, patient.getDateOfBirth() != null ? new java.sql.Date(patient.getDateOfBirth().getTime()) : null);
            ps.setString(5, patient.getHomeAddress());
            ps.setObject(6, patient.getBirthWeightKg());
            ps.setString(7, patient.getLifestyleNotes());
            ps.setString(8, patient.getPositiveFoodAllergenHistory());
            ps.setString(9, patient.getPositiveInhaledAllergenHistory());
            ps.setString(10, patient.getAllergicDiseaseHistory());
            ps.setString(11, patient.getFamilyAllergyHistoryDegree1());
            ps.setString(12, patient.getFamilyAllergicDiseaseHistoryDegree1());
            ps.setString(13, patient.getFamilyAllergyHistoryDegree2());
            ps.setString(14, patient.getFamilyAllergicDiseaseHistoryDegree2());
            ps.setInt(15, patient.getId());

            return ps.executeUpdate() > 0;
        }
    }

    // 删除患者（级联删除相关数据）
    public boolean deletePatient(int patientId) throws Exception {
        String sql = "DELETE FROM patients WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, patientId);
            return ps.executeUpdate() > 0;
        }
    }

    // 获取所有患者
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

    // 根据ID获取患者
    public Patient getPatientById(int id) {
        String sql = "SELECT * FROM patients WHERE id = ?";
        Patient patient = null;

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    patient = new Patient();
                    patient.setId(rs.getInt("id"));
                    patient.setHospitalPatientId(rs.getString("hospital_patient_id"));
                    patient.setName(rs.getString("name"));
                    patient.setGender(rs.getString("gender"));
                    patient.setDateOfBirth(rs.getDate("date_of_birth"));
                    patient.setHomeAddress(rs.getString("home_address"));
                    patient.setBirthWeightKg(rs.getDouble("birth_weight_kg"));
                    patient.setLifestyleNotes(rs.getString("lifestyle_notes"));
                    patient.setPositiveFoodAllergenHistory(rs.getString("positive_food_allergen_history"));
                    patient.setPositiveInhaledAllergenHistory(rs.getString("positive_inhaled_allergen_history"));
                    patient.setAllergicDiseaseHistory(rs.getString("allergic_disease_history"));
                    patient.setFamilyAllergyHistoryDegree1(rs.getString("family_allergy_history_degree1"));
                    patient.setFamilyAllergicDiseaseHistoryDegree1(rs.getString("family_allergic_disease_history_degree1"));
                    patient.setFamilyAllergyHistoryDegree2(rs.getString("family_allergy_history_degree2"));
                    patient.setFamilyAllergicDiseaseHistoryDegree2(rs.getString("family_allergic_disease_history_degree2"));
                    patient.setCreatedAt(rs.getTimestamp("created_at"));
                    patient.setUpdatedAt(rs.getTimestamp("updated_at"));
                }
            }
        } catch (SQLException e) {
            System.err.println("查询患者信息时发生SQL异常: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("查询患者信息失败", e);
        } catch (Exception e) {
            System.err.println("获取患者信息时发生未知异常: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("获取患者信息失败", e);
        }

        return patient;
    }

}