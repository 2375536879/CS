package com.szz.view.patientView;

import com.szz.model.OtherAuxiliaryTreatment;
import com.szz.service.OtherAuxiliaryTreatmentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class OtherAuxiliaryTreatmentView extends JPanel {
    private final int visitId;
    private final int patientId;
    private final boolean isPatientView;
    private final OtherAuxiliaryTreatmentService otherAuxiliaryTreatmentService;
    private JTable treatmentTable;
    private DefaultTableModel tableModel;
    private JTextArea precautionsArea;

    // 构造函数：按就诊记录查询
    public OtherAuxiliaryTreatmentView(int visitId) {
        this.visitId = visitId;
        this.patientId = -1;
        this.isPatientView = false;
        this.otherAuxiliaryTreatmentService = new OtherAuxiliaryTreatmentService();
        initializeUI();
        loadData();
    }

    // 构造函数：按患者ID查询（重载）
    public OtherAuxiliaryTreatmentView(int patientId, boolean isPatientView) {
        this.visitId = -1;
        this.patientId = patientId;
        this.isPatientView = isPatientView;
        this.otherAuxiliaryTreatmentService = new OtherAuxiliaryTreatmentService();
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // 创建表格模型
        String[] columnNames = {
            "治疗方法", "开始时间", "结束时间", "过敏原特异性免疫治疗", "抗IgE抗体治疗"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 设置表格不可编辑
            }
        };

        // 创建表格
        treatmentTable = new JTable(tableModel);
        treatmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // 设置列宽
        treatmentTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        treatmentTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        treatmentTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        treatmentTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        treatmentTable.getColumnModel().getColumn(4).setPreferredWidth(150);

        // 添加选择监听器
        treatmentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updatePrecautionsArea();
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(treatmentTable);
        
        // 创建注意事项面板
        JPanel precautionsPanel = new JPanel(new BorderLayout());
        precautionsPanel.setBorder(BorderFactory.createTitledBorder("注意事项"));
        
        precautionsArea = new JTextArea();
        precautionsArea.setEditable(false);
        precautionsArea.setWrapStyleWord(true);
        precautionsArea.setLineWrap(true);
        
        JScrollPane precautionsScrollPane = new JScrollPane(precautionsArea);
        precautionsScrollPane.setPreferredSize(new Dimension(500, 120));
        precautionsPanel.add(precautionsScrollPane, BorderLayout.CENTER);

        // 创建分割面板
        JSplitPane splitPane = new JSplitPane(
            JSplitPane.VERTICAL_SPLIT,
            tableScrollPane,
            precautionsPanel
        );
        splitPane.setResizeWeight(0.7);

        add(splitPane, BorderLayout.CENTER);

        // 添加说明标签
        JLabel infoLabel = new JLabel("其他辅助治疗 - 根据就诊记录显示");
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(infoLabel, BorderLayout.NORTH);
    }

    private void loadData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            if (isPatientView) {
                // 按患者ID查询：获取患者的所有就诊记录，然后获取每个就诊记录的辅助治疗数据
                com.szz.service.ClinicalVisitService visitService = new com.szz.service.ClinicalVisitService();
                List<com.szz.model.ClinicalVisit> visits = visitService.getVisitsByPatientId(patientId);
                
                for (com.szz.model.ClinicalVisit visit : visits) {
                    List<OtherAuxiliaryTreatment> treatments = otherAuxiliaryTreatmentService.getOtherAuxiliaryTreatmentsByVisitId(visit.getId());
                    
                    for (OtherAuxiliaryTreatment treatment : treatments) {
                        Object[] rowData = {
                            treatment.getTreatmentMethod() != null ? treatment.getTreatmentMethod() : "",
                            treatment.getStartDate() != null ? dateFormat.format(treatment.getStartDate()) : "",
                            treatment.getEndDate() != null ? dateFormat.format(treatment.getEndDate()) : "",
                            Boolean.TRUE.equals(treatment.getIsAllergenSpecificImmunotherapy()) ? "是" : "否",
                            Boolean.TRUE.equals(treatment.getIsAntiIgeAntibodyTherapy()) ? "是" : "否"
                        };
                        tableModel.addRow(rowData);
                    }
                }
            } else {
                // 按就诊记录ID查询
                List<OtherAuxiliaryTreatment> treatments = otherAuxiliaryTreatmentService.getOtherAuxiliaryTreatmentsByVisitId(visitId);
                
                for (OtherAuxiliaryTreatment treatment : treatments) {
                    Object[] rowData = {
                        treatment.getTreatmentMethod() != null ? treatment.getTreatmentMethod() : "",
                        treatment.getStartDate() != null ? dateFormat.format(treatment.getStartDate()) : "",
                        treatment.getEndDate() != null ? dateFormat.format(treatment.getEndDate()) : "",
                        Boolean.TRUE.equals(treatment.getIsAllergenSpecificImmunotherapy()) ? "是" : "否",
                        Boolean.TRUE.equals(treatment.getIsAntiIgeAntibodyTherapy()) ? "是" : "否"
                    };
                    tableModel.addRow(rowData);
                }
            }

            if (tableModel.getRowCount() == 0) {
                Object[] emptyRow = {"暂无其他辅助治疗记录", "", "", "", ""};
                tableModel.addRow(emptyRow);
            } else {
                // 如果有数据，选择第一行
                treatmentTable.setRowSelectionInterval(0, 0);
                updatePrecautionsArea();
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "加载其他辅助治疗数据时出错: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updatePrecautionsArea() {
        int selectedRow = treatmentTable.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {
            try {
                // 获取所有辅助治疗记录
                List<OtherAuxiliaryTreatment> allTreatments = new java.util.ArrayList<>();
                
                if (isPatientView) {
                    // 按患者ID查询所有就诊记录的辅助治疗数据
                    com.szz.service.ClinicalVisitService visitService = new com.szz.service.ClinicalVisitService();
                    List<com.szz.model.ClinicalVisit> visits = visitService.getVisitsByPatientId(patientId);
                    
                    for (com.szz.model.ClinicalVisit visit : visits) {
                        List<OtherAuxiliaryTreatment> treatments = otherAuxiliaryTreatmentService.getOtherAuxiliaryTreatmentsByVisitId(visit.getId());
                        allTreatments.addAll(treatments);
                    }
                } else {
                    // 按就诊记录ID查询
                    allTreatments = otherAuxiliaryTreatmentService.getOtherAuxiliaryTreatmentsByVisitId(visitId);
                }
                
                if (selectedRow < allTreatments.size()) {
                    OtherAuxiliaryTreatment treatment = allTreatments.get(selectedRow);
                    precautionsArea.setText(treatment.getPrecautions() != null ? treatment.getPrecautions() : "暂无注意事项");
                    precautionsArea.setCaretPosition(0);
                } else {
                    precautionsArea.setText("暂无注意事项");
                }
            } catch (Exception e) {
                precautionsArea.setText("加载注意事项时出错: " + e.getMessage());
            }
        } else {
            precautionsArea.setText("");
        }
    }
}
