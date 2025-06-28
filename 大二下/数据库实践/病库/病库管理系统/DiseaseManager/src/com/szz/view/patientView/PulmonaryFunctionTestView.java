package com.szz.view.patientView;

import com.szz.model.PulmonaryFunctionTest;
import com.szz.service.PulmonaryFunctionTestService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class PulmonaryFunctionTestView extends JPanel {
    private final int visitId;
    private final int patientId;
    private final boolean isPatientView;
    private final PulmonaryFunctionTestService pulmonaryFunctionTestService;
    private JTable testTable;
    private DefaultTableModel tableModel;

    // 构造函数：按就诊记录查询
    public PulmonaryFunctionTestView(int visitId) {
        this.visitId = visitId;
        this.patientId = -1;
        this.isPatientView = false;
        this.pulmonaryFunctionTestService = new PulmonaryFunctionTestService();
        initializeUI();
        loadData();
    }

    // 构造函数：按患者ID查询（重载）
    public PulmonaryFunctionTestView(int patientId, boolean isPatientView) {
        this.visitId = -1;
        this.patientId = patientId;
        this.isPatientView = isPatientView;
        this.pulmonaryFunctionTestService = new PulmonaryFunctionTestService();
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // 创建表格列
        String[] columns = {
            "检查名称", "检查日期", "FEV1值", "FVC值", "FEV1/FVC比率", "检查报告详情"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        testTable = new JTable(tableModel);
        testTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        testTable.getTableHeader().setReorderingAllowed(false);

        // 设置表格列宽
        testTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        testTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        testTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        testTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        testTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        testTable.getColumnModel().getColumn(5).setPreferredWidth(300);

        // 添加滚动面板
        JScrollPane scrollPane = new JScrollPane(testTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            if (isPatientView) {
                // 按患者ID查询：获取患者的所有就诊记录，然后获取每个就诊记录的肺功能检查数据
                com.szz.service.ClinicalVisitService visitService = new com.szz.service.ClinicalVisitService();
                List<com.szz.model.ClinicalVisit> visits = visitService.getVisitsByPatientId(patientId);

                for (com.szz.model.ClinicalVisit visit : visits) {
                    List<PulmonaryFunctionTest> tests = pulmonaryFunctionTestService.getPulmonaryFunctionTestsByVisitId(visit.getId());

                    for (PulmonaryFunctionTest test : tests) {
                        Object[] rowData = {
                            test.getTestName(),
                            test.getTestDate() != null ? dateFormat.format(test.getTestDate()) : "",
                            test.getFev1Value(),
                            test.getFvcValue(),
                            test.getFev1FvcRatio(),
                            test.getReportDetails()
                        };
                        tableModel.addRow(rowData);
                    }
                }
            } else {
                // 按就诊记录ID查询
                List<PulmonaryFunctionTest> tests = pulmonaryFunctionTestService.getPulmonaryFunctionTestsByVisitId(visitId);

                for (PulmonaryFunctionTest test : tests) {
                    Object[] rowData = {
                        test.getTestName(),
                        test.getTestDate() != null ? dateFormat.format(test.getTestDate()) : "",
                        test.getFev1Value(),
                        test.getFvcValue(),
                        test.getFev1FvcRatio(),
                        test.getReportDetails()
                    };
                    tableModel.addRow(rowData);
                }
            }

            if (tableModel.getRowCount() == 0) {
                Object[] emptyRow = {"暂无肺功能检查数据", "", "", "", "", ""};
                tableModel.addRow(emptyRow);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "加载肺功能检查数据时出错: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}