package com.szz.view.patientView;

import com.szz.model.Clinical.Patient;

import javax.swing.*;
import java.awt.*;

public class PatientDetailDialog extends JDialog {
    private final Patient patient;

    public PatientDetailDialog(JFrame parent, Patient patient) {
        super(parent, "患者详情: " + patient.getName(), true);
        this.patient = patient;
        initUI();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // 患者基本信息
        tabbedPane.addTab("基本信息", createPatientInfoPanel());

        // 联系人信息
        tabbedPane.addTab("联系人", new ContactView(patient.getId()));

        // 医保信息
        tabbedPane.addTab("医保", new InsuranceView(patient.getId()));

        // 就诊记录
        tabbedPane.addTab("就诊记录", new ClinicalVisitView(patient.getId()));

        // 设置对话框大小和布局
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
        setSize(1000, 800);
    }

    private JPanel createPatientInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 添加基本信息字段
        addField(panel, gbc, "就诊号：", patient.getHospitalPatientId(), 0);
        addField(panel, gbc, "姓名：", patient.getName(), 1);
        addField(panel, gbc, "性别：", patient.getGender(), 2);
        addField(panel, gbc, "出生日期：", patient.getDateOfBirth() != null ? 
                new java.text.SimpleDateFormat("yyyy-MM-dd").format(patient.getDateOfBirth()) : "", 3);
        addField(panel, gbc, "家庭住址：", patient.getHomeAddress(), 4);
        addField(panel, gbc, "出生体重(kg)：", String.valueOf(patient.getBirthWeightKg()), 5);
        
        // 添加文本区域字段
        addTextArea(panel, gbc, "生活方式备注：", patient.getLifestyleNotes(), 6);
        addTextArea(panel, gbc, "食物过敏原阳性史：", patient.getPositiveFoodAllergenHistory(), 7);
        addTextArea(panel, gbc, "吸入过敏原阳性史：", patient.getPositiveInhaledAllergenHistory(), 8);
        addTextArea(panel, gbc, "过敏性疾病史：", patient.getAllergicDiseaseHistory(), 9);
        addTextArea(panel, gbc, "一级亲属过敏史：", patient.getFamilyAllergyHistoryDegree1(), 10);
        addTextArea(panel, gbc, "一级亲属过敏性疾病史：", patient.getFamilyAllergicDiseaseHistoryDegree1(), 11);
        addTextArea(panel, gbc, "二级亲属过敏史：", patient.getFamilyAllergyHistoryDegree2(), 12);
        addTextArea(panel, gbc, "二级亲属过敏性疾病史：", patient.getFamilyAllergicDiseaseHistoryDegree2(), 13);

        return panel;
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, String value, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        JTextField field = new JTextField(value, 30);
        field.setEditable(false);
        panel.add(field, gbc);
    }

    private void addTextArea(JPanel panel, GridBagConstraints gbc, String label, String value, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        JTextArea area = new JTextArea(value, 3, 30);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(area);
        panel.add(scrollPane, gbc);
    }
}