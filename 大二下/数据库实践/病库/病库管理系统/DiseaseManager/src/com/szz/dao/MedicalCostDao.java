package com.szz.dao;

import com.szz.model.MedicalCost;
import com.szz.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalCostDao {
    
    public List<MedicalCost> getMedicalCostsByVisitId(int visitId) throws Exception {
        List<MedicalCost> costs = new ArrayList<>();
        String sql = "SELECT * FROM medical_costs WHERE visit_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, visitId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                MedicalCost cost = new MedicalCost();
                cost.setId(rs.getInt("id"));
                cost.setVisitId(rs.getInt("visit_id"));
                cost.setCostCategory(rs.getString("cost_category"));
                cost.setCostAmount(rs.getBigDecimal("cost_amount"));
                cost.setCreatedAt(rs.getTimestamp("created_at"));
                cost.setUpdatedAt(rs.getTimestamp("updated_at"));
                costs.add(cost);
            }
        }

        return costs;
    }
}
