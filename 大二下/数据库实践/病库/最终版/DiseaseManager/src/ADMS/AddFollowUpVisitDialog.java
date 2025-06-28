package ADMS;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 添加新的随访记录的对话框
 */
class AddFollowUpVisitDialog extends JDialog {

    private JTextField patientIdField = new JTextField(20);
    private JTextField hospitalPatientIdField = new JTextField(20);
    private JTextField visitDateTimeField = new JTextField(20);
    private JComboBox<String> isInitialVisitCombo = new JComboBox<>(new String[]{"0", "1"});
    private JTextField heightField = new JTextField(20);
    private JTextField weightField = new JTextField(20);
    private JTextField providerNameField = new JTextField(20);
    private JComboBox<String> providerTitleCombo = new JComboBox<>(new String[]{"住院医师", "主治医师", "副主任医师", "主任医师"});
    private JTextField homeAddressField = new JTextField(20);

    public AddFollowUpVisitDialog(Frame owner) {
        super(owner, "添加新的随访记录", true);
        
        setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("患者ID (patient_id):*"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; formPanel.add(patientIdField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("门诊号:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(hospitalPatientIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("就诊时间 (YYYY-MM-DD HH:MM:SS):"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(visitDateTimeField, gbc);
        visitDateTimeField.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("初诊 (1=是, 0=否):"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; formPanel.add(isInitialVisitCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(new JLabel("身高 (cm):"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; formPanel.add(heightField, gbc);

        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(new JLabel("体重 (kg):"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; formPanel.add(weightField, gbc);

        gbc.gridx = 0; gbc.gridy = 6; formPanel.add(new JLabel("医疗服务者姓名:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6; formPanel.add(providerNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 7; formPanel.add(new JLabel("医疗服务者职称:"), gbc);
        gbc.gridx = 1; gbc.gridy = 7; formPanel.add(providerTitleCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 8; formPanel.add(new JLabel("家庭住址:"), gbc);
        gbc.gridx = 1; gbc.gridy = 8; formPanel.add(homeAddressField, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("保存");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> saveFollowUpVisit());
        cancelButton.addActionListener(e -> dispose());
        
        pack();
        setLocationRelativeTo(owner);
    }

    private void saveFollowUpVisit() {
        String patientId = patientIdField.getText();
        if (patientId == null || patientId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "患者ID是必填项！", "输入错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String msg = DatabaseManager.addFollowUpVisit(
            patientId,
            hospitalPatientIdField.getText(),
            visitDateTimeField.getText(),
            (String) isInitialVisitCombo.getSelectedItem(),
            heightField.getText(),
            weightField.getText(),
            providerNameField.getText(),
            (String) providerTitleCombo.getSelectedItem(),
            homeAddressField.getText()
        );

        JOptionPane.showMessageDialog(this, msg, "添加结果", JOptionPane.INFORMATION_MESSAGE);
        if (!msg.contains("失败")) {
            dispose();
        }
    }
}
