package ADMS;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 随访数据管理界面 (支持完整的增删改查)
 */
public class FollowUpManageUI extends JFrame {

    private JTabbedPane tabbedPane;
    private JTable visitsTable;
    private JTextField searchVisitIdField, searchPatientIdField, searchProviderNameField;
    private String oldValue;
    private boolean isUpdating = false;
    private JTextField detailVisitIdField;
    private JTable symptomsTable, signsTable, diagnosesTable, medicationsTable, questionnairesTable;
    private JComboBox<String> sortComboBox;
    
    private JTable labTestsTable;
    private JTable pulmonaryFunctionTestsTable;
    private JTable fenoTestsTable;
    private JTable nasoendoscopyTable;
    private JTable hearingTestsTable;
    private JTable nasalResistanceTestsTable;
    private JTable imagingStudiesTable;
    private JTable pastMedicationHistoryTable;
    private JTable otherTreatmentsTable;
    private JTable summaryInfoTable;
    private JTable costsTable;
    private JTable adverseDrugReactionsTable;
    
    public FollowUpManageUI() {
        setTitle("管理随访数据");
        setSize(1200, 800);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("随访记录管理", createVisitSearchPanel());
        tabbedPane.addTab("随访详情", createDetailPanel());

        add(tabbedPane);
    }

