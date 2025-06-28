package com.szz.dao.Clinical;

import com.szz.model.Clinical.PulmonaryFunctionTest;
import ADMS.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PulmonaryFunctionTestDao {
    public List<PulmonaryFunctionTest> getPulmonaryFunctionTestsByVisitId(int visitId) throws SQLException {
        List<PulmonaryFunctionTest> tests = new ArrayList<>();
        String sql = "SELECT * FROM pulmonary_function_tests WHERE visit_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, visitId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PulmonaryFunctionTest test = new PulmonaryFunctionTest();
                test.setId(rs.getInt("id"));
                test.setVisitId(rs.getInt("visit_id"));
                test.setTestName(rs.getString("test_name"));
                test.setTestDate(rs.getDate("test_date"));
                test.setFev1Value(rs.getDouble("fev1_value"));
                test.setFvcValue(rs.getDouble("fvc_value"));
                test.setFev1FvcRatio(rs.getDouble("fev1_fvc_ratio"));
                test.setReportDetails(rs.getString("report_details"));
                test.setCreatedAt(rs.getTimestamp("created_at"));
                test.setUpdatedAt(rs.getTimestamp("updated_at"));
                tests.add(test);
            }
        }

        return tests;
    }
}