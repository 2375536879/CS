package ADMS;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 添加新的生物样本的对话框
 */
class AddBioSampleDialog extends JDialog {

    private JTextField patientIdField = new JTextField(20);
    private JTextField visitIdField = new JTextField(20);
    private JTextField sampleTypeField = new JTextField(20);
    private JTextField collectionDateTimeField = new JTextField(20);
    private JTextField collectionSiteField = new JTextField(20);
    private JTextField preprocessingMethodField = new JTextField(20);
    private JTextField freezeCyclesField = new JTextField(20);
    private JTextField storageTempField = new JTextField(20);
    private JTextField storageDaysField = new JTextField(20);
    private JTextField rnaIntegrityField = new JTextField(20);
    private JTextField dnaConcentrationField = new JTextField(20);
    private JTextField linkedSummaryField = new JTextField(20);
    private JTextField consentIdField = new JTextField(20);
    private JTextArea notesArea = new JTextArea(3, 20);

    public AddBioSampleDialog(Frame owner) {
        super(owner, "添加新的生物样本", true);
        
        setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 原有字段
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("患者ID:*"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; formPanel.add(patientIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("就诊ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(visitIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("样本类型:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(sampleTypeField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("采集时间 (YYYY-MM-DD HH:MM:SS):"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; formPanel.add(collectionDateTimeField, gbc);
        collectionDateTimeField.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(new JLabel("存储温度 (°C):"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; formPanel.add(storageTempField, gbc);

        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(new JLabel("知情同意书ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; formPanel.add(consentIdField, gbc);

        // 新增字段
        gbc.gridx = 0; gbc.gridy = 6; formPanel.add(new JLabel("采集地点:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6; formPanel.add(collectionSiteField, gbc);

        gbc.gridx = 0; gbc.gridy = 7; formPanel.add(new JLabel("预处理方法:"), gbc);
        gbc.gridx = 1; gbc.gridy = 7; formPanel.add(preprocessingMethodField, gbc);

        gbc.gridx = 0; gbc.gridy = 8; formPanel.add(new JLabel("冻融次数 (次):"), gbc);
        gbc.gridx = 1; gbc.gridy = 8; formPanel.add(freezeCyclesField, gbc);

        gbc.gridx = 0; gbc.gridy = 9; formPanel.add(new JLabel("存储天数: (天)"), gbc);
        gbc.gridx = 1; gbc.gridy = 9; formPanel.add(storageDaysField, gbc);

        gbc.gridx = 0; gbc.gridy = 10; formPanel.add(new JLabel("RNA完整性指数:"), gbc);
        gbc.gridx = 1; gbc.gridy = 10; formPanel.add(rnaIntegrityField, gbc);

        gbc.gridx = 0; gbc.gridy = 11; formPanel.add(new JLabel("DNA浓度 (ng/µL):"), gbc);
        gbc.gridx = 1; gbc.gridy = 11; formPanel.add(dnaConcentrationField, gbc);

        gbc.gridx = 0; gbc.gridy = 12; formPanel.add(new JLabel("关联临床表型数据摘要:"), gbc);
        gbc.gridx = 1; gbc.gridy = 12; formPanel.add(linkedSummaryField, gbc);

        gbc.gridx = 0; gbc.gridy = 13; formPanel.add(new JLabel("实验室处理备注:"), gbc);
        gbc.gridx = 1; gbc.gridy = 13; formPanel.add(new JScrollPane(notesArea), gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("保存");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> saveBioSample());
        cancelButton.addActionListener(e -> dispose());
        
        pack();
        setLocationRelativeTo(owner);
    }

    private void saveBioSample() {
        String patientId = patientIdField.getText();
        String visitId = visitIdField.getText();
        String sampleType = sampleTypeField.getText();
        String collectionDateTime = collectionDateTimeField.getText();
        String collectionSite = collectionSiteField.getText();
        String preprocessingMethod = preprocessingMethodField.getText();
        String freezeCycles = freezeCyclesField.getText();
        String storageTemp = storageTempField.getText();
        String storageDays = storageDaysField.getText();
        String rnaIntegrity = rnaIntegrityField.getText();
        String dnaConcentration = dnaConcentrationField.getText();
        String linkedSummary = linkedSummaryField.getText();
        String consentId = consentIdField.getText();
        String notes = notesArea.getText();

        if (patientId == null || patientId.trim().isEmpty() || sampleType == null || sampleType.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "患者ID和样本类型是必填项！", "输入错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String msg = DatabaseManager.addBioSample(
            patientId,
            visitId,
            sampleType,
            collectionDateTime,
            collectionSite,
            preprocessingMethod,
            freezeCycles,
            storageTemp,
            storageDays,
            rnaIntegrity,
            dnaConcentration,
            linkedSummary,
            consentId,
            notes
        );

        JOptionPane.showMessageDialog(this, msg, "添加结果", JOptionPane.INFORMATION_MESSAGE);
        if (!msg.contains("失败")) {
            dispose();
        }
    }
}