    private JPanel createVisitSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        
        JPanel searchCriteriaPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; searchCriteriaPanel.add(new JLabel("随访ID:"), gbc);
        gbc.gridx = 1; searchVisitIdField = new JTextField(15); searchCriteriaPanel.add(searchVisitIdField, gbc);
        gbc.gridx = 2; gbc.gridy = 0; searchCriteriaPanel.add(new JLabel("患者ID:"), gbc);
        gbc.gridx = 3; searchPatientIdField = new JTextField(15); searchCriteriaPanel.add(searchPatientIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; searchCriteriaPanel.add(new JLabel("服务者姓名:"), gbc);
        gbc.gridx = 1; searchProviderNameField = new JTextField(15); searchCriteriaPanel.add(searchProviderNameField, gbc);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.add(new JLabel("排序依据:"));
        sortComboBox = new JComboBox<>(new String[]{"id", "patient_id", "provider_name", "visit_datetime"});
        actionPanel.add(sortComboBox);
        JButton searchButton = new JButton("查询");
        actionPanel.add(searchButton);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("添加");
        buttonPanel.add(addButton);
        JButton deleteButton = new JButton("删除");
        buttonPanel.add(deleteButton);
        JButton clearButton = new JButton("重置");
        buttonPanel.add(clearButton);
        JButton outButton = new JButton("导出");
        buttonPanel.add(outButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchCriteriaPanel, BorderLayout.CENTER);
        JPanel buttonContainerPanel = new JPanel(new BorderLayout());
        buttonContainerPanel.add(actionPanel, BorderLayout.NORTH);
        buttonContainerPanel.add(buttonPanel, BorderLayout.SOUTH);
        topPanel.add(buttonContainerPanel, BorderLayout.SOUTH);
        panel.add(topPanel, BorderLayout.NORTH);

        visitsTable = new JTable();
        visitsTable.getTableHeader().setReorderingAllowed(false);
        visitsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        visitsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        visitsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = visitsTable.getSelectedRow();
                int col = visitsTable.getSelectedColumn();
                if (row != -1 && col != -1) {
                    Object value = visitsTable.getValueAt(row, col);
                    oldValue = (value == null) ? null : value.toString();
                }
            }
        });
        panel.add(new JScrollPane(visitsTable), BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            DefaultTableModel model = DatabaseManager.searchFollowUpVisits(
                    searchVisitIdField.getText(),
                    searchPatientIdField.getText(),
                    searchProviderNameField.getText(),
                    (String) sortComboBox.getSelectedItem()
            );

            model.addTableModelListener(e1 -> {
                if (e1.getType() == TableModelEvent.UPDATE && !isUpdating) {
                	isUpdating = true;
                	try {
                        int row = e1.getFirstRow();
                        int column = e1.getColumn();
                        if (column != TableModelEvent.ALL_COLUMNS) {
                            String columnName = model.getColumnName(column);
                            Object dataObj = model.getValueAt(row, column);
                            String data = (dataObj == null) ? null : dataObj.toString();
                            String visitId = model.getValueAt(row, 0).toString();
                            
                            String msg = DatabaseManager.updateFollowUpVisit(visitId, columnName, data);
                            if (msg.contains("失败")) {
                            	visitsTable.setValueAt(oldValue, row, column);
                                JOptionPane.showMessageDialog(this, msg, "更新失败", JOptionPane.ERROR_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(this, msg, "更新成功", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                	}finally {
                		isUpdating = false;
                	}
                }
            });
            visitsTable.setModel(model);
            auto_resize_talbe(visitsTable);
        });
        
        addButton.addActionListener(e -> {
            AddFollowUpVisitDialog dialog = new AddFollowUpVisitDialog(this);
            dialog.setVisible(true);
            searchButton.doClick();
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = visitsTable.getSelectedRow();
            if (selectedRow != -1) {
                String visitId = visitsTable.getValueAt(selectedRow, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(this, "确定要删除ID为 " + visitId + " 的随访记录吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String msg = DatabaseManager.deleteFollowUpVisit(visitId);
                    JOptionPane.showMessageDialog(this, msg, "删除信息", JOptionPane.INFORMATION_MESSAGE);
                    if (!msg.contains("失败")) {
                        ((DefaultTableModel) visitsTable.getModel()).removeRow(selectedRow);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "请先选择要删除的行！", "提示", JOptionPane.WARNING_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> {
            searchVisitIdField.setText("");
            searchPatientIdField.setText("");
            searchProviderNameField.setText("");
        });
        
        outButton.addActionListener(e -> exportTableToCSV(this, visitsTable));

        return panel;
    }
    private static void exportTableToCSV(JFrame frame, JTable table) {
        if (table.getRowCount() == 0) {
            JOptionPane.showMessageDialog(frame, "表格中没有数据可导出。", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("选择保存位置");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setSelectedFile(new File("follow_up_data.csv"));

        int returnValue = fileChooser.showSaveDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.getName().toLowerCase().endsWith(".csv")) {
                selectedFile = new File(selectedFile.getParentFile(), selectedFile.getName() + ".csv");
            }
            try {
                exportToCSV(table, selectedFile);
                JOptionPane.showMessageDialog(frame, "导出成功: " + selectedFile.getAbsolutePath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "导出失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public static void exportToCSV(JTable table, File file) throws IOException {
        TableModel model = table.getModel();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < model.getColumnCount(); i++) {
                writer.write(escapeCSV(model.getColumnName(i)));
                if (i < model.getColumnCount() - 1) writer.write(",");
            }
            writer.newLine();

            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    Object value = model.getValueAt(i, j);
                    writer.write(escapeCSV(value != null ? value.toString() : ""));
                    if (j < model.getColumnCount() - 1) writer.write(",");
                }
                writer.newLine();
            }
        }
    }

    private static String escapeCSV(String input) {
        if (input == null) return "";
        if (input.contains(",") || input.contains("\"") || input.contains("\n")) {
            return "\"" + input.replace("\"", "\"\"") + "\"";
        }
        return input;
    }
    private JPanel createDetailPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("输入要查询详情的随访ID (followup_visit_id):"));
        detailVisitIdField = new JTextField(10);
        topPanel.add(detailVisitIdField);
        JButton searchDetailsButton = new JButton("查询详情");
        topPanel.add(searchDetailsButton);
        panel.add(topPanel, BorderLayout.NORTH);

        JTabbedPane detailTabs = new JTabbedPane();
        
        symptomsTable = new JTable();
        signsTable = new JTable();
        diagnosesTable = new JTable();
        medicationsTable = new JTable();
        questionnairesTable = new JTable();
        labTestsTable = new JTable();
        pulmonaryFunctionTestsTable = new JTable();
        fenoTestsTable = new JTable();
        nasoendoscopyTable = new JTable();
        hearingTestsTable = new JTable();
        nasalResistanceTestsTable = new JTable();
        imagingStudiesTable = new JTable();
        pastMedicationHistoryTable = new JTable();
        otherTreatmentsTable = new JTable();
        summaryInfoTable = new JTable();
        costsTable = new JTable();
        adverseDrugReactionsTable = new JTable();

        // 设置所有表格的自动调整模式
        symptomsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        signsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        diagnosesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        medicationsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        questionnairesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        
        labTestsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        pulmonaryFunctionTestsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        fenoTestsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        nasoendoscopyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        hearingTestsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        nasalResistanceTestsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        imagingStudiesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        pastMedicationHistoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        otherTreatmentsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        summaryInfoTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        costsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        adverseDrugReactionsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        detailTabs.addTab("症状", new JScrollPane(symptomsTable));
        detailTabs.addTab("体征", new JScrollPane(signsTable));
        detailTabs.addTab("诊断", new JScrollPane(diagnosesTable));
        detailTabs.addTab("用药", new JScrollPane(medicationsTable));
        detailTabs.addTab("问卷", new JScrollPane(questionnairesTable));
        detailTabs.addTab("实验室检查", new JScrollPane(labTestsTable));
        detailTabs.addTab("肺功能检查", new JScrollPane(pulmonaryFunctionTestsTable));
        detailTabs.addTab("FeNO检查", new JScrollPane(fenoTestsTable));
        detailTabs.addTab("鼻内镜检查", new JScrollPane(nasoendoscopyTable));
        detailTabs.addTab("听力检查", new JScrollPane(hearingTestsTable));
        detailTabs.addTab("鼻阻力检查", new JScrollPane(nasalResistanceTestsTable));
        detailTabs.addTab("影像学检查", new JScrollPane(imagingStudiesTable));
        detailTabs.addTab("既往用药史", new JScrollPane(pastMedicationHistoryTable));
        detailTabs.addTab("其他治疗", new JScrollPane(otherTreatmentsTable));
        detailTabs.addTab("信息摘要", new JScrollPane(summaryInfoTable));
        detailTabs.addTab("费用", new JScrollPane(costsTable));
        detailTabs.addTab("药物不良反应", new JScrollPane(adverseDrugReactionsTable));

        panel.add(detailTabs, BorderLayout.CENTER);

        searchDetailsButton.addActionListener(e -> {
            String visitId = detailVisitIdField.getText();
            if (visitId == null || visitId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入随访ID！", "输入错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int id = Integer.parseInt(visitId);
                

                symptomsTable.setModel(DatabaseManager.getFollowUpSymptoms(id));
                signsTable.setModel(DatabaseManager.getFollowUpSigns(id));
                diagnosesTable.setModel(DatabaseManager.getFollowUpDiagnoses(id));
                medicationsTable.setModel(DatabaseManager.getFollowUpMedications(id));
                questionnairesTable.setModel(DatabaseManager.getFollowUpQuestionnaires(id));
                labTestsTable.setModel(DatabaseManager.getFollowUpLabTests(id));
                pulmonaryFunctionTestsTable.setModel(DatabaseManager.getFollowUpPulmonaryFunctionTests(id));
                fenoTestsTable.setModel(DatabaseManager.getFollowUpFeNOTests(id));
                nasoendoscopyTable.setModel(DatabaseManager.getFollowUpNasoendoscopy(id));
                hearingTestsTable.setModel(DatabaseManager.getFollowUpHearingTests(id));
                nasalResistanceTestsTable.setModel(DatabaseManager.getFollowUpNasalResistanceTests(id));
                imagingStudiesTable.setModel(DatabaseManager.getFollowUpImagingStudies(id));
                pastMedicationHistoryTable.setModel(DatabaseManager.getFollowUpPastMedicationHistory(id));
                otherTreatmentsTable.setModel(DatabaseManager.getFollowUpOtherTreatments(id));
                summaryInfoTable.setModel(DatabaseManager.getFollowUpSummaryInfo(id));
                costsTable.setModel(DatabaseManager.getFollowUpCosts(id));
                adverseDrugReactionsTable.setModel(DatabaseManager.getFollowUpAdverseDrugReactions(id));
                
                // 自动调整所有表格列宽
                auto_resize_talbe(symptomsTable);
                auto_resize_talbe(signsTable);
                auto_resize_talbe(diagnosesTable);
                auto_resize_talbe(medicationsTable);
                auto_resize_talbe(questionnairesTable);                
                auto_resize_talbe(labTestsTable);
                auto_resize_talbe(pulmonaryFunctionTestsTable);
                auto_resize_talbe(fenoTestsTable);
                auto_resize_talbe(nasoendoscopyTable);
                auto_resize_talbe(hearingTestsTable);
                auto_resize_talbe(nasalResistanceTestsTable);
                auto_resize_talbe(imagingStudiesTable);
                auto_resize_talbe(pastMedicationHistoryTable);
                auto_resize_talbe(otherTreatmentsTable);
                auto_resize_talbe(summaryInfoTable);
                auto_resize_talbe(costsTable);
                auto_resize_talbe(adverseDrugReactionsTable);
                
                // 禁用所有表格的编辑功能
                symptomsTable.setEnabled(false);
                signsTable.setEnabled(false);
                diagnosesTable.setEnabled(false);
                medicationsTable.setEnabled(false);
                questionnairesTable.setEnabled(false);                
                labTestsTable.setEnabled(false);
                pulmonaryFunctionTestsTable.setEnabled(false);
                fenoTestsTable.setEnabled(false);
                nasoendoscopyTable.setEnabled(false);
                hearingTestsTable.setEnabled(false);
                nasalResistanceTestsTable.setEnabled(false);
                imagingStudiesTable.setEnabled(false);
                pastMedicationHistoryTable.setEnabled(false);
                otherTreatmentsTable.setEnabled(false);
                summaryInfoTable.setEnabled(false);
                costsTable.setEnabled(false);
                adverseDrugReactionsTable.setEnabled(false);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "随访ID必须是数字！", "输入错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }
    public void auto_resize_talbe(JTable table) {
    	for (int column = 0; column < table.getColumnModel().getColumnCount(); column++) {
    	    TableColumn tableColumn = table.getColumnModel().getColumn(column);
    	    int preferredWidth = tableColumn.getHeaderValue().toString().length() * 10; // 根据文本长度计算宽度
    	    tableColumn.setPreferredWidth(Math.max(preferredWidth, 100)); // 设置最小宽度为100
    	}
    }
}