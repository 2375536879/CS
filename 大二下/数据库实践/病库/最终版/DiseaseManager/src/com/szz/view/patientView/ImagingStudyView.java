package com.szz.view.patientView;

import com.szz.model.Clinical.ClinicalVisit;
import com.szz.model.Clinical.ImagingStudy;
import com.szz.service.Clinical.ImagingStudyService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ImagingStudyView extends JPanel {
    private final int visitId;
    private final int patientId;
    private final boolean isPatientView;
    private final ImagingStudyService imagingStudyService;
    private JTable studyTable;
    private DefaultTableModel tableModel;
    private JTextArea reportArea;

    // 构造函数：按就诊记录查询
    public ImagingStudyView(int visitId) {
        this.visitId = visitId;
        this.patientId = -1;
        this.isPatientView = false;
        this.imagingStudyService = new ImagingStudyService();
        initializeUI();
        loadData();
    }

    // 构造函数：按患者ID查询（重载）
    public ImagingStudyView(int patientId, boolean isPatientView) {
        this.visitId = -1;
        this.patientId = patientId;
        this.isPatientView = isPatientView;
        this.imagingStudyService = new ImagingStudyService();
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // 创建表格面板
        JPanel tablePanel = new JPanel(new BorderLayout());
        
        // 创建表格列
        String[] columns = {
            "检查名称", "检查日期", "影像存档路径"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studyTable = new JTable(tableModel);
        studyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        studyTable.getTableHeader().setReorderingAllowed(false);

        // 设置表格列宽
        studyTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        studyTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        studyTable.getColumnModel().getColumn(2).setPreferredWidth(200);

        // 添加表格选择监听器
        studyTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateReportArea();
            }
        });

        // 添加表格滚动面板
        JScrollPane tableScrollPane = new JScrollPane(studyTable);
        tableScrollPane.setPreferredSize(new Dimension(500, 200));
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // 创建报告详情面板
        JPanel reportPanel = new JPanel(new BorderLayout());
        reportPanel.setBorder(BorderFactory.createTitledBorder("检查报告摘要"));
        
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setWrapStyleWord(true);
        reportArea.setLineWrap(true);
        
        JScrollPane reportScrollPane = new JScrollPane(reportArea);
        reportScrollPane.setPreferredSize(new Dimension(500, 150));
        reportPanel.add(reportScrollPane, BorderLayout.CENTER);

        // 创建分割面板
        JSplitPane splitPane = new JSplitPane(
            JSplitPane.VERTICAL_SPLIT,
            tablePanel,
            reportPanel
        );
        splitPane.setResizeWeight(0.6);

        add(splitPane, BorderLayout.CENTER);
    }

    private void loadData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            if (isPatientView) {
                // 按患者ID查询：获取患者的所有就诊记录，然后获取每个就诊记录的影像学检查数据
                com.szz.service.Clinical.ClinicalVisitService visitService = new com.szz.service.Clinical.ClinicalVisitService();
                List<ClinicalVisit> visits = visitService.getVisitsByPatientId(patientId);

                for (ClinicalVisit visit : visits) {
                    List<ImagingStudy> studies = imagingStudyService.getImagingStudiesByVisitId(visit.getId());

                    for (ImagingStudy study : studies) {
                        Object[] rowData = {
                            study.getStudyName(),
                            study.getStudyDate() != null ? dateFormat.format(study.getStudyDate()) : "",
                            study.getImagePathOrIdentifier()
                        };
                        tableModel.addRow(rowData);
                    }
                }
            } else {
                // 按就诊记录ID查询
                List<ImagingStudy> studies = imagingStudyService.getImagingStudiesByVisitId(visitId);

                for (ImagingStudy study : studies) {
                    Object[] rowData = {
                        study.getStudyName(),
                        study.getStudyDate() != null ? dateFormat.format(study.getStudyDate()) : "",
                        study.getImagePathOrIdentifier()
                    };
                    tableModel.addRow(rowData);
                }
            }

            if (tableModel.getRowCount() == 0) {
                Object[] emptyRow = {"暂无影像学检查数据", "", ""};
                tableModel.addRow(emptyRow);
            } else {
                // 如果有数据，选择第一行
                studyTable.setRowSelectionInterval(0, 0);
                updateReportArea();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "加载影像学检查数据时出错: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updateReportArea() {
        int selectedRow = studyTable.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {
            try {
                // 获取所有影像学检查数据
                List<ImagingStudy> allStudies = new java.util.ArrayList<>();

                if (isPatientView) {
                    // 按患者ID查询所有就诊记录的影像学检查数据
                    com.szz.service.Clinical.ClinicalVisitService visitService = new com.szz.service.Clinical.ClinicalVisitService();
                    List<ClinicalVisit> visits = visitService.getVisitsByPatientId(patientId);

                    for (ClinicalVisit visit : visits) {
                        List<ImagingStudy> studies = imagingStudyService.getImagingStudiesByVisitId(visit.getId());
                        allStudies.addAll(studies);
                    }
                } else {
                    // 按就诊记录ID查询
                    allStudies = imagingStudyService.getImagingStudiesByVisitId(visitId);
                }

                if (selectedRow < allStudies.size()) {
                    ImagingStudy study = allStudies.get(selectedRow);
                    reportArea.setText(study.getReportSummary() != null ? study.getReportSummary() : "暂无报告摘要");
                    reportArea.setCaretPosition(0);
                } else {
                    reportArea.setText("暂无报告摘要");
                }
            } catch (Exception e) {
                reportArea.setText("加载报告摘要时出错: " + e.getMessage());
            }
        } else {
            reportArea.setText("");
        }
    }
}