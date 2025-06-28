package com.szz.view.clinicalVisitManageView;

import com.szz.model.Survey.SurveyParticipant;
import com.szz.service.Survey.SurveyParticipantService;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SurveyParticipantDialog extends JDialog {
    private SurveyParticipantService surveyParticipantService;
    private SurveyParticipant participant;
    private boolean confirmed = false;

    // 基本信息字段
    private JTextField patientIdField;
    private JTextField nameField;
    private JComboBox<String> genderComboBox;
    private JTextField birthDateField;
    private JTextField phoneField;
    private JTextArea addressArea;
    private JComboBox<String> educationComboBox;
    private JTextField occupationField;
    private JComboBox<String> maritalStatusComboBox;
    private JTextField incomeField;

    // 生活习惯字段
    private JComboBox<String> smokingStatusComboBox;
    private JComboBox<String> drinkingStatusComboBox;
    private JTextField exerciseField;
    private JTextArea dietArea;
    private JComboBox<String> sleepQualityComboBox;
    private JComboBox<String> stressLevelComboBox;

    // 健康信息字段
    private JTextArea chronicDiseasesArea;
    private JTextArea medicationHistoryArea;
    private JTextArea allergyHistoryArea;
    private JTextArea familyMedicalHistoryArea;
    private JTextArea environmentalExposureArea;
    private JTextArea occupationalExposureArea;
    private JTextArea notesArea;

    public SurveyParticipantDialog(JFrame parent, String title, SurveyParticipant participant, 
                                   SurveyParticipantService service) {
        super(parent, title, true);
        this.surveyParticipantService = service;
        this.participant = participant;
        
        initUI();
        if (participant != null) {
            populateFields();
        }
        
        setSize(800, 700);
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 创建标签页面板
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 12));

        // 基本信息标签页
        tabbedPane.addTab("基本信息", createBasicInfoPanel());
        
        // 生活习惯标签页
        tabbedPane.addTab("生活习惯", createLifestylePanel());
        
        // 健康信息标签页
        tabbedPane.addTab("健康信息", createHealthInfoPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createBasicInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 患者ID
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("患者ID:"), gbc);
        gbc.gridx = 1;
        patientIdField = new JTextField(20);
        panel.add(patientIdField, gbc);

        // 参与者姓名
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("参与者姓名:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        panel.add(nameField, gbc);

        // 性别
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("性别:"), gbc);
        gbc.gridx = 1;
        genderComboBox = new JComboBox<>(new String[]{"", "男", "女", "其他"});
        panel.add(genderComboBox, gbc);

        // 出生日期
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("出生日期(yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        birthDateField = new JTextField(20);
        panel.add(birthDateField, gbc);

        // 联系电话
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("联系电话:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(20);
        panel.add(phoneField, gbc);

        // 家庭住址
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("家庭住址:"), gbc);
        gbc.gridx = 1;
        addressArea = new JTextArea(3, 20);
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(addressArea), gbc);

        // 教育水平
        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(new JLabel("教育水平:"), gbc);
        gbc.gridx = 1;
        educationComboBox = new JComboBox<>(new String[]{"", "小学", "初中", "高中", "大专", "本科", "硕士", "博士"});
        panel.add(educationComboBox, gbc);

        // 职业
        gbc.gridx = 0; gbc.gridy = 7;
        panel.add(new JLabel("职业:"), gbc);
        gbc.gridx = 1;
        occupationField = new JTextField(20);
        panel.add(occupationField, gbc);

        // 婚姻状况
        gbc.gridx = 0; gbc.gridy = 8;
        panel.add(new JLabel("婚姻状况:"), gbc);
        gbc.gridx = 1;
        maritalStatusComboBox = new JComboBox<>(new String[]{"", "未婚", "已婚", "离异", "丧偶"});
        panel.add(maritalStatusComboBox, gbc);

        // 家庭收入
        gbc.gridx = 0; gbc.gridy = 9;
        panel.add(new JLabel("家庭收入:"), gbc);
        gbc.gridx = 1;
        incomeField = new JTextField(20);
        panel.add(incomeField, gbc);

        return panel;
    }

    private JPanel createLifestylePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 吸烟状况
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("吸烟状况:"), gbc);
        gbc.gridx = 1;
        smokingStatusComboBox = new JComboBox<>(new String[]{"", "从不吸烟", "偶尔吸烟", "经常吸烟", "已戒烟"});
        panel.add(smokingStatusComboBox, gbc);

        // 饮酒状况
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("饮酒状况:"), gbc);
        gbc.gridx = 1;
        drinkingStatusComboBox = new JComboBox<>(new String[]{"", "从不饮酒", "偶尔饮酒", "经常饮酒", "已戒酒"});
        panel.add(drinkingStatusComboBox, gbc);

        // 运动频率
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("运动频率:"), gbc);
        gbc.gridx = 1;
        exerciseField = new JTextField(20);
        panel.add(exerciseField, gbc);

        // 饮食习惯
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("饮食习惯:"), gbc);
        gbc.gridx = 1;
        dietArea = new JTextArea(3, 20);
        dietArea.setLineWrap(true);
        dietArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(dietArea), gbc);

        // 睡眠质量
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("睡眠质量:"), gbc);
        gbc.gridx = 1;
        sleepQualityComboBox = new JComboBox<>(new String[]{"", "很好", "好", "一般", "差", "很差"});
        panel.add(sleepQualityComboBox, gbc);

        // 压力水平
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("压力水平:"), gbc);
        gbc.gridx = 1;
        stressLevelComboBox = new JComboBox<>(new String[]{"", "很低", "低", "中等", "高", "很高"});
        panel.add(stressLevelComboBox, gbc);

        return panel;
    }

    private JPanel createHealthInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.BOTH;

        // 慢性疾病
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panel.add(new JLabel("慢性疾病:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.weighty = 0.2;
        chronicDiseasesArea = new JTextArea(3, 20);
        chronicDiseasesArea.setLineWrap(true);
        chronicDiseasesArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(chronicDiseasesArea), gbc);

        // 用药史
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.weighty = 0;
        panel.add(new JLabel("用药史:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.weighty = 0.2;
        medicationHistoryArea = new JTextArea(3, 20);
        medicationHistoryArea.setLineWrap(true);
        medicationHistoryArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(medicationHistoryArea), gbc);

        // 过敏史
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; gbc.weighty = 0;
        panel.add(new JLabel("过敏史:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.weighty = 0.2;
        allergyHistoryArea = new JTextArea(3, 20);
        allergyHistoryArea.setLineWrap(true);
        allergyHistoryArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(allergyHistoryArea), gbc);

        // 家族病史
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0; gbc.weighty = 0;
        panel.add(new JLabel("家族病史:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.weighty = 0.2;
        familyMedicalHistoryArea = new JTextArea(3, 20);
        familyMedicalHistoryArea.setLineWrap(true);
        familyMedicalHistoryArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(familyMedicalHistoryArea), gbc);

        // 环境暴露
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0; gbc.weighty = 0;
        panel.add(new JLabel("环境暴露:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.weighty = 0.2;
        environmentalExposureArea = new JTextArea(3, 20);
        environmentalExposureArea.setLineWrap(true);
        environmentalExposureArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(environmentalExposureArea), gbc);

        // 职业暴露
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0; gbc.weighty = 0;
        panel.add(new JLabel("职业暴露:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.weighty = 0.2;
        occupationalExposureArea = new JTextArea(3, 20);
        occupationalExposureArea.setLineWrap(true);
        occupationalExposureArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(occupationalExposureArea), gbc);

        // 备注
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0; gbc.weighty = 0;
        panel.add(new JLabel("备注:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.weighty = 0.2;
        notesArea = new JTextArea(3, 20);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(notesArea), gbc);

        return panel;
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

        patientIdField.setText(String.valueOf(participant.getPatientId()));
        nameField.setText(participant.getParticipantName());
        genderComboBox.setSelectedItem(participant.getGender());
        
        if (participant.getDateOfBirth() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            birthDateField.setText(dateFormat.format(participant.getDateOfBirth()));
        }
        
        phoneField.setText(participant.getContactPhone());
        addressArea.setText(participant.getHomeAddress());
        educationComboBox.setSelectedItem(participant.getEducationLevel());
        occupationField.setText(participant.getOccupation());
        maritalStatusComboBox.setSelectedItem(participant.getMaritalStatus());
        incomeField.setText(participant.getHouseholdIncome());
        
        smokingStatusComboBox.setSelectedItem(participant.getSmokingStatus());
        drinkingStatusComboBox.setSelectedItem(participant.getDrinkingStatus());
        exerciseField.setText(participant.getExerciseFrequency());
        dietArea.setText(participant.getDietHabits());
        sleepQualityComboBox.setSelectedItem(participant.getSleepQuality());
        stressLevelComboBox.setSelectedItem(participant.getStressLevel());
        
        chronicDiseasesArea.setText(participant.getChronicDiseases());
        medicationHistoryArea.setText(participant.getMedicationHistory());
        allergyHistoryArea.setText(participant.getAllergyHistory());
        familyMedicalHistoryArea.setText(participant.getFamilyMedicalHistory());
        environmentalExposureArea.setText(participant.getEnvironmentalExposure());
        occupationalExposureArea.setText(participant.getOccupationalExposure());
        notesArea.setText(participant.getNotes());
    }

    private void saveParticipant() {
        try {
            // 验证必填字段
            if (nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入参与者姓名", "验证错误", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (patientIdField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入患者ID", "验证错误", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 创建或更新参与者对象
            if (participant == null) {
                participant = new SurveyParticipant();
            }

            // 设置基本信息
            participant.setPatientId(Integer.parseInt(patientIdField.getText().trim()));
            participant.setParticipantName(nameField.getText().trim());
            participant.setGender((String) genderComboBox.getSelectedItem());
            
            // 解析出生日期
            if (!birthDateField.getText().trim().isEmpty()) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date birthDate = dateFormat.parse(birthDateField.getText().trim());
                    participant.setDateOfBirth(birthDate);
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(this, "出生日期格式错误，请使用yyyy-MM-dd格式", "验证错误", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            
            participant.setContactPhone(phoneField.getText().trim());
            participant.setHomeAddress(addressArea.getText().trim());
            participant.setEducationLevel((String) educationComboBox.getSelectedItem());
            participant.setOccupation(occupationField.getText().trim());
            participant.setMaritalStatus((String) maritalStatusComboBox.getSelectedItem());
            participant.setHouseholdIncome(incomeField.getText().trim());
            
            // 设置生活习惯
            participant.setSmokingStatus((String) smokingStatusComboBox.getSelectedItem());
            participant.setDrinkingStatus((String) drinkingStatusComboBox.getSelectedItem());
            participant.setExerciseFrequency(exerciseField.getText().trim());
            participant.setDietHabits(dietArea.getText().trim());
            participant.setSleepQuality((String) sleepQualityComboBox.getSelectedItem());
            participant.setStressLevel((String) stressLevelComboBox.getSelectedItem());
            
            // 设置健康信息
            participant.setChronicDiseases(chronicDiseasesArea.getText().trim());
            participant.setMedicationHistory(medicationHistoryArea.getText().trim());
            participant.setAllergyHistory(allergyHistoryArea.getText().trim());
            participant.setFamilyMedicalHistory(familyMedicalHistoryArea.getText().trim());
            participant.setEnvironmentalExposure(environmentalExposureArea.getText().trim());
            participant.setOccupationalExposure(occupationalExposureArea.getText().trim());
            participant.setNotes(notesArea.getText().trim());

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
            JOptionPane.showMessageDialog(this, "患者ID必须是数字", "验证错误", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "保存失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
