package com.szz.dao.Survey;

import com.szz.model.Survey.Insurance;
import com.szz.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InsuranceDao {

    public List<Insurance> getInsuranceByPatientId(int patientId) throws SQLException {
        List<Insurance> insuranceList = new ArrayList<>();
        String sql = "SELECT * FROM insurance_details WHERE patient_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, patientId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Insurance insurance = new Insurance();
                    insurance.setId(rs.getInt("id"));
                    insurance.setPatientId(rs.getInt("patient_id"));
                    insurance.setInsuranceType(rs.getString("insurance_type"));
                    insurance.setInsuranceNumber(rs.getString("insurance_number"));
                    insurance.setCreatedAt(rs.getTimestamp("created_at"));
                    insurance.setUpdatedAt(rs.getTimestamp("updated_at"));
                    insuranceList.add(insurance);
                }
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


        return insuranceList;
    }
}