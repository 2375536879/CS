package com.szz.view.patientView;

import com.szz.model.Survey.PastMedicationHistory;
import com.szz.service.Survey.PastMedicationHistoryService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class PastMedicationHistoryView extends JPanel {
    private final int patientId;
    private final PastMedicationHistoryService pastMedicationHistoryService;
    private JTable historyTable;
    private DefaultTableModel tableModel;

    public PastMedicationHistoryView(int patientId) {
        this.patientId = patientId;
        this.pastMedicationHistoryService = new PastMedicationHistoryService();
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // 创建表格模型
        String[] columnNames = {
            "药物名称", "使用天数", "使用原因", "大致开始日期", "大致结束日期", "创建时间", "更新时间"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 设置表格不可编辑
            }
        };

        // 创建表格
        historyTable = new JTable(tableModel);
        historyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // 设置列宽
        historyTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        historyTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        historyTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        historyTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        historyTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        historyTable.getColumnModel().getColumn(5).setPreferredWidth(150);
        historyTable.getColumnModel().getColumn(6).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(historyTable);
        add(scrollPane, BorderLayout.CENTER);

        // 添加说明标签
        JLabel infoLabel = new JLabel("既往用药史 - 患者级别数据");
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(infoLabel, BorderLayout.NORTH);
    }

    private void loadData() {
        try {
            List<PastMedicationHistory> histories = pastMedicationHistoryService.getPastMedicationHistoryByPatientId(patientId);
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // 清空现有数据
            tableModel.setRowCount(0);

            for (PastMedicationHistory history : histories) {
                Object[] rowData = {
                    history.getDrugName() != null ? history.getDrugName() : "",
                    history.getDrugUseDays() != null ? history.getDrugUseDays().toString() : "",
                    history.getReasonForUse() != null ? history.getReasonForUse() : "",
                    history.getApproximateStartDate() != null ? dateFormat.format(history.getApproximateStartDate()) : "",
                    history.getApproximateEndDate() != null ? dateFormat.format(history.getApproximateEndDate()) : "",
                    history.getCreatedAt() != null ? dateTimeFormat.format(history.getCreatedAt()) : "",
                    history.getUpdatedAt() != null ? dateTimeFormat.format(history.getUpdatedAt()) : ""
                };
                tableModel.addRow(rowData);
            }

            if (tableModel.getRowCount() == 0) {
                // 如果没有数据，添加一行提示信息
                Object[] emptyRow = {"暂无既往用药史", "", "", "", "", "", ""};
                tableModel.addRow(emptyRow);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "加载既往用药史时出错: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
