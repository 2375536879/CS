package com.szz.dao.Survey;

import com.szz.model.Survey.OtherAuxiliaryTreatment;
import com.szz.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OtherAuxiliaryTreatmentDao {
    
    public List<OtherAuxiliaryTreatment> getOtherAuxiliaryTreatmentsByVisitId(int visitId) throws Exception {
        List<OtherAuxiliaryTreatment> treatments = new ArrayList<>();
        String sql = "SELECT * FROM other_auxiliary_treatments WHERE visit_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, visitId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OtherAuxiliaryTreatment treatment = new OtherAuxiliaryTreatment();
                treatment.setId(rs.getInt("id"));
                treatment.setVisitId(rs.getInt("visit_id"));
                treatment.setTreatmentMethod(rs.getString("treatment_method"));
                treatment.setStartDate(rs.getDate("start_date"));
                treatment.setEndDate(rs.getDate("end_date"));
                treatment.setPrecautions(rs.getString("precautions"));
                treatment.setIsAllergenSpecificImmunotherapy(rs.getObject("is_allergen_specific_immunotherapy", Boolean.class));
                treatment.setIsAntiIgeAntibodyTherapy(rs.getObject("is_anti_ige_antibody_therapy", Boolean.class));
                treatment.setCreatedAt(rs.getTimestamp("created_at"));
                treatment.setUpdatedAt(rs.getTimestamp("updated_at"));
                treatments.add(treatment);
            }
        }

        return treatments;
    }
}
