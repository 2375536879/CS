package com.szz.dao.Survey;

import com.szz.model.Survey.PastMedicationHistory;
import com.szz.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PastMedicationHistoryDao {
    
    public List<PastMedicationHistory> getPastMedicationHistoryByPatientId(int patientId) throws Exception {
        List<PastMedicationHistory> histories = new ArrayList<>();
        String sql = "SELECT * FROM past_medication_history WHERE patient_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PastMedicationHistory history = new PastMedicationHistory();
                history.setId(rs.getInt("id"));
                history.setPatientId(rs.getInt("patient_id"));
                history.setDrugName(rs.getString("drug_name"));
                history.setDrugUseDays(rs.getObject("drug_use_days", Integer.class));
                history.setReasonForUse(rs.getString("reason_for_use"));
                history.setApproximateStartDate(rs.getDate("approximate_start_date"));
                history.setApproximateEndDate(rs.getDate("approximate_end_date"));
                history.setCreatedAt(rs.getTimestamp("created_at"));
                history.setUpdatedAt(rs.getTimestamp("updated_at"));
                histories.add(history);
            }
        }

        return histories;
    }
}
