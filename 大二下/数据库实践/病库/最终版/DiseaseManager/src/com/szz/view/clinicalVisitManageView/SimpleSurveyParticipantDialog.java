package com.szz.view.clinicalVisitManageView;

import com.szz.model.Survey.SurveyParticipant;
import com.szz.service.Survey.SurveyParticipantService;

import javax.swing.*;
import java.awt.*;

public class SimpleSurveyParticipantDialog extends JDialog {
    private SurveyParticipantService surveyParticipantService;
    private SurveyParticipant participant;
    private boolean confirmed = false;


    private JTextField patientIdField;
    private JTextField surveyIdentifierField;
    private JTextField nameField;
    private JComboBox<String> genderComboBox;
    private JTextField ageField;
    private JComboBox<String> residenceTypeComboBox;

    public SimpleSurveyParticipantDialog(JFrame parent, String title, SurveyParticipant participant, 
                                   SurveyParticipantService service) {
        super(parent, title, true);
        this.surveyParticipantService = service;
        this.participant = participant;
        
        initUI();
        if (participant != null) {
            populateFields();
        }
        
        setSize(600, 450);
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // 创建主面板
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 患者ID
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        JLabel patientIdLabel = new JLabel("患者ID:");
        patientIdLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        mainPanel.add(patientIdLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        patientIdField = new JTextField();
        patientIdField.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        patientIdField.setPreferredSize(new Dimension(300, 30));
        mainPanel.add(patientIdField, gbc);

        // 调查编号
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        JLabel surveyIdLabel = new JLabel("调查编号:");
        surveyIdLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        mainPanel.add(surveyIdLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        surveyIdentifierField = new JTextField();
        surveyIdentifierField.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        surveyIdentifierField.setPreferredSize(new Dimension(300, 30));
        mainPanel.add(surveyIdentifierField, gbc);

        // 参与者姓名
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
        JLabel nameLabel = new JLabel("参与者姓名:");
        nameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        mainPanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        nameField = new JTextField();
        nameField.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        nameField.setPreferredSize(new Dimension(300, 30));
        mainPanel.add(nameField, gbc);

        // 性别
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0;
        JLabel genderLabel = new JLabel("性别:");
        genderLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        mainPanel.add(genderLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        genderComboBox = new JComboBox<>(new String[]{"", "男", "女", "其他"});
        genderComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        genderComboBox.setPreferredSize(new Dimension(300, 30));
        mainPanel.add(genderComboBox, gbc);

        // 年龄
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.0;
        JLabel ageLabel = new JLabel("年龄:");
        ageLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        mainPanel.add(ageLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        ageField = new JTextField();
        ageField.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        ageField.setPreferredSize(new Dimension(300, 30));
        mainPanel.add(ageField, gbc);

        // 居住地类型
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0.0;
        JLabel residenceLabel = new JLabel("居住地类型:");
        residenceLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        mainPanel.add(residenceLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        residenceTypeComboBox = new JComboBox<>(new String[]{"", "城市", "农村"});
        residenceTypeComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        residenceTypeComboBox.setPreferredSize(new Dimension(300, 30));
        mainPanel.add(residenceTypeComboBox, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JButton saveButton = new JButton("保存");
        saveButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        saveButton.setPreferredSize(new Dimension(80, 35));
        saveButton.setBackground(new Color(92, 184, 92));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> saveParticipant());

        JButton cancelButton = new JButton("取消");
        cancelButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        cancelButton.setPreferredSize(new Dimension(80, 35));
        cancelButton.setBackground(new Color(108, 117, 125));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> dispose());

        panel.add(saveButton);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(cancelButton);

        return panel;
    }

    private void populateFields() {
        if (participant == null) return;

        if (participant.getPatientId() != null) {
            patientIdField.setText(String.valueOf(participant.getPatientId()));
        }
        surveyIdentifierField.setText(participant.getSurveyIdentifier());
        nameField.setText(participant.getName());
        genderComboBox.setSelectedItem(participant.getGender());
        if (participant.getAgeAtSurvey() != null) {
            ageField.setText(String.valueOf(participant.getAgeAtSurvey()));
        }
        residenceTypeComboBox.setSelectedItem(participant.getResidenceType());
    }

    private void saveParticipant() {
        try {
            // 验证必填字段
            if (nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入参与者姓名", "验证错误", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 创建或更新参与者对象
            if (participant == null) {
                participant = new SurveyParticipant();
            }

            // 设置基本信息
            String patientIdText = patientIdField.getText().trim();
            if (!patientIdText.isEmpty()) {
                participant.setPatientId(Integer.parseInt(patientIdText));
            } else {
                participant.setPatientId(null);
            }

            participant.setSurveyIdentifier(surveyIdentifierField.getText().trim());
            participant.setName(nameField.getText().trim());
            participant.setGender((String) genderComboBox.getSelectedItem());

            String ageText = ageField.getText().trim();
            if (!ageText.isEmpty()) {
                participant.setAgeAtSurvey(Integer.parseInt(ageText));
            } else {
                participant.setAgeAtSurvey(null);
            }

            participant.setResidenceType((String) residenceTypeComboBox.getSelectedItem());

            // 保存到数据库
            boolean success;
            if (participant.getId() == 0) {
                // 新增
                int newId = surveyParticipantService.createSurveyParticipant(participant);
                success = newId > 0;
            } else {
                // 更新
                success = surveyParticipantService.updateSurveyParticipant(participant);
            }

            if (success) {
                confirmed = true;
                JOptionPane.showMessageDialog(this, "保存成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "保存失败", "错误", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "患者ID和年龄必须是数字", "验证错误", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "保存失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
