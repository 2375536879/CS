package com.szz.dao;

import com.szz.model.ExhaledNitricOxideTest;
import ADMS.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExhaledNitricOxideTestDao {
    public List<ExhaledNitricOxideTest> getExhaledNitricOxideTestsByVisitId(int visitId) throws SQLException {
        List<ExhaledNitricOxideTest> tests = new ArrayList<>();
        String sql = "SELECT * FROM exhaled_nitric_oxide_tests WHERE visit_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, visitId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ExhaledNitricOxideTest test = new ExhaledNitricOxideTest();
                test.setId(rs.getInt("id"));
                test.setVisitId(rs.getInt("visit_id"));
                test.setTestName(rs.getString("test_name"));
                test.setTestDate(rs.getDate("test_date"));
                test.setFenoValuePpb(rs.getInt("feno_value_ppb"));
                test.setCreatedAt(rs.getTimestamp("created_at"));
                test.setUpdatedAt(rs.getTimestamp("updated_at"));
                tests.add(test);
            }
        }

        return tests;
    }
}