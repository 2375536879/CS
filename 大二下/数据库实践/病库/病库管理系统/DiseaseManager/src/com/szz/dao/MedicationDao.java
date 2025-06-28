package com.szz.dao;

import com.szz.model.Medication;
import com.szz.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicationDao {
    
    public List<Medication> getMedicationsByVisitId(int visitId) throws Exception {
        List<Medication> medications = new ArrayList<>();
        String sql = "SELECT * FROM medications WHERE visit_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, visitId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Medication medication = new Medication();
                medication.setId(rs.getInt("id"));
                medication.setVisitId(rs.getInt("visit_id"));
                medication.setDrugName(rs.getString("drug_name"));
                medication.setDrugSpecification(rs.getString("drug_specification"));
                medication.setDosagePerAdministration(rs.getString("dosage_per_administration"));
                medication.setDrugUseDays(rs.getObject("drug_use_days", Integer.class));
                medication.setAdministrationFrequency(rs.getString("administration_frequency"));
                medication.setAdministrationRoute(rs.getString("administration_route"));
                medication.setAdministrationSite(rs.getString("administration_site"));
                medication.setTreatmentStartDate(rs.getDate("treatment_start_date"));
                medication.setTreatmentEndDate(rs.getDate("treatment_end_date"));
                medication.setMedicationGuidance(rs.getString("medication_guidance"));
                medication.setMedicationPrecautions(rs.getString("medication_precautions"));
                
                // 设置药物类型标记
                medication.setIsIcs(rs.getObject("is_ics", Boolean.class));
                medication.setIsLaba(rs.getObject("is_laba", Boolean.class));
                medication.setIsLtra(rs.getObject("is_ltra", Boolean.class));
                medication.setIsTheophylline(rs.getObject("is_theophylline", Boolean.class));
                medication.setIsSaba(rs.getObject("is_saba", Boolean.class));
                medication.setIsAntihistamine(rs.getObject("is_antihistamine", Boolean.class));
                medication.setIsCorticosteroidNasalOral(rs.getObject("is_corticosteroid_nasal_oral", Boolean.class));
                medication.setIsMastCellStabilizer(rs.getObject("is_mast_cell_stabilizer", Boolean.class));
                medication.setIsAnticholinergic(rs.getObject("is_anticholinergic", Boolean.class));
                medication.setIsTopicalCorticosteroid(rs.getObject("is_topical_corticosteroid", Boolean.class));
                medication.setIsCalcineurinInhibitor(rs.getObject("is_calcineurin_inhibitor", Boolean.class));
                medication.setIsFoodAllergyCorticosteroid(rs.getObject("is_food_allergy_corticosteroid", Boolean.class));
                
                medication.setCreatedAt(rs.getTimestamp("created_at"));
                medication.setUpdatedAt(rs.getTimestamp("updated_at"));
                medications.add(medication);
            }
        }

        return medications;
    }
}
