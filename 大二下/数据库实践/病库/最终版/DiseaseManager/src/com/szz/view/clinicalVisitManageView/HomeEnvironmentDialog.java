package com.szz.view.clinicalVisitManageView;

import com.szz.model.Survey.HomeEnvironmentExposure;
import com.szz.service.Survey.HomeEnvironmentExposureService;

import javax.swing.*;
import java.awt.*;

public class HomeEnvironmentDialog extends JDialog {
    private HomeEnvironmentExposureService service;
    private HomeEnvironmentExposure homeEnv;
    private boolean confirmed = false;

    private JComboBox<String> housingTypeCombo;
    private JComboBox<String> buildingMaterialCombo;
    private JComboBox<String> ventilationCombo;
    private JCheckBox hasCarpetCheck;
    private JCheckBox hasPetsCheck;
    private JTextField petTypeField;


    public HomeEnvironmentDialog(JFrame parent, String title, HomeEnvironmentExposure homeEnv, HomeEnvironmentExposureService service) {
        super(parent, title, true);
        this.service = service;
        this.homeEnv = homeEnv != null ? homeEnv : new HomeEnvironmentExposure();
        initUI();
        if (homeEnv != null) populateFields();
        setSize(400, 350);
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        form.add(new JLabel("房屋类型:"));
        housingTypeCombo = new JComboBox<>(new String[]{"", "平房", "楼房", "别墅", "其他"});
        form.add(housingTypeCombo);

        form.add(new JLabel("建筑材料:"));
        buildingMaterialCombo = new JComboBox<>(new String[]{"", "木质", "混凝土", "其他"});
        form.add(buildingMaterialCombo);

        form.add(new JLabel("通风频率:"));
        ventilationCombo = new JComboBox<>(new String[]{"", "每日", "每周", "偶尔"});
        form.add(ventilationCombo);

        form.add(new JLabel("有地毯:"));
        hasCarpetCheck = new JCheckBox("有");
        form.add(hasCarpetCheck);

        form.add(new JLabel("有宠物:"));
        hasPetsCheck = new JCheckBox("有");
        form.add(hasPetsCheck);

        form.add(new JLabel("宠物类型:"));
        petTypeField = new JTextField();
        form.add(petTypeField);



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
        housingTypeCombo.setSelectedItem(homeEnv.getHousingType());
        hasCarpetCheck.setSelected(Boolean.TRUE.equals(homeEnv.getHasCarpets()));
        hasPetsCheck.setSelected(Boolean.TRUE.equals(homeEnv.getHasPets()));
        petTypeField.setText(homeEnv.getPetTypes());
    }

    private void onSave() {
        homeEnv.setHousingType((String) housingTypeCombo.getSelectedItem());
        homeEnv.setHasCarpets(hasCarpetCheck.isSelected());
        homeEnv.setHasPets(hasPetsCheck.isSelected());
        homeEnv.setPetTypes(petTypeField.getText().trim());
        confirmed = true;
        dispose();
    }

    public boolean isConfirmed() { return confirmed; }
    public HomeEnvironmentExposure getHomeEnvironment() { return homeEnv; }
} 