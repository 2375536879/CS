package com.szz.view.patientView;

import com.szz.model.Clinical.ClinicalVisit;
import com.szz.model.Clinical.MedicalCost;
import com.szz.service.Clinical.MedicalCostService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

public class MedicalCostView extends JPanel {
    private final int visitId;
    private final int patientId;
    private final boolean isPatientView;
    private final MedicalCostService medicalCostService;
    private JTable costTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;

    // 构造函数：按就诊记录查询
    public MedicalCostView(int visitId) {
        this.visitId = visitId;
        this.patientId = -1;
        this.isPatientView = false;
        this.medicalCostService = new MedicalCostService();
        initializeUI();
        loadData();
    }

    // 构造函数：按患者ID查询（重载）
    public MedicalCostView(int patientId, boolean isPatientView) {
        this.visitId = -1;
        this.patientId = patientId;
        this.isPatientView = isPatientView;
        this.medicalCostService = new MedicalCostService();
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // 创建表格模型
        String[] columnNames = {
            "费用类别", "费用金额(元)", "创建时间", "更新时间"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 设置表格不可编辑
            }
        };

        // 创建表格
        costTable = new JTable(tableModel);
        costTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // 设置列宽
        costTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        costTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        costTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        costTable.getColumnModel().getColumn(3).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(costTable);
        add(scrollPane, BorderLayout.CENTER);

        // 创建顶部面板
        JPanel topPanel = new JPanel(new BorderLayout());
        
        // 添加说明标签
        JLabel infoLabel = new JLabel("医疗费用 - 根据就诊记录显示");
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        topPanel.add(infoLabel, BorderLayout.NORTH);
        
        // 添加总费用标签
        totalLabel = new JLabel("总费用: ¥0.00");
        totalLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        totalLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        totalLabel.setForeground(new Color(0, 100, 0));
        topPanel.add(totalLabel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
    }

    private void loadData() {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BigDecimal totalCost = BigDecimal.ZERO;
        
        try {
            if (isPatientView) {
                // 按患者ID查询：获取患者的所有就诊记录，然后获取每个就诊记录的费用数据
                com.szz.service.Clinical.ClinicalVisitService visitService = new com.szz.service.Clinical.ClinicalVisitService();
                List<ClinicalVisit> visits = visitService.getVisitsByPatientId(patientId);
                
                for (ClinicalVisit visit : visits) {
                    List<MedicalCost> costs = medicalCostService.getMedicalCostsByVisitId(visit.getId());
                    
                    for (MedicalCost cost : costs) {
                        Object[] rowData = {
                            cost.getCostCategory() != null ? cost.getCostCategory() : "",
                            cost.getCostAmount() != null ? String.format("%.2f", cost.getCostAmount()) : "0.00",
                            cost.getCreatedAt() != null ? dateTimeFormat.format(cost.getCreatedAt()) : "",
                            cost.getUpdatedAt() != null ? dateTimeFormat.format(cost.getUpdatedAt()) : ""
                        };
                        tableModel.addRow(rowData);
                        
                        // 累计总费用
                        if (cost.getCostAmount() != null) {
                            totalCost = totalCost.add(cost.getCostAmount());
                        }
                    }
                }
            } else {
                // 按就诊记录ID查询
                List<MedicalCost> costs = medicalCostService.getMedicalCostsByVisitId(visitId);
                
                for (MedicalCost cost : costs) {
                    Object[] rowData = {
                        cost.getCostCategory() != null ? cost.getCostCategory() : "",
                        cost.getCostAmount() != null ? String.format("%.2f", cost.getCostAmount()) : "0.00",
                        cost.getCreatedAt() != null ? dateTimeFormat.format(cost.getCreatedAt()) : "",
                        cost.getUpdatedAt() != null ? dateTimeFormat.format(cost.getUpdatedAt()) : ""
                    };
                    tableModel.addRow(rowData);
                    
                    // 累计总费用
                    if (cost.getCostAmount() != null) {
                        totalCost = totalCost.add(cost.getCostAmount());
                    }
                }
            }

            if (tableModel.getRowCount() == 0) {
                Object[] emptyRow = {"暂无费用记录", "0.00", "", ""};
                tableModel.addRow(emptyRow);
            }
            
            // 更新总费用显示
            totalLabel.setText(String.format("总费用: ¥%.2f", totalCost));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "加载医疗费用数据时出错: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
