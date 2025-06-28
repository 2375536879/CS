package com.szz.view.patientView;

import com.szz.model.Clinical.Patient;
import com.szz.service.Clinical.PatientService;
import com.szz.util.DateUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PatientManageView extends JFrame {

    private PatientService patientService = new PatientService();
    private JTable patientTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, viewDetailsButton;

    public PatientManageView() {
        setTitle("患者信息管理系统");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        loadPatientData();
    }

    private void initUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());

        // 顶部：搜索区域
        JPanel searchPanel = createSearchPanel();
        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // 中部：患者表格
        JScrollPane tableScrollPane = createPatientTable();
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // 底部：操作按钮
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("搜索患者"));

        JLabel searchLabel = new JLabel("姓名或就诊号:");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("搜索");
        searchButton.addActionListener(e -> searchPatients());

        JButton refreshButton = new JButton("刷新");
        refreshButton.addActionListener(e -> loadPatientData());

        panel.add(searchLabel);
        panel.add(searchField);
        panel.add(searchButton);
        panel.add(refreshButton);

        return panel;
    }

    private JScrollPane createPatientTable() {
        // 列名
        String[] columnNames = {
                "ID", "就诊号", "姓名", "性别", "出生日期", "家庭住址", "创建时间"
        };

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 所有单元格不可编辑
            }
        };

        patientTable = new JTable(tableModel);
        patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        patientTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = patientTable.getSelectedRow();
            boolean enabled = selectedRow != -1;
            editButton.setEnabled(enabled);
            deleteButton.setEnabled(enabled);
            viewDetailsButton.setEnabled(enabled);
        });

        // 设置列宽
        patientTable.getColumnModel().getColumn(0).setMaxWidth(40);   // ID
        patientTable.getColumnModel().getColumn(3).setMaxWidth(50);   // 性别
        patientTable.getColumnModel().getColumn(4).setMaxWidth(100);  // 出生日期
        patientTable.getColumnModel().getColumn(6).setMaxWidth(150); // 创建时间

        return new JScrollPane(patientTable);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        addButton = new JButton("添加患者");
        editButton = new JButton("编辑患者");
        deleteButton = new JButton("删除患者");
        viewDetailsButton = new JButton("查看详情");

        // 初始状态禁用编辑/删除/详情按钮
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        viewDetailsButton.setEnabled(false);


        addButton.addActionListener(e -> addNewPatient());
        editButton.addActionListener(e -> editSelectedPatient());
        deleteButton.addActionListener(e -> deleteSelectedPatient());
        viewDetailsButton.addActionListener(e -> viewPatientDetails());

        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(viewDetailsButton);

        return panel;
    }

    private void loadPatientData() {
        try {
            List<Patient> patients = patientService.getAllPatients();
            updateTable(patients);
        } catch (Exception e) {
            showErrorDialog("加载患者数据失败: " + e.getMessage());
        }
    }

    private void updateTable(List<Patient> patients) {
        tableModel.setRowCount(0); // 清空表格

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Patient patient : patients) {
            Object[] rowData = {
                    patient.getId(),
                    patient.getHospitalPatientId(),
                    patient.getName(),
                    patient.getGender(),
                    dateFormat.format(patient.getDateOfBirth()),
                    patient.getHomeAddress(),
                    patient.getCreatedAt() != null ? patient.getCreatedAt().toString() : ""
            };
            tableModel.addRow(rowData);
        }
    }

    private void searchPatients() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadPatientData();
            return;
        }

        try {
            List<Patient> patients = patientService.getAllPatients();
            // 简单过滤（实际应通过DAO查询）
            patients.removeIf(p ->
                    !p.getName().contains(keyword) &&
                            !p.getHospitalPatientId().contains(keyword)
            );
            updateTable(patients);
        } catch (Exception e) {
            showErrorDialog("搜索失败: " + e.getMessage());
        }
    }

    private void addNewPatient() {
        PatientDialog dialog = new PatientDialog(this, "添加患者", null);
        dialog.setVisible(true);
        Patient newPatient = dialog.getPatient();

        if (newPatient != null) {
            try {
                patientService.registerNewPatient(newPatient);
                loadPatientData();
                JOptionPane.showMessageDialog(this, "患者添加成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                showErrorDialog("添加患者失败: " + ex.getMessage());
            }
        }
    }

    private void editSelectedPatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) return;

        int patientId = (int) tableModel.getValueAt(selectedRow, 0);

        try {
            Patient patient = patientService.getPatientById(patientId);
            PatientDialog dialog = new PatientDialog(this, "编辑患者", patient);
            dialog.setVisible(true);
            Patient updatedPatient = dialog.getPatient();

            if (updatedPatient != null) {
                // 实现更新逻辑
                loadPatientData();
                JOptionPane.showMessageDialog(this, "患者信息更新成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            showErrorDialog("无法编辑患者: " + e.getMessage());
        }
    }

    private void deleteSelectedPatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) return;

        int patientId = (int) tableModel.getValueAt(selectedRow, 0);
        String patientName = (String) tableModel.getValueAt(selectedRow, 2);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "确定要删除患者 '" + patientName + "' 吗？此操作无法撤销！",
                "确认删除",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // TODO: 实现删除逻辑
                loadPatientData();
                JOptionPane.showMessageDialog(this, "患者删除成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                showErrorDialog("删除患者失败: " + e.getMessage());
            }
        }
    }

    private void viewPatientDetails() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) return;

        int patientId = (int) tableModel.getValueAt(selectedRow, 0);

        try {
            Patient patient = patientService.getPatientById(patientId);

            // 添加空值检查
            if (patient == null) {
                showErrorDialog("未找到ID为 " + patientId + " 的患者！");
                return;
            }

            PatientDetailDialog dialog = new PatientDetailDialog(this, patient);
            dialog.setVisible(true);
        } catch (Exception e) {
            showErrorDialog("无法查看患者详情: " + e.getMessage());
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "错误",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // 患者添加/编辑对话框
    private class PatientDialog extends JDialog {
        private Patient patient;
        private boolean confirmed = false;

        private JTextField hospitalPatientIdField, nameField;
        private JComboBox<String> genderComboBox;
        private JTextField dateOfBirthField;
        private JTextField homeAddressField, birthWeightField;
        private JTextArea lifestyleNotesArea, positiveFoodAllergenArea, positiveInhaledAllergenArea;
        private JTextArea allergicDiseaseArea, familyAllergyDegree1Area, familyAllergyDegree2Area;
        private JTextArea familyDiseaseDegree1Area, familyDiseaseDegree2Area;

        public PatientDialog(JFrame parent, String title, Patient existingPatient) {
            super(parent, title, true);
            this.patient = existingPatient != null ? existingPatient : new Patient();
            initUI();
            pack();
            setLocationRelativeTo(parent);
        }

        private void initUI() {
            JPanel mainPanel = new JPanel(new BorderLayout());

            // 基本信息表单
            JPanel formPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;

            // 第1行：就诊号
            gbc.gridx = 0; gbc.gridy = 0;
            formPanel.add(new JLabel("就诊号:"), gbc);
            gbc.gridx = 1;
            hospitalPatientIdField = new JTextField(20);
            hospitalPatientIdField.setText(patient.getHospitalPatientId());
            formPanel.add(hospitalPatientIdField, gbc);

            // 第2行：姓名
            gbc.gridx = 0; gbc.gridy++;
            formPanel.add(new JLabel("姓名:"), gbc);
            gbc.gridx = 1;
            nameField = new JTextField(20);
            nameField.setText(patient.getName());
            formPanel.add(nameField, gbc);

            // 第3行：性别
            gbc.gridx = 0; gbc.gridy++;
            formPanel.add(new JLabel("性别:"), gbc);
            gbc.gridx = 1;
            genderComboBox = new JComboBox<>(new String[]{"男", "女", "其他"});
            if (patient.getGender() != null) {
                genderComboBox.setSelectedItem(patient.getGender());
            }
            formPanel.add(genderComboBox, gbc);

            // 第4行：出生日期
            gbc.gridx = 0; gbc.gridy++;
            formPanel.add(new JLabel("出生日期:"), gbc);
            gbc.gridx = 1;
            dateOfBirthField = new JTextField(20);
            if (patient.getDateOfBirth() != null) {
                dateOfBirthField.setText(new SimpleDateFormat("yyyy-MM-dd").format(patient.getDateOfBirth()));
            }
            formPanel.add(dateOfBirthField, gbc);

            // 第5行：家庭地址
            gbc.gridx = 0; gbc.gridy++;
            formPanel.add(new JLabel("家庭地址:"), gbc);
            gbc.gridx = 1;
            homeAddressField = new JTextField(20);
            homeAddressField.setText(patient.getHomeAddress());
            formPanel.add(homeAddressField, gbc);

            // 第6行：出生体重
            gbc.gridx = 0; gbc.gridy++;
            formPanel.add(new JLabel("出生体重(kg):"), gbc);
            gbc.gridx = 1;
            birthWeightField = new JTextField(10);
            if (patient.getBirthWeightKg() != null) {
                birthWeightField.setText(patient.getBirthWeightKg().toString());
            }
            formPanel.add(birthWeightField, gbc);

            // 添加选项卡面板
            JTabbedPane tabbedPane = new JTabbedPane();

            // 生活方式选项卡
            tabbedPane.addTab("生活方式", createLifestylePanel());

            // 过敏史选项卡
            tabbedPane.addTab("过敏史", createAllergyPanel());

            // 家族史选项卡
            tabbedPane.addTab("家族史", createFamilyHistoryPanel());

            gbc.gridx = 0; gbc.gridy++;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.BOTH;
            formPanel.add(tabbedPane, gbc);

            mainPanel.add(formPanel, BorderLayout.CENTER);

            // 底部按钮
            JPanel buttonPanel = new JPanel();
            JButton saveButton = new JButton("保存");
            saveButton.addActionListener(e -> {
                if (validateFields()) {
                    confirmed = true;
                    dispose();
                }
            });

            JButton cancelButton = new JButton("取消");
            cancelButton.addActionListener(e -> dispose());

            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            add(mainPanel);
        }

        private JPanel createLifestylePanel() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel label = new JLabel("生活方式备注:");
            lifestyleNotesArea = new JTextArea(5, 40);
            if (patient.getLifestyleNotes() != null) {
                lifestyleNotesArea.setText(patient.getLifestyleNotes());
            }

            panel.add(label, BorderLayout.NORTH);
            panel.add(new JScrollPane(lifestyleNotesArea), BorderLayout.CENTER);

            return panel;
        }

        private JPanel createAllergyPanel() {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // 食物过敏史
            JLabel foodLabel = new JLabel("食物过敏原阳性史:");
            positiveFoodAllergenArea = new JTextArea(3, 40);
            if (patient.getPositiveFoodAllergenHistory() != null) {
                positiveFoodAllergenArea.setText(patient.getPositiveFoodAllergenHistory());
            }

            // 吸入过敏史
            JLabel inhaledLabel = new JLabel("吸入过敏原阳性史:");
            positiveInhaledAllergenArea = new JTextArea(3, 40);
            if (patient.getPositiveInhaledAllergenHistory() != null) {
                positiveInhaledAllergenArea.setText(patient.getPositiveInhaledAllergenHistory());
            }

            // 过敏性疾病史
            JLabel diseaseLabel = new JLabel("过敏性疾病史:");
            allergicDiseaseArea = new JTextArea(3, 40);
            if (patient.getAllergicDiseaseHistory() != null) {
                allergicDiseaseArea.setText(patient.getAllergicDiseaseHistory());
            }

            panel.add(foodLabel);
            panel.add(new JScrollPane(positiveFoodAllergenArea));
            panel.add(Box.createVerticalStrut(10));
            panel.add(inhaledLabel);
            panel.add(new JScrollPane(positiveInhaledAllergenArea));
            panel.add(Box.createVerticalStrut(10));
            panel.add(diseaseLabel);
            panel.add(new JScrollPane(allergicDiseaseArea));

            return panel;
        }

        private JPanel createFamilyHistoryPanel() {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // 一代亲属过敏史
            JLabel deg1AllergyLabel = new JLabel("一代亲属过敏史:");
            familyAllergyDegree1Area = new JTextArea(3, 40);
            if (patient.getFamilyAllergyHistoryDegree1() != null) {
                familyAllergyDegree1Area.setText(patient.getFamilyAllergyHistoryDegree1());
            }

            // 一代亲属过敏性疾病史
            JLabel deg1DiseaseLabel = new JLabel("一代亲属过敏性疾病史:");
            familyDiseaseDegree1Area = new JTextArea(3, 40);
            if (patient.getFamilyAllergicDiseaseHistoryDegree1() != null) {
                familyDiseaseDegree1Area.setText(patient.getFamilyAllergicDiseaseHistoryDegree1());
            }

            // 二代亲属过敏史
            JLabel deg2AllergyLabel = new JLabel("二代亲属过敏史:");
            familyAllergyDegree2Area = new JTextArea(3, 40);
            if (patient.getFamilyAllergyHistoryDegree2() != null) {
                familyAllergyDegree2Area.setText(patient.getFamilyAllergyHistoryDegree2());
            }

            // 二代亲属过敏性疾病史
            JLabel deg2DiseaseLabel = new JLabel("二代亲属过敏性疾病史:");
            familyDiseaseDegree2Area = new JTextArea(3, 40);
            if (patient.getFamilyAllergicDiseaseHistoryDegree2() != null) {
                familyDiseaseDegree2Area.setText(patient.getFamilyAllergicDiseaseHistoryDegree2());
            }

            panel.add(deg1AllergyLabel);
            panel.add(new JScrollPane(familyAllergyDegree1Area));
            panel.add(Box.createVerticalStrut(10));
            panel.add(deg1DiseaseLabel);
            panel.add(new JScrollPane(familyDiseaseDegree1Area));
            panel.add(Box.createVerticalStrut(10));
            panel.add(deg2AllergyLabel);
            panel.add(new JScrollPane(familyAllergyDegree2Area));
            panel.add(Box.createVerticalStrut(10));
            panel.add(deg2DiseaseLabel);
            panel.add(new JScrollPane(familyDiseaseDegree2Area));

            return panel;
        }

        private boolean validateFields() {
            // 必填字段验证
            if (hospitalPatientIdField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "就诊号不能为空", "输入错误", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            if (nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "姓名不能为空", "输入错误", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            // 日期格式验证
            if (!dateOfBirthField.getText().isEmpty()) {
                try {
                    DateUtil.parseDate(dateOfBirthField.getText());
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(this, "出生日期格式错误 (应为yyyy-MM-dd)", "输入错误", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            }

            // 数值格式验证
            if (!birthWeightField.getText().isEmpty()) {
                try {
                    Double.parseDouble(birthWeightField.getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "出生体重必须是数字", "输入错误", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            }

            return true;
        }

        public Patient getPatient() {
            if (!confirmed) return null;

            try {
                patient.setHospitalPatientId(hospitalPatientIdField.getText().trim());
                patient.setName(nameField.getText().trim());
                patient.setGender(genderComboBox.getSelectedItem().toString());

                if (!dateOfBirthField.getText().isEmpty()) {
                    patient.setDateOfBirth(DateUtil.parseDate(dateOfBirthField.getText()));
                }

                patient.setHomeAddress(homeAddressField.getText().trim());

                if (!birthWeightField.getText().isEmpty()) {
                    patient.setBirthWeightKg(Double.parseDouble(birthWeightField.getText()));
                }

                patient.setLifestyleNotes(lifestyleNotesArea.getText().trim());
                patient.setPositiveFoodAllergenHistory(positiveFoodAllergenArea.getText().trim());
                patient.setPositiveInhaledAllergenHistory(positiveInhaledAllergenArea.getText().trim());
                patient.setAllergicDiseaseHistory(allergicDiseaseArea.getText().trim());
                patient.setFamilyAllergyHistoryDegree1(familyAllergyDegree1Area.getText().trim());
                patient.setFamilyAllergicDiseaseHistoryDegree1(familyDiseaseDegree1Area.getText().trim());
                patient.setFamilyAllergyHistoryDegree2(familyAllergyDegree2Area.getText().trim());
                patient.setFamilyAllergicDiseaseHistoryDegree2(familyDiseaseDegree2Area.getText().trim());

            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "日期格式错误", "数据错误", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            return patient;
        }
    }

    // 患者详情查看对话框
    private class PatientDetailDialog extends JDialog {
        public PatientDetailDialog(JFrame parent, Patient patient) {
            super(parent, "患者详情: " + patient.getName(), true);
            initUI(patient);
            pack();
            setLocationRelativeTo(parent);
        }

        private void initUI(Patient patient) {
            JTabbedPane tabbedPane = new JTabbedPane();

            // 患者基本信息
            tabbedPane.addTab("基本信息", createPatientInfoPanel(patient));

            // 联系人信息
            tabbedPane.addTab("联系人", new com.szz.view.patientView.ContactsView(patient.getId()));

            // 医保信息
            tabbedPane.addTab("医保", new com.szz.view.patientView.InsuranceView(patient.getId()));

            // 就诊记录
            tabbedPane.addTab("就诊记录", new com.szz.view.patientView.ClinicalVisitView(patient.getId()));

            // 其他相关信息（仅查看）
            tabbedPane.addTab("其他信息", createOtherInfoPanel(patient.getId()));

            add(tabbedPane);
        }

        private JComponent createPatientInfoPanel(Patient patient) {
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // 创建标题
            JLabel titleLabel = new JLabel("患者基本信息", JLabel.CENTER);
            titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
            titleLabel.setForeground(new Color(51, 102, 153));
            titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
            mainPanel.add(titleLabel, BorderLayout.NORTH);

            // 创建信息面板
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(Color.WHITE);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // 基本信息卡片
            JPanel basicCard = createInfoCard("基本信息", new Color(230, 240, 255));
            addInfoRow(basicCard, "患者ID", String.valueOf(patient.getId()));
            addInfoRow(basicCard, "就诊号", patient.getHospitalPatientId());
            addInfoRow(basicCard, "姓名", patient.getName());
            addInfoRow(basicCard, "性别", patient.getGender());
            addInfoRow(basicCard, "出生日期", patient.getDateOfBirth() != null ? dateFormat.format(patient.getDateOfBirth()) : "");
            addInfoRow(basicCard, "家庭住址", patient.getHomeAddress());
            addInfoRow(basicCard, "出生体重", patient.getBirthWeightKg() != null ? patient.getBirthWeightKg() + " kg" : "");
            infoPanel.add(basicCard);
            infoPanel.add(Box.createVerticalStrut(15));

            // 过敏史卡片
            JPanel allergyCard = createInfoCard("过敏史信息", new Color(255, 240, 240));
            addInfoRow(allergyCard, "食物过敏原阳性史", patient.getPositiveFoodAllergenHistory());
            addInfoRow(allergyCard, "吸入过敏原阳性史", patient.getPositiveInhaledAllergenHistory());
            addInfoRow(allergyCard, "过敏性疾病史", patient.getAllergicDiseaseHistory());
            infoPanel.add(allergyCard);
            infoPanel.add(Box.createVerticalStrut(15));

            // 家族史卡片
            JPanel familyCard = createInfoCard("家族史信息", new Color(240, 255, 240));
            addInfoRow(familyCard, "一代亲属过敏史", patient.getFamilyAllergyHistoryDegree1());
            addInfoRow(familyCard, "一代亲属过敏性疾病史", patient.getFamilyAllergicDiseaseHistoryDegree1());
            addInfoRow(familyCard, "二代亲属过敏史", patient.getFamilyAllergyHistoryDegree2());
            addInfoRow(familyCard, "二代亲属过敏性疾病史", patient.getFamilyAllergicDiseaseHistoryDegree2());
            infoPanel.add(familyCard);
            infoPanel.add(Box.createVerticalStrut(15));

            // 生活方式卡片
            if (patient.getLifestyleNotes() != null && !patient.getLifestyleNotes().trim().isEmpty()) {
                JPanel lifestyleCard = createInfoCard("生活方式", new Color(255, 255, 230));
                addInfoRow(lifestyleCard, "生活方式备注", patient.getLifestyleNotes());
                infoPanel.add(lifestyleCard);
            }

            // 创建带滚动条的面板
            JScrollPane scrollPane = new JScrollPane(infoPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setBorder(null);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);

            mainPanel.add(scrollPane, BorderLayout.CENTER);
            return mainPanel;
        }

        private JPanel createInfoCard(String title, Color backgroundColor) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));
            card.setBackground(backgroundColor);

            // 添加标题
            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
            titleLabel.setForeground(new Color(51, 51, 51));
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            card.add(titleLabel);
            card.add(Box.createVerticalStrut(10));

            return card;
        }

        private void addInfoRow(JPanel parent, String label, String value) {
            if (value == null || value.trim().isEmpty()) {
                return; // 不显示空值
            }

            JPanel row = new JPanel(new BorderLayout());
            row.setOpaque(false);
            row.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel labelComponent = new JLabel(label + ":");
            labelComponent.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            labelComponent.setForeground(new Color(102, 102, 102));
            labelComponent.setPreferredSize(new Dimension(120, 25));

            JLabel valueComponent = new JLabel("<html><div style='width: 300px;'>" + value + "</div></html>");
            valueComponent.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            valueComponent.setForeground(new Color(51, 51, 51));

            row.add(labelComponent, BorderLayout.WEST);
            row.add(valueComponent, BorderLayout.CENTER);

            parent.add(row);
            parent.add(Box.createVerticalStrut(8));
        }



        private JComponent createOtherInfoPanel(int patientId) {
            JTabbedPane otherInfoTabs = new JTabbedPane();

            // 添加各种医疗信息标签页
            otherInfoTabs.addTab("实验室检查", new com.szz.view.patientView.LabExaminationView(patientId, true));
            otherInfoTabs.addTab("肺功能检查", new com.szz.view.patientView.PulmonaryFunctionTestView(patientId, true));
            otherInfoTabs.addTab("呼出气一氧化氮", new com.szz.view.patientView.ExhaledNitricOxideTestView(patientId, true));
            otherInfoTabs.addTab("影像学检查", new com.szz.view.patientView.ImagingStudyView(patientId, true));
            otherInfoTabs.addTab("疾病诊断", new com.szz.view.patientView.DiagnosisView(patientId, true));
            otherInfoTabs.addTab("用药记录", new com.szz.view.patientView.MedicationView(patientId, true));
            otherInfoTabs.addTab("既往用药史", new com.szz.view.patientView.PastMedicationHistoryView(patientId));
            otherInfoTabs.addTab("其他辅助治疗", new com.szz.view.patientView.OtherAuxiliaryTreatmentView(patientId, true));
            otherInfoTabs.addTab("医疗费用", new com.szz.view.patientView.MedicalCostView(patientId, true));

            return otherInfoTabs;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PatientManageView().setVisible(true));
    }
}