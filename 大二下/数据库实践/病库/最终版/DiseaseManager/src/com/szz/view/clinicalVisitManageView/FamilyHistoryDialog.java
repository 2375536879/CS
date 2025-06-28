package com.szz.view.clinicalVisitManageView;

import com.szz.model.Survey.FamilyHistorySurvey;
import com.szz.service.Survey.FamilyHistorySurveyService;

import javax.swing.*;
import java.awt.*;

public class FamilyHistoryDialog extends JDialog {
    private FamilyHistorySurveyService service;
    private FamilyHistorySurvey familyHistory;
    private boolean confirmed = false;

    private JComboBox<String> degreeComboBox;
    private JTextField relationshipField;
    private JCheckBox asthmaCheck;
    private JCheckBox eczemaCheck;
    private JCheckBox rhinitisCheck;
    private JCheckBox foodAllergyCheck;
    private JCheckBox smokingCheck;

    public FamilyHistoryDialog(JFrame parent, String title, FamilyHistorySurvey familyHistory, FamilyHistorySurveyService service) {
        super(parent, title, true);
        this.service = service;
        this.familyHistory = familyHistory != null ? familyHistory : new FamilyHistorySurvey();
        initUI();
        if (familyHistory != null) populateFields();
        setSize(400, 350);
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        form.add(new JLabel("亲属级别:"));
        degreeComboBox = new JComboBox<>(new String[]{"", "一级", "二级"});
        form.add(degreeComboBox);

        form.add(new JLabel("关系:"));
        relationshipField = new JTextField();
        form.add(relationshipField);

        form.add(new JLabel("疾病:"));
        JPanel diseasePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        asthmaCheck = new JCheckBox("哮喘");
        eczemaCheck = new JCheckBox("湿疹");
        rhinitisCheck = new JCheckBox("鼻炎");
        foodAllergyCheck = new JCheckBox("食物过敏");
        diseasePanel.add(asthmaCheck);
        diseasePanel.add(eczemaCheck);
        diseasePanel.add(rhinitisCheck);
        diseasePanel.add(foodAllergyCheck);
        form.add(diseasePanel);

        form.add(new JLabel("吸烟暴露:"));
        smokingCheck = new JCheckBox("有");
        form.add(smokingCheck);

        add(form, BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("保存");
        JButton cancelBtn = new JButton("取消");
        saveBtn.addActionListener(e -> onSave());
        cancelBtn.addActionListener(e -> dispose());
        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void populateFields() {
        degreeComboBox.setSelectedItem(familyHistory.getRelativeDegree());
        relationshipField.setText(familyHistory.getRelativeRelationship());
        asthmaCheck.setSelected(Boolean.TRUE.equals(familyHistory.getDiseaseAsthma()));
        eczemaCheck.setSelected(Boolean.TRUE.equals(familyHistory.getDiseaseEczema()));
        rhinitisCheck.setSelected(Boolean.TRUE.equals(familyHistory.getDiseaseRhinitis()));
        foodAllergyCheck.setSelected(Boolean.TRUE.equals(familyHistory.getDiseaseFoodAllergy()));
        smokingCheck.setSelected(Boolean.TRUE.equals(familyHistory.getFamilyEnvSimilaritySmoking()));
    }

    private void onSave() {
        familyHistory.setRelativeDegree((String) degreeComboBox.getSelectedItem());
        familyHistory.setRelativeRelationship(relationshipField.getText().trim());
        familyHistory.setDiseaseAsthma(asthmaCheck.isSelected());
        familyHistory.setDiseaseEczema(eczemaCheck.isSelected());
        familyHistory.setDiseaseRhinitis(rhinitisCheck.isSelected());
        familyHistory.setDiseaseFoodAllergy(foodAllergyCheck.isSelected());
        familyHistory.setFamilyEnvSimilaritySmoking(smokingCheck.isSelected());
        confirmed = true;
        dispose();
    }

    public boolean isConfirmed() { return confirmed; }
    public FamilyHistorySurvey getFamilyHistory() { return familyHistory; }
} 