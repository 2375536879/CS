package com.szz.dao;

import com.szz.model.Diagnosis;
import ADMS.DatabaseManager;
import com.szz.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosisDao {
    public List<Diagnosis> getDiagnosesByVisitId(int visitId) throws SQLException {
        List<Diagnosis> diagnoses = new ArrayList<>();
        String sql = "SELECT * FROM diagnoses WHERE visit_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, visitId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Diagnosis diagnosis = new Diagnosis();
                diagnosis.setId(rs.getInt("id"));
                diagnosis.setVisitId(rs.getInt("visit_id"));
                diagnosis.setDiseaseName(rs.getString("disease_name"));
                diagnosis.setIcd11Code(rs.getString("icd_11_code"));
                diagnosis.setSeverity(rs.getString("severity"));
                diagnosis.setDiagnosisDate(rs.getDate("diagnosis_date"));
                diagnosis.setCreatedAt(rs.getTimestamp("created_at"));
                diagnosis.setUpdatedAt(rs.getTimestamp("updated_at"));
                diagnoses.add(diagnosis);
            }
        }catch (SQLException e) {
            System.err.println("更新患者信息时发生SQL异常: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("更新患者信息失败", e);
        } catch (Exception e) {
            System.err.println("更新患者信息时发生未知异常: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("更新患者信息失败", e);
        }


        return diagnoses;
    }
}