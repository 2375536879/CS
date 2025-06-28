package com.szz.view.patientView;

import com.szz.model.Clinical.ClinicalVisit;
import com.szz.model.Clinical.Medication;
import com.szz.service.Clinical.MedicationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class MedicationView extends JPanel {
    private final int visitId;
    private final int patientId;
    private final boolean isPatientView;
    private final MedicationService medicationService;
    private JTable medicationTable;
    private DefaultTableModel tableModel;
    private JTextArea detailsArea;

    // 构造函数：按就诊记录查询
    public MedicationView(int visitId) {
        this.visitId = visitId;
        this.patientId = -1;
        this.isPatientView = false;
        this.medicationService = new MedicationService();
        initializeUI();
        loadData();
    }

    // 构造函数：按患者ID查询（重载）
    public MedicationView(int patientId, boolean isPatientView) {
        this.visitId = -1;
        this.patientId = patientId;
        this.isPatientView = isPatientView;
        this.medicationService = new MedicationService();
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // 创建表格模型
        String[] columnNames = {
            "药物名称", "规格", "剂量", "使用天数", "给药频率", 
            "给药途径", "治疗开始日期", "治疗结束日期", "药物类型"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 设置表格不可编辑
            }
        };

        // 创建表格
        medicationTable = new JTable(tableModel);
        medicationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // 设置列宽
        medicationTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        medicationTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        medicationTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        medicationTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        medicationTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        medicationTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        medicationTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        medicationTable.getColumnModel().getColumn(7).setPreferredWidth(100);
        medicationTable.getColumnModel().getColumn(8).setPreferredWidth(150);

        // 添加选择监听器
        medicationTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateDetailsArea();
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(medicationTable);
        
        // 创建详情面板
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("用药详情"));
        
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setWrapStyleWord(true);
        detailsArea.setLineWrap(true);
        detailsArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        JScrollPane detailsScrollPane = new JScrollPane(detailsArea);
        detailsScrollPane.setPreferredSize(new Dimension(500, 150));
        detailsPanel.add(detailsScrollPane, BorderLayout.CENTER);

        // 创建分割面板
        JSplitPane splitPane = new JSplitPane(
            JSplitPane.VERTICAL_SPLIT,
            tableScrollPane,
            detailsPanel
        );
        splitPane.setResizeWeight(0.7);

        add(splitPane, BorderLayout.CENTER);

        // 添加说明标签
        JLabel infoLabel = new JLabel("用药记录 - 根据就诊记录显示");
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(infoLabel, BorderLayout.NORTH);
    }

    private void loadData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            if (isPatientView) {
                // 按患者ID查询：获取患者的所有就诊记录，然后获取每个就诊记录的用药数据
                com.szz.service.Clinical.ClinicalVisitService visitService = new com.szz.service.Clinical.ClinicalVisitService();
                List<ClinicalVisit> visits = visitService.getVisitsByPatientId(patientId);
                
                for (ClinicalVisit visit : visits) {
                    List<Medication> medications = medicationService.getMedicationsByVisitId(visit.getId());
                    
                    for (Medication medication : medications) {
                        Object[] rowData = {
                            medication.getDrugName() != null ? medication.getDrugName() : "",
                            medication.getDrugSpecification() != null ? medication.getDrugSpecification() : "",
                            medication.getDosagePerAdministration() != null ? medication.getDosagePerAdministration() : "",
                            medication.getDrugUseDays() != null ? medication.getDrugUseDays().toString() : "",
                            medication.getAdministrationFrequency() != null ? medication.getAdministrationFrequency() : "",
                            medication.getAdministrationRoute() != null ? medication.getAdministrationRoute() : "",
                            medication.getTreatmentStartDate() != null ? dateFormat.format(medication.getTreatmentStartDate()) : "",
                            medication.getTreatmentEndDate() != null ? dateFormat.format(medication.getTreatmentEndDate()) : "",
                            getMedicationTypeString(medication)
                        };
                        tableModel.addRow(rowData);
                    }
                }
            } else {
                // 按就诊记录ID查询
                List<Medication> medications = medicationService.getMedicationsByVisitId(visitId);
                
                for (Medication medication : medications) {
                    Object[] rowData = {
                        medication.getDrugName() != null ? medication.getDrugName() : "",
                        medication.getDrugSpecification() != null ? medication.getDrugSpecification() : "",
                        medication.getDosagePerAdministration() != null ? medication.getDosagePerAdministration() : "",
                        medication.getDrugUseDays() != null ? medication.getDrugUseDays().toString() : "",
                        medication.getAdministrationFrequency() != null ? medication.getAdministrationFrequency() : "",
                        medication.getAdministrationRoute() != null ? medication.getAdministrationRoute() : "",
                        medication.getTreatmentStartDate() != null ? dateFormat.format(medication.getTreatmentStartDate()) : "",
                        medication.getTreatmentEndDate() != null ? dateFormat.format(medication.getTreatmentEndDate()) : "",
                        getMedicationTypeString(medication)
                    };
                    tableModel.addRow(rowData);
                }
            }

            if (tableModel.getRowCount() == 0) {
                Object[] emptyRow = {"暂无用药记录", "", "", "", "", "", "", "", ""};
                tableModel.addRow(emptyRow);
            } else {
                // 如果有数据，选择第一行
                medicationTable.setRowSelectionInterval(0, 0);
                updateDetailsArea();
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "加载用药记录时出错: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private String getMedicationTypeString(Medication medication) {
        StringBuilder types = new StringBuilder();
        
        if (Boolean.TRUE.equals(medication.getIsIcs())) types.append("ICS ");
        if (Boolean.TRUE.equals(medication.getIsLaba())) types.append("LABA ");
        if (Boolean.TRUE.equals(medication.getIsLtra())) types.append("LTRA ");
        if (Boolean.TRUE.equals(medication.getIsTheophylline())) types.append("茶碱 ");
        if (Boolean.TRUE.equals(medication.getIsSaba())) types.append("SABA ");
        if (Boolean.TRUE.equals(medication.getIsAntihistamine())) types.append("抗组胺 ");
        if (Boolean.TRUE.equals(medication.getIsCorticosteroidNasalOral())) types.append("糖皮质激素 ");
        if (Boolean.TRUE.equals(medication.getIsMastCellStabilizer())) types.append("肥大细胞稳定剂 ");
        if (Boolean.TRUE.equals(medication.getIsAnticholinergic())) types.append("抗胆碱 ");
        if (Boolean.TRUE.equals(medication.getIsTopicalCorticosteroid())) types.append("外用激素 ");
        if (Boolean.TRUE.equals(medication.getIsCalcineurinInhibitor())) types.append("钙调抑制剂 ");
        if (Boolean.TRUE.equals(medication.getIsFoodAllergyCorticosteroid())) types.append("食物过敏激素 ");
        
        return types.toString().trim();
    }

    private void updateDetailsArea() {
        int selectedRow = medicationTable.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {
            try {
                // 获取所有用药记录
                List<Medication> allMedications = new java.util.ArrayList<>();
                
                if (isPatientView) {
                    // 按患者ID查询所有就诊记录的用药数据
                    com.szz.service.Clinical.ClinicalVisitService visitService = new com.szz.service.Clinical.ClinicalVisitService();
                    List<ClinicalVisit> visits = visitService.getVisitsByPatientId(patientId);
                    
                    for (ClinicalVisit visit : visits) {
                        List<Medication> medications = medicationService.getMedicationsByVisitId(visit.getId());
                        allMedications.addAll(medications);
                    }
                } else {
                    // 按就诊记录ID查询
                    allMedications = medicationService.getMedicationsByVisitId(visitId);
                }
                
                if (selectedRow < allMedications.size()) {
                    Medication medication = allMedications.get(selectedRow);
                    StringBuilder details = new StringBuilder();
                    
                    details.append("药物名称: ").append(medication.getDrugName() != null ? medication.getDrugName() : "").append("\n");
                    details.append("药物规格: ").append(medication.getDrugSpecification() != null ? medication.getDrugSpecification() : "").append("\n");
                    details.append("给药部位: ").append(medication.getAdministrationSite() != null ? medication.getAdministrationSite() : "").append("\n");
                    details.append("用药指导: ").append(medication.getMedicationGuidance() != null ? medication.getMedicationGuidance() : "").append("\n");
                    details.append("注意事项: ").append(medication.getMedicationPrecautions() != null ? medication.getMedicationPrecautions() : "").append("\n");
                    
                    detailsArea.setText(details.toString());
                    detailsArea.setCaretPosition(0);
                } else {
                    detailsArea.setText("暂无详细信息");
                }
            } catch (Exception e) {
                detailsArea.setText("加载详细信息时出错: " + e.getMessage());
            }
        } else {
            detailsArea.setText("");
        }
    }
}
