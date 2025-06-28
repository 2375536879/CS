package com.szz.view.patientView;

import com.szz.model.Diagnosis;
import com.szz.service.DiagnosisService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class DiagnosisView extends JPanel {
    private final int visitId;
    private final int patientId;
    private final boolean isPatientView;
    private final DiagnosisService diagnosisService;
    private JTable diagnosisTable;
    private DefaultTableModel tableModel;

    // 构造函数：按就诊记录查询
    public DiagnosisView(int visitId) {
        this.visitId = visitId;
        this.patientId = -1;
        this.isPatientView = false;
        this.diagnosisService = new DiagnosisService();
        initializeUI();
        loadData();
    }

    // 构造函数：按患者ID查询（重载）
    public DiagnosisView(int patientId, boolean isPatientView) {
        this.visitId = -1;
        this.patientId = patientId;
        this.isPatientView = isPatientView;
        this.diagnosisService = new DiagnosisService();
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // 创建表格列
        String[] columns = {
            "疾病名称", "ICD-11编码", "严重程度", "诊断时间"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        diagnosisTable = new JTable(tableModel);
        diagnosisTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        diagnosisTable.getTableHeader().setReorderingAllowed(false);

        // 设置表格列宽
        diagnosisTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        diagnosisTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        diagnosisTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        diagnosisTable.getColumnModel().getColumn(3).setPreferredWidth(100);

        // 添加滚动面板
        JScrollPane scrollPane = new JScrollPane(diagnosisTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            if (isPatientView) {
                // 按患者ID查询：获取患者的所有就诊记录，然后获取每个就诊记录的诊断数据
                com.szz.service.ClinicalVisitService visitService = new com.szz.service.ClinicalVisitService();
                List<com.szz.model.ClinicalVisit> visits = visitService.getVisitsByPatientId(patientId);

                for (com.szz.model.ClinicalVisit visit : visits) {
                    List<Diagnosis> diagnoses = diagnosisService.getDiagnosesByVisitId(visit.getId());

                    for (Diagnosis diagnosis : diagnoses) {
                        Object[] rowData = {
                            diagnosis.getDiseaseName(),
                            diagnosis.getIcd11Code(),
                            diagnosis.getSeverity(),
                            diagnosis.getDiagnosisDate() != null ? dateFormat.format(diagnosis.getDiagnosisDate()) : ""
                        };
                        tableModel.addRow(rowData);
                    }
                }
            } else {
                // 按就诊记录ID查询
                List<Diagnosis> diagnoses = diagnosisService.getDiagnosesByVisitId(visitId);

                for (Diagnosis diagnosis : diagnoses) {
                    Object[] rowData = {
                        diagnosis.getDiseaseName(),
                        diagnosis.getIcd11Code(),
                        diagnosis.getSeverity(),
                        diagnosis.getDiagnosisDate() != null ? dateFormat.format(diagnosis.getDiagnosisDate()) : ""
                    };
                    tableModel.addRow(rowData);
                }
            }

            if (tableModel.getRowCount() == 0) {
                Object[] emptyRow = {"暂无诊断数据", "", "", ""};
                tableModel.addRow(emptyRow);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "加载诊断数据时出错: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}