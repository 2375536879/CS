package com.szz.view.patientView;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import com.szz.util.JDBCUtil;

public class ClinicalVisitView extends JPanel {
    private JTable visitTable;
    private DefaultTableModel tableModel;
    private int patientId;
    private Map<Integer, Integer> rowToVisitIdMap = new HashMap<>(); // 存储行索引到就诊ID的映射

    public ClinicalVisitView(int patientId) {
        this.patientId = patientId;
        setLayout(new BorderLayout());
        initUI();
        loadVisitData();
    }

    private void initUI() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 创建标题面板
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel titleLabel = new JLabel("就诊记录");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        titleLabel.setForeground(new Color(51, 51, 51));
        titlePanel.add(titleLabel, BorderLayout.WEST);

        add(titlePanel, BorderLayout.NORTH);

        // 创建表格模型
        String[] columnNames = {
                "就诊时间", "身高(cm)", "体重(kg)",
                "医疗服务者", "职称", "机构名称", "机构地址"
        };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 设置表格不可编辑
            }
        };

        // 创建表格
        visitTable = new JTable(tableModel);
        visitTable.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        visitTable.setRowHeight(35);
        visitTable.setGridColor(new Color(230, 230, 230));
        visitTable.setSelectionBackground(new Color(240, 248, 255));
        visitTable.setSelectionForeground(Color.BLACK);
        visitTable.setShowGrid(true);
        visitTable.setIntercellSpacing(new Dimension(1, 1));

        // 设置表头样式
        visitTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        visitTable.getTableHeader().setBackground(new Color(248, 249, 250));
        visitTable.getTableHeader().setForeground(new Color(51, 51, 51));
        visitTable.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)));

        // 设置列宽
        visitTable.getColumnModel().getColumn(0).setPreferredWidth(120); // 就诊时间
        visitTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // 身高
        visitTable.getColumnModel().getColumn(2).setPreferredWidth(80);  // 体重
        visitTable.getColumnModel().getColumn(3).setPreferredWidth(100); // 医疗服务者
        visitTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // 职称
        visitTable.getColumnModel().getColumn(5).setPreferredWidth(150); // 机构名称
        visitTable.getColumnModel().getColumn(6).setPreferredWidth(200); // 机构地址

        // 创建滚动面板
        JScrollPane scrollPane = new JScrollPane(visitTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JButton viewDetailsButton = new JButton("查看详细症状和体征");
        viewDetailsButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        viewDetailsButton.setPreferredSize(new Dimension(150, 35));
        viewDetailsButton.setBackground(new Color(51, 122, 183));
        viewDetailsButton.setForeground(Color.WHITE);
        viewDetailsButton.setFocusPainted(false);
        viewDetailsButton.setBorder(BorderFactory.createEmptyBorder());
        viewDetailsButton.addActionListener(e -> showSymptomDetails());

        buttonPanel.add(viewDetailsButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadVisitData() {
        String sql = "SELECT id, visit_date, height_cm, weight_kg, healthcare_provider_name, " +
                "healthcare_provider_title, institution_name, institution_address " +
                "FROM clinical_visits WHERE patient_id = ? ORDER BY visit_date DESC";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

            // 清空现有数据和映射
            tableModel.setRowCount(0);
            rowToVisitIdMap.clear();

            // 添加新数据
            int rowIndex = 0;
            while (rs.next()) {
                Object[] row = {
                        rs.getTimestamp("visit_date"),
                        rs.getDouble("height_cm"),
                        rs.getDouble("weight_kg"),
                        rs.getString("healthcare_provider_name"),
                        rs.getString("healthcare_provider_title"),
                        rs.getString("institution_name"),
                        rs.getString("institution_address")
                };
                tableModel.addRow(row);

                // 存储就诊ID到映射
                rowToVisitIdMap.put(rowIndex, rs.getInt("id"));
                rowIndex++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "加载就诊记录时出错: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "加载就诊记录时发生异常: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showSymptomDetails() {
        int selectedRow = visitTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "请先选择一条就诊记录",
                    "提示",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // 获取选中的就诊记录ID
        Integer visitId = rowToVisitIdMap.get(selectedRow);
        if (visitId == null) {
            JOptionPane.showMessageDialog(this,
                    "无法获取就诊记录ID",
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = JDBCUtil.getConnection()) {
            // 获取症状体征数据
            String symptomSql = "SELECT * FROM visit_symptoms_signs WHERE visit_id = ?";
            PreparedStatement symptomPs = conn.prepareStatement(symptomSql);
            symptomPs.setInt(1, visitId);
            ResultSet symptomRs = symptomPs.executeQuery();

            // 检查是否有数据
            if (symptomRs.next()) {
                // 使用新的症状和体征详情对话框
                SymptomsSignsDetailDialog dialog = new SymptomsSignsDetailDialog(
                    SwingUtilities.getWindowAncestor(this),
                    symptomRs
                );
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "该就诊记录没有症状和体征数据",
                        "提示",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "加载症状详情时出错: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "加载症状详情时发生异常: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }





}