package com.szz.dao.Survey;

import com.szz.model.Survey.Contact;
import com.szz.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDao {

    public List<Contact> getContactsByPatientId(int patientId) throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM patient_contacts WHERE patient_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, patientId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Contact contact = new Contact();
                    contact.setId(rs.getInt("id"));
                    contact.setPatientId(rs.getInt("patient_id"));
                    contact.setContactName(rs.getString("contact_name"));
                    contact.setContactPhone(rs.getString("contact_phone"));
                    contact.setRelationship(rs.getString("relationship"));
                    contact.setCreatedAt(rs.getTimestamp("created_at"));
                    contact.setUpdatedAt(rs.getTimestamp("updated_at"));
                    contacts.add(contact);
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


        return contacts;
    }
}