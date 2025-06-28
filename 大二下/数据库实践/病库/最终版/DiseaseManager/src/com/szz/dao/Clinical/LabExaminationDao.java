package com.szz.dao.Clinical;

import com.szz.model.Clinical.LabExamination;
import ADMS.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LabExaminationDao {
    public List<LabExamination> getLabExaminationsByVisitId(int visitId) throws SQLException {
        List<LabExamination> examinations = new ArrayList<>();
        String sql = "SELECT * FROM lab_examinations WHERE visit_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, visitId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LabExamination exam = new LabExamination();
                exam.setId(rs.getInt("id"));
                exam.setVisitId(rs.getInt("visit_id"));
                exam.setExaminationName(rs.getString("examination_name"));
                exam.setExaminationDate(rs.getDate("examination_date"));
                exam.setExaminationValue(rs.getString("examination_value"));
                exam.setExaminationUnit(rs.getString("examination_unit"));
                exam.setReferenceRange(rs.getString("reference_range"));
                exam.setNotes(rs.getString("notes"));
                exam.setAllergySkinPrickTestDetails(rs.getString("allergy_skin_prick_test_details"));
                exam.setAllergySerumSpecificIgeLevel(rs.getString("allergy_serum_specific_ige_level"));
                exam.setAllergySerumTotalIgeLevel(rs.getString("allergy_serum_total_ige_level"));
                exam.setAllergyPeripheralEosinophilCount(rs.getString("allergy_peripheral_eosinophil_count"));
                exam.setAsthmaChestXrayFindings(rs.getString("asthma_chest_xray_findings"));
                exam.setAsthmaFenoTestValue(rs.getString("asthma_feno_test_value"));
                exam.setAsthmaBronchodilatorResponseTestResult(rs.getString("asthma_bronchodilator_response_test_result"));
                exam.setAsthmaPefVariabilityRate(rs.getString("asthma_pef_variability_rate"));
                exam.setAsthmaExerciseChallengeTestResult(rs.getString("asthma_exercise_challenge_test_result"));
                exam.setAsthmaBronchialChallengeTestResult(rs.getString("asthma_bronchial_challenge_test_result"));
                exam.setArNasalMucosalChallengeTestResult(rs.getString("ar_nasal_mucosal_challenge_test_result"));
                exam.setArRegulatoryTCellCount(rs.getString("ar_regulatory_t_cell_count"));
                exam.setArIgg4Level(rs.getString("ar_igg4_level"));
                exam.setArTh1CellCount(rs.getString("ar_th1_cell_count"));
                exam.setArTh2CellCount(rs.getString("ar_th2_cell_count"));
                exam.setArEosinophilCationicProteinLevel(rs.getString("ar_eosinophil_cationic_protein_level"));
                exam.setCreatedAt(rs.getTimestamp("created_at"));
                exam.setUpdatedAt(rs.getTimestamp("updated_at"));
                examinations.add(exam);
            }
        }

        return examinations;
    }
}