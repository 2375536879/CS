package ADMS;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 生物样本管理界面
 */
public class BioSampleManageUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JTable searchResultTable;
    private JTextField searchIdField, searchPatientIdField, searchVisitIdField, searchSampleTypeField, searchConsentIdField;
    private JComboBox<String> sortComboBox;
    private String oldValue;

    private JTextField omicsBioSampleIdField;
    private JTable genomicDataTable, proteomicDataTable, metabolomicDataTable, microbiomeDataTable;

    public BioSampleManageUI() {
        setTitle("管理生物样本");
        setSize(1000, 700);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("生物样本管理", createSearchPanel());
        tabbedPane.addTab("组学数据查询", createOmicsPanel());
        add(tabbedPane);
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JPanel searchCriteriaPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; searchCriteriaPanel.add(new JLabel("样本ID:"), gbc);
        gbc.gridx = 1; searchIdField = new JTextField(15); searchCriteriaPanel.add(searchIdField, gbc);
        gbc.gridx = 2; gbc.gridy = 0; searchCriteriaPanel.add(new JLabel("患者ID:"), gbc);
        gbc.gridx = 3; searchPatientIdField = new JTextField(15); searchCriteriaPanel.add(searchPatientIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; searchCriteriaPanel.add(new JLabel("就诊ID:"), gbc);
        gbc.gridx = 1; searchVisitIdField = new JTextField(15); searchCriteriaPanel.add(searchVisitIdField, gbc);
        gbc.gridx = 2; gbc.gridy = 1; searchCriteriaPanel.add(new JLabel("样本类型:"), gbc);
        gbc.gridx = 3; searchSampleTypeField = new JTextField(15); searchCriteriaPanel.add(searchSampleTypeField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; searchCriteriaPanel.add(new JLabel("知情同意书ID:"), gbc);
        gbc.gridx = 1; searchConsentIdField = new JTextField(15); searchCriteriaPanel.add(searchConsentIdField, gbc);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.add(new JLabel("排序依据:"));
        sortComboBox = new JComboBox<>(new String[]{"id", "patient_id", "visit_id", "sample_type", "collection_datetime"});
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

        searchResultTable = new JTable();
        searchResultTable.getTableHeader().setReorderingAllowed(false);
        searchResultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // --- 添加横向滚动条的关键设置 ---
        searchResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        searchResultTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = searchResultTable.getSelectedRow();
                int col = searchResultTable.getSelectedColumn();
                if (row != -1 && col != -1) {
                    Object value = searchResultTable.getValueAt(row, col);
                    oldValue = (value == null) ? null : value.toString();
                }
            }
        });
        panel.add(new JScrollPane(searchResultTable), BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            DefaultTableModel model = DatabaseManager.searchBioSamples(
                    searchIdField.getText(), searchPatientIdField.getText(), searchVisitIdField.getText(),
                    searchSampleTypeField.getText(), searchConsentIdField.getText(), (String) sortComboBox.getSelectedItem()
            );
            model.addTableModelListener(e1 -> {
                if (e1.getType() == TableModelEvent.UPDATE) {
                    int row = e1.getFirstRow();
                    int column = e1.getColumn();
                    if (column != TableModelEvent.ALL_COLUMNS) {
                        String columnName = model.getColumnName(column);
                        Object dataObj = model.getValueAt(row, column);
                        String data = (dataObj == null) ? null : dataObj.toString();
                        String sampleId = model.getValueAt(row, 0).toString();
                        String msg = DatabaseManager.updateBioSample(sampleId, columnName, data);
                        if (msg.contains("失败")) {
                            searchResultTable.setValueAt(oldValue, row, column);
                            JOptionPane.showMessageDialog(null, msg, "更新失败", JOptionPane.ERROR_MESSAGE);
                        } else {
                             JOptionPane.showMessageDialog(null, msg, "更新成功", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            });
            searchResultTable.setModel(model);
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = searchResultTable.getSelectedRow();
            if (selectedRow != -1) {
                String sampleId = searchResultTable.getValueAt(selectedRow, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(this, "确定要删除ID为 " + sampleId + " 的样本吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String msg = DatabaseManager.deleteBioSample(sampleId);
                    JOptionPane.showMessageDialog(null, msg, "删除信息", JOptionPane.INFORMATION_MESSAGE);
                    if (!msg.contains("失败")) {
                        ((DefaultTableModel) searchResultTable.getModel()).removeRow(selectedRow);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "请先选择要删除的行！", "提示", JOptionPane.WARNING_MESSAGE);
            }
        });

        addButton.addActionListener(e -> {
            String patientId = JOptionPane.showInputDialog(this, "输入患者ID (patient_id):");
            if (patientId != null && !patientId.trim().isEmpty()) {
                String sampleType = JOptionPane.showInputDialog(this, "输入样本类型 (sample_type):");
                if(sampleType != null && !sampleType.trim().isEmpty()){
                    String msg = DatabaseManager.addBioSample(patientId, sampleType);
                    JOptionPane.showMessageDialog(null, msg, "添加信息", JOptionPane.INFORMATION_MESSAGE);
                    searchButton.doClick(); // Refresh table
                }
            }
        });

        clearButton.addActionListener(e -> {
            searchIdField.setText("");
            searchPatientIdField.setText("");
            searchVisitIdField.setText("");
            searchSampleTypeField.setText("");
            searchConsentIdField.setText("");
        });
        
        outButton.addActionListener(e -> exportTableToCSV(this, searchResultTable));

        return panel;
    }
    
    private JPanel createOmicsPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("生物样本ID (biosample_id):"));
        omicsBioSampleIdField = new JTextField(10);
        topPanel.add(omicsBioSampleIdField);
        JButton searchOmicsButton = new JButton("查询组学数据");
        topPanel.add(searchOmicsButton);
        panel.add(topPanel, BorderLayout.NORTH);

        JTabbedPane omicsTabbedPane = new JTabbedPane();
        genomicDataTable = new JTable();
        proteomicDataTable = new JTable();
        metabolomicDataTable = new JTable();
        microbiomeDataTable = new JTable();

        // --- 为所有组学表格添加横向滚动条的关键设置 ---
        genomicDataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        proteomicDataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        metabolomicDataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        microbiomeDataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        omicsTabbedPane.addTab("基因组数据", new JScrollPane(genomicDataTable));
        omicsTabbedPane.addTab("蛋白质组数据", new JScrollPane(proteomicDataTable));
        omicsTabbedPane.addTab("代谢组数据", new JScrollPane(metabolomicDataTable));
        omicsTabbedPane.addTab("菌群数据", new JScrollPane(microbiomeDataTable));
        panel.add(omicsTabbedPane, BorderLayout.CENTER);

        searchOmicsButton.addActionListener(e -> {
            String bioSampleId = omicsBioSampleIdField.getText();
            if (bioSampleId == null || bioSampleId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入生物样本ID！", "输入错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                genomicDataTable.setModel(DatabaseManager.getGenomicData(bioSampleId));
                proteomicDataTable.setModel(DatabaseManager.getProteomicData(bioSampleId));
                metabolomicDataTable.setModel(DatabaseManager.getMetabolomicData(bioSampleId));
                microbiomeDataTable.setModel(DatabaseManager.getMicrobiomeData(bioSampleId));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "查询失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

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
        fileChooser.setSelectedFile(new File("biosample_data.csv"));

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
}