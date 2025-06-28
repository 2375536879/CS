package com.szz.view.patientView;

import com.szz.model.Clinical.ClinicalVisit;
import com.szz.model.Clinical.LabExamination;
import com.szz.service.Clinical.LabExaminationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class LabExaminationView extends JPanel {
    private final int visitId;
    private final int patientId;
    private final boolean isPatientView;
    private final LabExaminationService labExaminationService;
    private JTable examTable;
    private DefaultTableModel tableModel;

    // 构造函数：按就诊记录查询
    public LabExaminationView(int visitId) {
        this.visitId = visitId;
        this.patientId = -1;
        this.isPatientView = false;
        this.labExaminationService = new LabExaminationService();
        initializeUI();
        loadData();
    }

    // 构造函数：按患者ID查询（重载）
    public LabExaminationView(int patientId, boolean isPatientView) {
        this.visitId = -1;
        this.patientId = patientId;
        this.isPatientView = isPatientView;
        this.labExaminationService = new LabExaminationService();
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // 创建表格列
        String[] columns = {
            "检查项目名称", "检查日期", "检查值", "检查单位", "参考范围",
            "皮肤点刺试验", "血清特异性IgE", "血清总IgE", "外周血嗜酸性粒细胞计数",
            "胸部X线检查", "呼出气一氧化氮", "支气管舒张试验", "呼气峰值流量变异率",
            "锻炼激发试验", "支气管激发试验", "鼻黏膜激发试验", "调节性T细胞计数",
            "IgG4水平", "Th1细胞计数", "Th2细胞计数", "嗜酸性粒细胞阳离子蛋白水平"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        examTable = new JTable(tableModel);
        examTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        examTable.getTableHeader().setReorderingAllowed(false);

        // 设置表格列宽
        for (int i = 0; i < examTable.getColumnCount(); i++) {
            examTable.getColumnModel().getColumn(i).setPreferredWidth(150);
        }

        // 添加滚动面板
        JScrollPane scrollPane = new JScrollPane(examTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            if (isPatientView) {
                // 按患者ID查询：获取患者的所有就诊记录，然后获取每个就诊记录的实验室检查数据
                com.szz.service.Clinical.ClinicalVisitService visitService = new com.szz.service.Clinical.ClinicalVisitService();
                List<ClinicalVisit> visits = visitService.getVisitsByPatientId(patientId);

                for (ClinicalVisit visit : visits) {
                    List<LabExamination> examinations = labExaminationService.getLabExaminationsByVisitId(visit.getId());

                    for (LabExamination exam : examinations) {
                        Object[] rowData = {
                            exam.getExaminationName(),
                            exam.getExaminationDate() != null ? dateFormat.format(exam.getExaminationDate()) : "",
                            exam.getExaminationValue(),
                            exam.getExaminationUnit(),
                            exam.getReferenceRange(),
                            exam.getAllergySkinPrickTestDetails(),
                            exam.getAllergySerumSpecificIgeLevel(),
                            exam.getAllergySerumTotalIgeLevel(),
                            exam.getAllergyPeripheralEosinophilCount(),
                            exam.getAsthmaChestXrayFindings(),
                            exam.getAsthmaFenoTestValue(),
                            exam.getAsthmaBronchodilatorResponseTestResult(),
                            exam.getAsthmaPefVariabilityRate(),
                            exam.getAsthmaExerciseChallengeTestResult(),
                            exam.getAsthmaBronchialChallengeTestResult(),
                            exam.getArNasalMucosalChallengeTestResult(),
                            exam.getArRegulatoryTCellCount(),
                            exam.getArIgg4Level(),
                            exam.getArTh1CellCount(),
                            exam.getArTh2CellCount(),
                            exam.getArEosinophilCationicProteinLevel()
                        };
                        tableModel.addRow(rowData);
                    }
                }
            } else {
                // 按就诊记录ID查询
                List<LabExamination> examinations = labExaminationService.getLabExaminationsByVisitId(visitId);

                for (LabExamination exam : examinations) {
                    Object[] rowData = {
                        exam.getExaminationName(),
                        exam.getExaminationDate() != null ? dateFormat.format(exam.getExaminationDate()) : "",
                        exam.getExaminationValue(),
                        exam.getExaminationUnit(),
                        exam.getReferenceRange(),
                        exam.getAllergySkinPrickTestDetails(),
                        exam.getAllergySerumSpecificIgeLevel(),
                        exam.getAllergySerumTotalIgeLevel(),
                        exam.getAllergyPeripheralEosinophilCount(),
                        exam.getAsthmaChestXrayFindings(),
                        exam.getAsthmaFenoTestValue(),
                        exam.getAsthmaBronchodilatorResponseTestResult(),
                        exam.getAsthmaPefVariabilityRate(),
                        exam.getAsthmaExerciseChallengeTestResult(),
                        exam.getAsthmaBronchialChallengeTestResult(),
                        exam.getArNasalMucosalChallengeTestResult(),
                        exam.getArRegulatoryTCellCount(),
                        exam.getArIgg4Level(),
                        exam.getArTh1CellCount(),
                        exam.getArTh2CellCount(),
                        exam.getArEosinophilCationicProteinLevel()
                    };
                    tableModel.addRow(rowData);
                }
            }

            if (tableModel.getRowCount() == 0) {
                Object[] emptyRow = new Object[21];
                emptyRow[0] = "暂无实验室检查数据";
                for (int i = 1; i < 21; i++) {
                    emptyRow[i] = "";
                }
                tableModel.addRow(emptyRow);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "加载实验室检查数据时出错: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}