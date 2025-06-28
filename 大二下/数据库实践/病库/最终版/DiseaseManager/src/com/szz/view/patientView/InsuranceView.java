package com.szz.view.patientView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import com.szz.util.JDBCUtil;

public class InsuranceView extends JPanel {
    private JTable insuranceTable;
    private DefaultTableModel tableModel;
    private int patientId;

    public InsuranceView(int patientId) {
        this.patientId = patientId;
        setLayout(new BorderLayout());
        initUI();
        loadInsuranceData();
    }

    private void initUI() {
        // 创建表格模型
        String[] columnNames = {"医保类型", "医保号", "创建时间", "更新时间"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 设置表格不可编辑
            }
        };

        // 创建表格并添加到滚动面板
        insuranceTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(insuranceTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadInsuranceData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "SELECT insurance_type, insurance_number, created_at, updated_at FROM insurance_details WHERE patient_id = ?";
        
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

            // 清空现有数据
            tableModel.setRowCount(0);

            // 添加新数据
            while (rs.next()) {
                Timestamp createdAt = rs.getTimestamp("created_at");
                Timestamp updatedAt = rs.getTimestamp("updated_at");
                Object[] row = {
                    rs.getString("insurance_type"),
                    rs.getString("insurance_number"),
                    createdAt != null ? dateFormat.format(createdAt) : "",
                    updatedAt != null ? dateFormat.format(updatedAt) : ""
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "加载医保信息时出错: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}