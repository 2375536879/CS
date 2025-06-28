package com.szz.view.clinicalVisitManageView;

import com.szz.model.Survey.SurveyParticipant;
import com.szz.model.Survey.FamilyHistorySurvey;
import com.szz.model.Survey.HomeEnvironmentExposure;
import com.szz.service.Survey.SurveyParticipantService;
import com.szz.service.Survey.FamilyHistorySurveyService;
import com.szz.service.Survey.HomeEnvironmentExposureService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class PatientSurveyManagementView extends JPanel {
    private int patientId;
    private SurveyParticipantService surveyParticipantService;
    private FamilyHistorySurveyService familyHistoryService;
    private HomeEnvironmentExposureService homeEnvironmentService;
    
    private JTable participantTable;
    private DefaultTableModel participantTableModel;
    private JTable familyHistoryTable;
    private DefaultTableModel familyHistoryTableModel;
    private JTable homeEnvironmentTable;
    private DefaultTableModel homeEnvironmentTableModel;
    private JTextField familyHistorySearchField;
    private JComboBox<String> diseaseComboBox;

    public PatientSurveyManagementView(int patientId) {
        this.patientId = patientId;
        this.surveyParticipantService = new SurveyParticipantService();
        this.familyHistoryService = new FamilyHistorySurveyService();
        this.homeEnvironmentService = new HomeEnvironmentExposureService();
        
        initUI();
        loadData();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 创建标题面板
        JPanel titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);

        // 创建标签页面板
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 12));

        // 流调参与者标签页
        tabbedPane.addTab("流调参与者", createParticipantPanel());
        
        // 家族史标签页
        tabbedPane.addTab("家族史", createFamilyHistoryPanel());
        
        // 家庭环境标签页
        tabbedPane.addTab("家庭环境", createHomeEnvironmentPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel titleLabel = new JLabel("患者流调数据管理");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        titleLabel.setForeground(new Color(51, 51, 51));

        JLabel subtitleLabel = new JLabel("患者ID: " + patientId);
        subtitleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(102, 102, 102));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(subtitleLabel);

        panel.add(textPanel, BorderLayout.WEST);
        return panel;
    }

    private JPanel createParticipantPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 创建表格
        String[] columnNames = {"ID", "参与者姓名", "性别", "出生日期", "联系电话", "教育水平", "职业", "创建时间"};
        participantTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        participantTable = new JTable(participantTableModel);
        participantTable.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        participantTable.setRowHeight(30);
        participantTable.setGridColor(new Color(230, 230, 230));
        participantTable.setSelectionBackground(new Color(240, 248, 255));

        // 设置表头样式
        participantTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        participantTable.getTableHeader().setBackground(new Color(248, 249, 250));

        JScrollPane scrollPane = new JScrollPane(participantTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        panel.add(scrollPane, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton addButton = new JButton("新增参与者");
        JButton editButton = new JButton("编辑");
        JButton deleteButton = new JButton("删除");
        JButton viewButton = new JButton("查看详情");

        // 设置按钮样式
        JButton[] buttons = {addButton, editButton, deleteButton, viewButton};
        Color[] colors = {
                new Color(92, 184, 92),   // 绿色
                new Color(51, 122, 183),  // 蓝色
                new Color(217, 83, 79),   // 红色
                new Color(91, 192, 222)   // 浅蓝色
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setFont(new Font("微软雅黑", Font.PLAIN, 12));
            buttons[i].setPreferredSize(new Dimension(100, 30));
            buttons[i].setBackground(colors[i]);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            buttonPanel.add(buttons[i]);
        }

        // 添加事件监听器
        addButton.addActionListener(e -> addParticipant());
        editButton.addActionListener(e -> editParticipant());
        deleteButton.addActionListener(e -> deleteParticipant());
        viewButton.addActionListener(e -> viewParticipantDetails());

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createFamilyHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 搜索面板
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        familyHistorySearchField = new JTextField(12);
        familyHistorySearchField.setToolTipText("按家族成员关系搜索，如父亲、母亲");
        diseaseComboBox = new JComboBox<>(new String[]{"", "哮喘", "湿疹", "鼻炎", "食物过敏"});
        diseaseComboBox.setToolTipText("按疾病筛选");
        JButton searchButton = new JButton("搜索");
        searchButton.addActionListener(e -> performFamilyHistorySearch());
        JButton resetButton = new JButton("重置");
        resetButton.addActionListener(e -> { familyHistorySearchField.setText(""); diseaseComboBox.setSelectedIndex(0); loadFamilyHistoryData(); });
        searchPanel.add(new JLabel("成员关系:"));
        searchPanel.add(familyHistorySearchField);
        searchPanel.add(new JLabel("疾病:"));
        searchPanel.add(diseaseComboBox);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);
        panel.add(searchPanel, BorderLayout.NORTH);

        // 创建表格
        String[] columnNames = {"ID", "参与者ID", "家族成员姓名", "关系", "性别", "疾病状况", "诊断时年龄", "当前状态"};
        familyHistoryTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        familyHistoryTable = new JTable(familyHistoryTableModel);
        familyHistoryTable.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        familyHistoryTable.setRowHeight(30);
        familyHistoryTable.setGridColor(new Color(230, 230, 230));
        familyHistoryTable.setSelectionBackground(new Color(240, 248, 255));

        // 设置表头样式
        familyHistoryTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        familyHistoryTable.getTableHeader().setBackground(new Color(248, 249, 250));

        JScrollPane scrollPane = new JScrollPane(familyHistoryTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        panel.add(scrollPane, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton addButton = new JButton("新增家族史");
        JButton editButton = new JButton("编辑");
        JButton deleteButton = new JButton("删除");

        // 设置按钮样式
        JButton[] buttons = {addButton, editButton, deleteButton};
        Color[] colors = {
                new Color(92, 184, 92),   // 绿色
                new Color(51, 122, 183),  // 蓝色
                new Color(217, 83, 79)    // 红色
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setFont(new Font("微软雅黑", Font.PLAIN, 12));
            buttons[i].setPreferredSize(new Dimension(100, 30));
            buttons[i].setBackground(colors[i]);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            buttonPanel.add(buttons[i]);
        }

        // 添加事件监听器
        addButton.addActionListener(e -> addFamilyHistory());
        editButton.addActionListener(e -> editFamilyHistory());
        deleteButton.addActionListener(e -> deleteFamilyHistory());

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createHomeEnvironmentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 创建表格
        String[] columnNames = {"ID", "参与者ID", "住房类型", "建筑年龄", "有宠物", "室内吸烟", "化学暴露", "环境风险"};
        homeEnvironmentTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        homeEnvironmentTable = new JTable(homeEnvironmentTableModel);
        homeEnvironmentTable.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        homeEnvironmentTable.setRowHeight(30);
        homeEnvironmentTable.setGridColor(new Color(230, 230, 230));
        homeEnvironmentTable.setSelectionBackground(new Color(240, 248, 255));

        // 设置表头样式
        homeEnvironmentTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        homeEnvironmentTable.getTableHeader().setBackground(new Color(248, 249, 250));

        JScrollPane scrollPane = new JScrollPane(homeEnvironmentTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        panel.add(scrollPane, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton addButton = new JButton("新增环境记录");
        JButton editButton = new JButton("编辑");
        JButton deleteButton = new JButton("删除");

        // 设置按钮样式
        JButton[] buttons = {addButton, editButton, deleteButton};
        Color[] colors = {
                new Color(92, 184, 92),   // 绿色
                new Color(51, 122, 183),  // 蓝色
                new Color(217, 83, 79)    // 红色
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setFont(new Font("微软雅黑", Font.PLAIN, 12));
            buttons[i].setPreferredSize(new Dimension(120, 30));
            buttons[i].setBackground(colors[i]);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            buttonPanel.add(buttons[i]);
        }

        // 添加事件监听器
        addButton.addActionListener(e -> addHomeEnvironment());
        editButton.addActionListener(e -> editHomeEnvironment());
        deleteButton.addActionListener(e -> deleteHomeEnvironment());

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void loadData() {
        loadParticipantData();
        loadFamilyHistoryData();
        loadHomeEnvironmentData();
    }

    private void loadParticipantData() {
        try {
            List<SurveyParticipant> participants = surveyParticipantService.getSurveyParticipantsByPatientId(patientId);
            updateParticipantTable(participants);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "加载流调参与者数据失败: " + e.getMessage(), 
                    "错误", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateParticipantTable(List<SurveyParticipant> participants) {
        participantTableModel.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (SurveyParticipant participant : participants) {
            Object[] row = {
                    participant.getId(),
                    participant.getParticipantName(),
                    participant.getGender(),
                    participant.getDateOfBirth() != null ? dateFormat.format(participant.getDateOfBirth()) : "",
                    participant.getContactPhone(),
                    participant.getEducationLevel(),
                    participant.getOccupation(),
                    participant.getCreatedAt() != null ? dateFormat.format(participant.getCreatedAt()) : ""
            };
            participantTableModel.addRow(row);
        }
    }

    private void loadFamilyHistoryData() {
        try {
            // 获取该患者的所有流调参与者
            List<SurveyParticipant> participants = surveyParticipantService.getSurveyParticipantsByPatientId(patientId);
            familyHistoryTableModel.setRowCount(0);

            for (SurveyParticipant participant : participants) {
                List<FamilyHistorySurvey> familyHistories = familyHistoryService.getFamilyHistorySurveysByParticipantId(participant.getId());
                
                for (FamilyHistorySurvey familyHistory : familyHistories) {
                    Object[] row = {
                            familyHistory.getId(),
                            familyHistory.getParticipantId(),
                            familyHistory.getFamilyMemberName(),
                            familyHistory.getRelationshipToParticipant(),
                            familyHistory.getGender(),
                            familyHistory.getMedicalCondition(),
                            familyHistory.getAgeAtDiagnosis(),
                            familyHistory.getCurrentStatus()
                    };
                    familyHistoryTableModel.addRow(row);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "加载家族史数据失败: " + e.getMessage(), 
                    "错误", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadHomeEnvironmentData() {
        try {
            // 获取该患者的所有流调参与者
            List<SurveyParticipant> participants = surveyParticipantService.getSurveyParticipantsByPatientId(patientId);
            homeEnvironmentTableModel.setRowCount(0);

            for (SurveyParticipant participant : participants) {
                List<HomeEnvironmentExposure> homeEnvironments = homeEnvironmentService.getHomeEnvironmentExposuresByParticipantId(participant.getId());
                
                for (HomeEnvironmentExposure homeEnv : homeEnvironments) {
                    String riskAssessment = homeEnvironmentService.getEnvironmentalRiskAssessment(homeEnv);
                    
                    Object[] row = {
                            homeEnv.getId(),
                            homeEnv.getParticipantId(),
                            homeEnv.getHousingType(),
                            homeEnv.getBuildingAge() != null ? homeEnv.getBuildingAge() + "年" : "",
                            homeEnv.getHasPets() != null ? (homeEnv.getHasPets() ? "是" : "否") : "",
                            homeEnv.getHasSmokingIndoors() != null ? (homeEnv.getHasSmokingIndoors() ? "是" : "否") : "",
                            homeEnv.getHasChemicalExposure() != null ? (homeEnv.getHasChemicalExposure() ? "是" : "否") : "",
                            riskAssessment
                    };
                    homeEnvironmentTableModel.addRow(row);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "加载家庭环境数据失败: " + e.getMessage(), 
                    "错误", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // 流调参与者操作方法
    private void addParticipant() {
        SurveyParticipant newParticipant = new SurveyParticipant();
        newParticipant.setPatientId(patientId);
        
        SurveyParticipantDialog dialog = new SurveyParticipantDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this), 
                "新增流调参与者", 
                newParticipant, 
                surveyParticipantService
        );
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            loadParticipantData();
        }
    }

    private void editParticipant() {
        int selectedRow = participantTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要编辑的参与者", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int participantId = (Integer) participantTableModel.getValueAt(selectedRow, 0);
        try {
            SurveyParticipant participant = surveyParticipantService.getSurveyParticipantById(participantId);
            if (participant != null) {
                SurveyParticipantDialog dialog = new SurveyParticipantDialog(
                        (JFrame) SwingUtilities.getWindowAncestor(this), 
                        "编辑流调参与者", 
                        participant, 
                        surveyParticipantService
                );
                dialog.setVisible(true);
                
                if (dialog.isConfirmed()) {
                    loadParticipantData();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "获取参与者信息失败: " + e.getMessage(), 
                    "错误", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteParticipant() {
        int selectedRow = participantTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的参与者", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(this, 
                "确定要删除选中的参与者吗？此操作不可撤销。", 
                "确认删除", 
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            int participantId = (Integer) participantTableModel.getValueAt(selectedRow, 0);
            try {
                boolean success = surveyParticipantService.deleteSurveyParticipant(participantId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    loadData(); // 重新加载所有数据
                } else {
                    JOptionPane.showMessageDialog(this, "删除失败", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                        "删除失败: " + e.getMessage(), 
                        "错误", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewParticipantDetails() {
        int selectedRow = participantTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要查看的参与者", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int participantId = (Integer) participantTableModel.getValueAt(selectedRow, 0);
        try {
            SurveyParticipant participant = surveyParticipantService.getSurveyParticipantById(participantId);
            if (participant != null) {
                SurveyParticipantDetailDialog detailDialog = new SurveyParticipantDetailDialog(
                        (JFrame) SwingUtilities.getWindowAncestor(this), 
                        participant
                );
                detailDialog.setVisible(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "获取参与者详情失败: " + e.getMessage(), 
                    "错误", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // 家族史操作方法
    private void addFamilyHistory() {
        int selectedParticipantRow = participantTable.getSelectedRow();
        if (selectedParticipantRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择一个流调参与者", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int participantId = (Integer) participantTableModel.getValueAt(selectedParticipantRow, 0);
        FamilyHistorySurvey newHistory = new FamilyHistorySurvey();
        newHistory.setSurveyParticipantId(participantId);
        FamilyHistoryDialog dialog = new FamilyHistoryDialog((JFrame) SwingUtilities.getWindowAncestor(this), "新增家族史", newHistory, familyHistoryService);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            familyHistoryService.createFamilyHistorySurvey(dialog.getFamilyHistory());
            loadFamilyHistoryData();
        }
    }

    private void editFamilyHistory() {
        int selectedRow = familyHistoryTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要编辑的家族史记录", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (Integer) familyHistoryTableModel.getValueAt(selectedRow, 0);
        FamilyHistorySurvey history = familyHistoryService.getFamilyHistorySurveyById(id);
        FamilyHistoryDialog dialog = new FamilyHistoryDialog((JFrame) SwingUtilities.getWindowAncestor(this), "编辑家族史", history, familyHistoryService);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            familyHistoryService.updateFamilyHistorySurvey(dialog.getFamilyHistory());
            loadFamilyHistoryData();
        }
    }

    private void deleteFamilyHistory() {
        int selectedRow = familyHistoryTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的家族史记录", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (Integer) familyHistoryTableModel.getValueAt(selectedRow, 0);
        int result = JOptionPane.showConfirmDialog(this, "确定要删除选中的家族史记录吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            familyHistoryService.deleteFamilyHistorySurvey(id);
            loadFamilyHistoryData();
        }
    }

    // 家庭环境操作方法
    private void addHomeEnvironment() {
        int selectedParticipantRow = participantTable.getSelectedRow();
        if (selectedParticipantRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择一个流调参与者", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int participantId = (Integer) participantTableModel.getValueAt(selectedParticipantRow, 0);
        HomeEnvironmentExposure newEnv = new HomeEnvironmentExposure();
        newEnv.setParticipantId(participantId);
        HomeEnvironmentDialog dialog = new HomeEnvironmentDialog((JFrame) SwingUtilities.getWindowAncestor(this), "新增家庭环境暴露", newEnv, homeEnvironmentService);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            homeEnvironmentService.createHomeEnvironmentExposure(dialog.getHomeEnvironment());
            loadHomeEnvironmentData();
        }
    }

    private void editHomeEnvironment() {
        int selectedRow = homeEnvironmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要编辑的家庭环境记录", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (Integer) homeEnvironmentTableModel.getValueAt(selectedRow, 0);
        HomeEnvironmentExposure env = homeEnvironmentService.getHomeEnvironmentExposureById(id);
        HomeEnvironmentDialog dialog = new HomeEnvironmentDialog((JFrame) SwingUtilities.getWindowAncestor(this), "编辑家庭环境暴露", env, homeEnvironmentService);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            homeEnvironmentService.updateHomeEnvironmentExposure(dialog.getHomeEnvironment());
            loadHomeEnvironmentData();
        }
    }

    private void deleteHomeEnvironment() {
        int selectedRow = homeEnvironmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的家庭环境记录", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (Integer) homeEnvironmentTableModel.getValueAt(selectedRow, 0);
        int result = JOptionPane.showConfirmDialog(this, "确定要删除选中的家庭环境记录吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            homeEnvironmentService.deleteHomeEnvironmentExposure(id);
            loadHomeEnvironmentData();
        }
    }

    private void performFamilyHistorySearch() {
        String relationship = familyHistorySearchField.getText().trim();
        String disease = (String) diseaseComboBox.getSelectedItem();
        String diseaseField = null;
        if (disease != null && !disease.isEmpty()) {
            switch (disease) {
                case "哮喘": diseaseField = "disease_asthma"; break;
                case "湿疹": diseaseField = "disease_eczema"; break;
                case "鼻炎": diseaseField = "disease_rhinitis"; break;
                case "食物过敏": diseaseField = "disease_food_allergy"; break;
            }
        }
        try {
            List<SurveyParticipant> participants = surveyParticipantService.getSurveyParticipantsByPatientId(patientId);
            familyHistoryTableModel.setRowCount(0);
            for (SurveyParticipant participant : participants) {
                List<FamilyHistorySurvey> familyHistories = familyHistoryService.searchFamilyHistory(participant.getId(), relationship, diseaseField);
                for (FamilyHistorySurvey familyHistory : familyHistories) {
                    Object[] row = {
                        familyHistory.getId(),
                        familyHistory.getParticipantId(),
                        familyHistory.getRelativeRelationship(),
                        familyHistory.getRelativeDegree(),
                        familyHistory.getDiseaseAsthma() != null && familyHistory.getDiseaseAsthma() ? "哮喘" : "",
                        familyHistory.getDiseaseEczema() != null && familyHistory.getDiseaseEczema() ? "湿疹" : "",
                        familyHistory.getDiseaseRhinitis() != null && familyHistory.getDiseaseRhinitis() ? "鼻炎" : "",
                        familyHistory.getDiseaseFoodAllergy() != null && familyHistory.getDiseaseFoodAllergy() ? "食物过敏" : ""
                    };
                    familyHistoryTableModel.addRow(row);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "搜索家族史数据失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
