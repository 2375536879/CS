package com.szz.view.clinicalVisitManageView;

import com.szz.model.FamilyHistorySurvey;
import com.szz.model.HomeEnvironmentExposure;
import com.szz.model.SurveyParticipant;
import com.szz.service.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ClinicalVisitManageView extends JFrame {
    private SurveyParticipantService surveyParticipantService;
    private FamilyHistorySurveyService familyHistoryService;
    private HomeEnvironmentExposureService homeEnvironmentService;
    private SurveyInvestigatorService surveyInvestigatorService;
    private WorkStudyEnvironmentExposureService workStudyEnvironmentService;
    private UrbanRuralEnvironmentMonitoringService urbanRuralEnvironmentService;
    private PotentialConfoundingFactorsService potentialConfoundingFactorsService;
    private EnvironmentalMonitoringMethodsSurveyService environmentalMonitoringMethodsService;

    private JTabbedPane mainTabbedPane;

    // 流调参与者相关组件
    private JTable participantTable;
    private DefaultTableModel participantTableModel;
    private JTextField participantSearchField;
    private JButton addParticipantButton, editParticipantButton, deleteParticipantButton, viewParticipantButton;

    // 家族史相关组件
    private JTable familyHistoryTable;
    private DefaultTableModel familyHistoryTableModel;
    private JTextField familySearchField;
    private JButton addFamilyButton, editFamilyButton, deleteFamilyButton;

    // 家庭环境相关组件
    private JTable homeEnvironmentTable;
    private DefaultTableModel homeEnvironmentTableModel;
    private JTextField environmentSearchField;
    private JButton addEnvironmentButton, editEnvironmentButton, deleteEnvironmentButton;

    public ClinicalVisitManageView() {
        this.surveyParticipantService = new SurveyParticipantService();
        this.familyHistoryService = new FamilyHistorySurveyService();
        this.homeEnvironmentService = new HomeEnvironmentExposureService();
        this.surveyInvestigatorService = new SurveyInvestigatorService();
        this.workStudyEnvironmentService = new WorkStudyEnvironmentExposureService();
        this.urbanRuralEnvironmentService = new UrbanRuralEnvironmentMonitoringService();
        this.potentialConfoundingFactorsService = new PotentialConfoundingFactorsService();
        this.environmentalMonitoringMethodsService = new EnvironmentalMonitoringMethodsSurveyService();

        setTitle("流调数据库管理系统");
        setSize(1400, 900);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        loadAllData();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // 创建标题面板
        JPanel titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);

        // 创建主标签页面板
        mainTabbedPane = new JTabbedPane();
        mainTabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 14));

        // 添加各个标签页
        mainTabbedPane.addTab("流调参与者管理", createParticipantManagementPanel());
        mainTabbedPane.addTab("家族史管理", createFamilyHistoryManagementPanel());
        mainTabbedPane.addTab("家庭环境管理", createHomeEnvironmentManagementPanel());
        mainTabbedPane.addTab("流调员信息", createSurveyInvestigatorPanel());
        mainTabbedPane.addTab("工作学习环境", createWorkStudyEnvironmentPanel());
        mainTabbedPane.addTab("城乡环境监测", createUrbanRuralEnvironmentPanel());
        mainTabbedPane.addTab("潜在混杂因素", createPotentialConfoundingFactorsPanel());
        mainTabbedPane.addTab("环境监测方法", createEnvironmentalMonitoringMethodsPanel());

        add(mainTabbedPane, BorderLayout.CENTER);

        // 创建状态栏
        JPanel statusPanel = createStatusPanel();
        add(statusPanel, BorderLayout.SOUTH);
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(51, 122, 183)),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        JLabel titleLabel = new JLabel("流调数据库管理系统");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setForeground(new Color(51, 51, 51));

        JLabel subtitleLabel = new JLabel("Epidemiological Survey Database Management System");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(102, 102, 102));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(subtitleLabel);

        panel.add(textPanel, BorderLayout.WEST);

        // 添加操作按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton exportButton = new JButton("导出数据");
        exportButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        exportButton.setBackground(new Color(240, 173, 78));
        exportButton.setForeground(Color.WHITE);
        exportButton.setFocusPainted(false);
        exportButton.setPreferredSize(new Dimension(100, 35));
        exportButton.addActionListener(e -> exportData());

        JButton refreshAllButton = new JButton("刷新所有数据");
        refreshAllButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        refreshAllButton.setBackground(new Color(92, 184, 92));
        refreshAllButton.setForeground(Color.WHITE);
        refreshAllButton.setFocusPainted(false);
        refreshAllButton.setPreferredSize(new Dimension(120, 35));
        refreshAllButton.addActionListener(e -> loadAllData());

        buttonPanel.add(exportButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(refreshAllButton);

        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createParticipantManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 创建搜索面板
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createTitledBorder("搜索流调参与者"));

        JLabel searchLabel = new JLabel("姓名/电话:");
        searchLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));

        participantSearchField = new JTextField(20);
        participantSearchField.setFont(new Font("微软雅黑", Font.PLAIN, 12));

        JButton searchButton = new JButton("搜索");
        searchButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        searchButton.setBackground(new Color(51, 122, 183));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> searchParticipants());

        JButton refreshButton = new JButton("刷新");
        refreshButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        refreshButton.setBackground(new Color(92, 184, 92));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> loadParticipantData());

        searchPanel.add(searchLabel);
        searchPanel.add(participantSearchField);
        searchPanel.add(searchButton);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(refreshButton);

        panel.add(searchPanel, BorderLayout.NORTH);

        // 创建表格
        String[] columnNames = {
            "ID", "患者ID", "调查编号", "参与者姓名", "性别", "年龄",
            "居住地类型", "调查时间", "创建时间"
        };

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
        participantTable.setSelectionForeground(Color.BLACK);
        participantTable.setShowGrid(true);

        // 设置表头样式
        participantTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        participantTable.getTableHeader().setBackground(new Color(248, 249, 250));
        participantTable.getTableHeader().setForeground(new Color(51, 51, 51));

        // 设置列宽
        participantTable.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        participantTable.getColumnModel().getColumn(1).setPreferredWidth(70);  // 患者ID
        participantTable.getColumnModel().getColumn(2).setPreferredWidth(100); // 调查编号
        participantTable.getColumnModel().getColumn(3).setPreferredWidth(100); // 姓名
        participantTable.getColumnModel().getColumn(4).setPreferredWidth(60);  // 性别
        participantTable.getColumnModel().getColumn(5).setPreferredWidth(60);  // 年龄
        participantTable.getColumnModel().getColumn(6).setPreferredWidth(80);  // 居住地类型
        participantTable.getColumnModel().getColumn(7).setPreferredWidth(100); // 调查时间
        participantTable.getColumnModel().getColumn(8).setPreferredWidth(120); // 创建时间

        JScrollPane scrollPane = new JScrollPane(participantTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        scrollPane.setBackground(Color.WHITE);

        panel.add(scrollPane, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        addParticipantButton = new JButton("新增参与者");
        editParticipantButton = new JButton("编辑");
        deleteParticipantButton = new JButton("删除");
        viewParticipantButton = new JButton("查看详情");

        // 设置按钮样式
        JButton[] buttons = {addParticipantButton, editParticipantButton, deleteParticipantButton, viewParticipantButton};
        Color[] colors = {
            new Color(92, 184, 92),   // 绿色
            new Color(51, 122, 183),  // 蓝色
            new Color(217, 83, 79),   // 红色
            new Color(91, 192, 222)   // 浅蓝色
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setFont(new Font("微软雅黑", Font.PLAIN, 12));
            buttons[i].setPreferredSize(new Dimension(100, 35));
            buttons[i].setBackground(colors[i]);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            buttons[i].setBorder(BorderFactory.createEmptyBorder());
            buttonPanel.add(buttons[i]);
            if (i < buttons.length - 1) {
                buttonPanel.add(Box.createHorizontalStrut(10));
            }
        }

        // 添加事件监听器
        addParticipantButton.addActionListener(e -> addParticipant());
        editParticipantButton.addActionListener(e -> editParticipant());
        deleteParticipantButton.addActionListener(e -> deleteParticipant());
        viewParticipantButton.addActionListener(e -> viewParticipantDetails());

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createFamilyHistoryManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 创建搜索面板
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createTitledBorder("搜索家族史"));

        JLabel searchLabel = new JLabel("家族成员/疾病:");
        searchLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));

        familySearchField = new JTextField(20);
        familySearchField.setFont(new Font("微软雅黑", Font.PLAIN, 12));

        JButton searchButton = new JButton("搜索");
        searchButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        searchButton.setBackground(new Color(51, 122, 183));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> searchFamilyHistory());

        JButton refreshButton = new JButton("刷新");
        refreshButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        refreshButton.setBackground(new Color(92, 184, 92));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> loadFamilyHistoryData());

        searchPanel.add(searchLabel);
        searchPanel.add(familySearchField);
        searchPanel.add(searchButton);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(refreshButton);

        panel.add(searchPanel, BorderLayout.NORTH);

        // 创建表格
        String[] columnNames = {
            "ID", "参与者ID", "家族成员", "关系", "性别", "疾病状况",
            "诊断年龄", "当前状态", "创建时间"
        };

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
        familyHistoryTable.setSelectionForeground(Color.BLACK);
        familyHistoryTable.setShowGrid(true);

        // 设置表头样式
        familyHistoryTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        familyHistoryTable.getTableHeader().setBackground(new Color(248, 249, 250));
        familyHistoryTable.getTableHeader().setForeground(new Color(51, 51, 51));

        JScrollPane scrollPane = new JScrollPane(familyHistoryTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        scrollPane.setBackground(Color.WHITE);

        panel.add(scrollPane, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        addFamilyButton = new JButton("新增家族史");
        editFamilyButton = new JButton("编辑");
        deleteFamilyButton = new JButton("删除");

        // 设置按钮样式
        JButton[] buttons = {addFamilyButton, editFamilyButton, deleteFamilyButton};
        Color[] colors = {
            new Color(92, 184, 92),   // 绿色
            new Color(51, 122, 183),  // 蓝色
            new Color(217, 83, 79)    // 红色
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setFont(new Font("微软雅黑", Font.PLAIN, 12));
            buttons[i].setPreferredSize(new Dimension(100, 35));
            buttons[i].setBackground(colors[i]);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            buttons[i].setBorder(BorderFactory.createEmptyBorder());
            buttonPanel.add(buttons[i]);
            if (i < buttons.length - 1) {
                buttonPanel.add(Box.createHorizontalStrut(10));
            }
        }

        // 添加事件监听器
        addFamilyButton.addActionListener(e -> addFamilyHistory());
        editFamilyButton.addActionListener(e -> editFamilyHistory());
        deleteFamilyButton.addActionListener(e -> deleteFamilyHistory());

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createHomeEnvironmentManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 创建搜索面板
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createTitledBorder("搜索家庭环境"));

        JLabel searchLabel = new JLabel("住房类型/环境因素:");
        searchLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));

        environmentSearchField = new JTextField(20);
        environmentSearchField.setFont(new Font("微软雅黑", Font.PLAIN, 12));

        JButton searchButton = new JButton("搜索");
        searchButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        searchButton.setBackground(new Color(51, 122, 183));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> searchHomeEnvironment());

        JButton refreshButton = new JButton("刷新");
        refreshButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        refreshButton.setBackground(new Color(92, 184, 92));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> loadHomeEnvironmentData());

        searchPanel.add(searchLabel);
        searchPanel.add(environmentSearchField);
        searchPanel.add(searchButton);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(refreshButton);

        panel.add(searchPanel, BorderLayout.NORTH);

        // 创建表格
        String[] columnNames = {
            "ID", "参与者ID", "住房类型", "建筑年龄", "有宠物", "室内吸烟",
            "化学暴露", "环境风险", "创建时间"
        };

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
        homeEnvironmentTable.setSelectionForeground(Color.BLACK);
        homeEnvironmentTable.setShowGrid(true);

        // 设置表头样式
        homeEnvironmentTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        homeEnvironmentTable.getTableHeader().setBackground(new Color(248, 249, 250));
        homeEnvironmentTable.getTableHeader().setForeground(new Color(51, 51, 51));

        JScrollPane scrollPane = new JScrollPane(homeEnvironmentTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        scrollPane.setBackground(Color.WHITE);

        panel.add(scrollPane, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        addEnvironmentButton = new JButton("新增环境记录");
        editEnvironmentButton = new JButton("编辑");
        deleteEnvironmentButton = new JButton("删除");

        // 设置按钮样式
        JButton[] buttons = {addEnvironmentButton, editEnvironmentButton, deleteEnvironmentButton};
        Color[] colors = {
            new Color(92, 184, 92),   // 绿色
            new Color(51, 122, 183),  // 蓝色
            new Color(217, 83, 79)    // 红色
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setFont(new Font("微软雅黑", Font.PLAIN, 12));
            buttons[i].setPreferredSize(new Dimension(120, 35));
            buttons[i].setBackground(colors[i]);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            buttons[i].setBorder(BorderFactory.createEmptyBorder());
            buttonPanel.add(buttons[i]);
            if (i < buttons.length - 1) {
                buttonPanel.add(Box.createHorizontalStrut(10));
            }
        }

        // 添加事件监听器
        addEnvironmentButton.addActionListener(e -> addHomeEnvironment());
        editEnvironmentButton.addActionListener(e -> editHomeEnvironment());
        deleteEnvironmentButton.addActionListener(e -> deleteHomeEnvironment());

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JLabel statusLabel; // 将statusLabel设为实例变量

    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        statusLabel = new JLabel("就绪");
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(102, 102, 102));

        panel.add(statusLabel, BorderLayout.WEST);

        // 添加数据统计信息
        JLabel statsLabel = new JLabel();
        statsLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        statsLabel.setForeground(new Color(102, 102, 102));
        updateStatsLabel(statsLabel);

        panel.add(statsLabel, BorderLayout.EAST);

        return panel;
    }

    private void updateStatsLabel(JLabel statsLabel) {
        try {
            int participantCount = surveyParticipantService.getAllSurveyParticipants().size();
            int familyHistoryCount = familyHistoryService.getAllFamilyHistorySurveys().size();
            int homeEnvCount = homeEnvironmentService.getAllHomeEnvironmentExposures().size();

            String stats = String.format("参与者: %d | 家族史: %d | 家庭环境: %d",
                    participantCount, familyHistoryCount, homeEnvCount);
            statsLabel.setText(stats);
        } catch (Exception e) {
            statsLabel.setText("统计信息获取失败");
        }
    }

    private void updateStatusMessage(String message) {
        if (statusLabel != null) {
            statusLabel.setText(message);
            // 2秒后恢复为"就绪"状态
            Timer timer = new Timer(2000, e -> statusLabel.setText("就绪"));
            timer.setRepeats(false);
            timer.start();
        }
    }

    // 数据加载方法
    private void loadAllData() {
        updateStatusMessage("正在加载数据...");
        loadParticipantData();
        loadFamilyHistoryData();
        loadHomeEnvironmentData();
        updateStatusMessage("数据加载完成");
    }

    private void loadParticipantData() {
        try {
            List<SurveyParticipant> participants = surveyParticipantService.getAllSurveyParticipants();
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
                    participant.getPatientId(),
                    participant.getSurveyIdentifier(),
                    participant.getName(),
                    participant.getGender(),
                    participant.getAgeAtSurvey(),
                    participant.getResidenceType(),
                    participant.getSurveyDate() != null ? dateFormat.format(participant.getSurveyDate()) : "",
                    participant.getCreatedAt() != null ? dateFormat.format(participant.getCreatedAt()) : ""
            };
            participantTableModel.addRow(row);
        }
    }

    private void loadFamilyHistoryData() {
        try {
            List<FamilyHistorySurvey> familyHistories = familyHistoryService.getAllFamilyHistorySurveys();
            updateFamilyHistoryTable(familyHistories);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "加载家族史数据失败: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateFamilyHistoryTable(List<FamilyHistorySurvey> familyHistories) {
        familyHistoryTableModel.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (FamilyHistorySurvey familyHistory : familyHistories) {
            Object[] row = {
                    familyHistory.getId(),
                    familyHistory.getParticipantId(),
                    familyHistory.getFamilyMemberName(),
                    familyHistory.getRelationshipToParticipant(),
                    familyHistory.getGender(),
                    familyHistory.getMedicalCondition(),
                    familyHistory.getAgeAtDiagnosis(),
                    familyHistory.getCurrentStatus(),
                    familyHistory.getCreatedAt() != null ? dateFormat.format(familyHistory.getCreatedAt()) : ""
            };
            familyHistoryTableModel.addRow(row);
        }
    }

    private void loadHomeEnvironmentData() {
        try {
            List<HomeEnvironmentExposure> homeEnvironments = homeEnvironmentService.getAllHomeEnvironmentExposures();
            updateHomeEnvironmentTable(homeEnvironments);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "加载家庭环境数据失败: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateHomeEnvironmentTable(List<HomeEnvironmentExposure> homeEnvironments) {
        homeEnvironmentTableModel.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
                    riskAssessment,
                    homeEnv.getCreatedAt() != null ? dateFormat.format(homeEnv.getCreatedAt()) : ""
            };
            homeEnvironmentTableModel.addRow(row);
        }
    }

    // 搜索方法
    private void searchParticipants() {
        String keyword = participantSearchField.getText().trim();
        if (keyword.isEmpty()) {
            loadParticipantData();
            return;
        }

        try {
            List<SurveyParticipant> results = surveyParticipantService.searchSurveyParticipants(keyword);
            updateParticipantTable(results);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "搜索失败: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchFamilyHistory() {
        String keyword = familySearchField.getText().trim();
        if (keyword.isEmpty()) {
            loadFamilyHistoryData();
            return;
        }

        try {
            List<FamilyHistorySurvey> results = familyHistoryService.searchByMedicalCondition(keyword);
            updateFamilyHistoryTable(results);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "搜索失败: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchHomeEnvironment() {
        String keyword = environmentSearchField.getText().trim();
        if (keyword.isEmpty()) {
            loadHomeEnvironmentData();
            return;
        }

        try {
            List<HomeEnvironmentExposure> results = homeEnvironmentService.searchByHousingType(keyword);
            updateHomeEnvironmentTable(results);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "搜索失败: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // 流调参与者操作方法
    private void addParticipant() {
        SimpleSurveyParticipantDialog dialog = new SimpleSurveyParticipantDialog(
                this,
                "新增流调参与者",
                null,
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
                SimpleSurveyParticipantDialog dialog = new SimpleSurveyParticipantDialog(
                        this,
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
                    loadAllData(); // 重新加载所有数据
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
                        this,
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
        // 检查是否选择了参与者
        int selectedRow = participantTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择一个流调参与者", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int participantId = (Integer) participantTableModel.getValueAt(selectedRow, 0);

        // 创建简单的家族史添加对话框
        JDialog dialog = new JDialog(this, "新增家族史", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // 亲属级别
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("亲属级别:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> degreeCombo = new JComboBox<>(new String[]{"一级", "二级"});
        panel.add(degreeCombo, gbc);

        // 关系
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("关系:"), gbc);
        gbc.gridx = 1;
        JTextField relationField = new JTextField(20);
        panel.add(relationField, gbc);

        // 疾病信息
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("疾病信息:"), gbc);
        gbc.gridx = 1;
        JPanel diseasePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JCheckBox asthmaBox = new JCheckBox("哮喘");
        JCheckBox eczemaBox = new JCheckBox("湿疹");
        JCheckBox rhinitisBox = new JCheckBox("鼻炎");
        JCheckBox foodAllergyBox = new JCheckBox("食物过敏");
        diseasePanel.add(asthmaBox);
        diseasePanel.add(eczemaBox);
        diseasePanel.add(rhinitisBox);
        diseasePanel.add(foodAllergyBox);
        panel.add(diseasePanel, gbc);

        // 按钮
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("保存");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);

        saveButton.addActionListener(e -> {
            try {
                FamilyHistorySurvey familyHistory = new FamilyHistorySurvey();
                familyHistory.setSurveyParticipantId(participantId);
                familyHistory.setRelativeDegree((String) degreeCombo.getSelectedItem());
                familyHistory.setRelativeRelationship(relationField.getText());
                familyHistory.setDiseaseAsthma(asthmaBox.isSelected());
                familyHistory.setDiseaseEczema(eczemaBox.isSelected());
                familyHistory.setDiseaseRhinitis(rhinitisBox.isSelected());
                familyHistory.setDiseaseFoodAllergy(foodAllergyBox.isSelected());

                int newId = familyHistoryService.createFamilyHistorySurvey(familyHistory);
                if (newId > 0) {
                    JOptionPane.showMessageDialog(dialog, "保存成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    loadFamilyHistoryData();
                } else {
                    JOptionPane.showMessageDialog(dialog, "保存失败", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "保存失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void editFamilyHistory() {
        int selectedRow = familyHistoryTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要编辑的家族史记录", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int familyHistoryId = (Integer) familyHistoryTableModel.getValueAt(selectedRow, 0);

        try {
            FamilyHistorySurvey familyHistory = familyHistoryService.getFamilyHistorySurveyById(familyHistoryId);
            if (familyHistory == null) {
                JOptionPane.showMessageDialog(this, "未找到家族史记录", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 创建编辑对话框
            JDialog dialog = new JDialog(this, "编辑家族史", true);
            dialog.setSize(500, 400);
            dialog.setLocationRelativeTo(this);

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.WEST;

            // 亲属级别
            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(new JLabel("亲属级别:"), gbc);
            gbc.gridx = 1;
            JComboBox<String> degreeCombo = new JComboBox<>(new String[]{"一级", "二级"});
            degreeCombo.setSelectedItem(familyHistory.getRelativeDegree());
            panel.add(degreeCombo, gbc);

            // 关系
            gbc.gridx = 0; gbc.gridy = 1;
            panel.add(new JLabel("关系:"), gbc);
            gbc.gridx = 1;
            JTextField relationField = new JTextField(20);
            relationField.setText(familyHistory.getRelativeRelationship());
            panel.add(relationField, gbc);

            // 疾病信息
            gbc.gridx = 0; gbc.gridy = 2;
            panel.add(new JLabel("疾病信息:"), gbc);
            gbc.gridx = 1;
            JPanel diseasePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JCheckBox asthmaBox = new JCheckBox("哮喘");
            JCheckBox eczemaBox = new JCheckBox("湿疹");
            JCheckBox rhinitisBox = new JCheckBox("鼻炎");
            JCheckBox foodAllergyBox = new JCheckBox("食物过敏");

            // 设置当前值
            asthmaBox.setSelected(Boolean.TRUE.equals(familyHistory.getDiseaseAsthma()));
            eczemaBox.setSelected(Boolean.TRUE.equals(familyHistory.getDiseaseEczema()));
            rhinitisBox.setSelected(Boolean.TRUE.equals(familyHistory.getDiseaseRhinitis()));
            foodAllergyBox.setSelected(Boolean.TRUE.equals(familyHistory.getDiseaseFoodAllergy()));

            diseasePanel.add(asthmaBox);
            diseasePanel.add(eczemaBox);
            diseasePanel.add(rhinitisBox);
            diseasePanel.add(foodAllergyBox);
            panel.add(diseasePanel, gbc);

            // 按钮
            gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton saveButton = new JButton("保存");
            JButton cancelButton = new JButton("取消");
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);
            panel.add(buttonPanel, gbc);

            saveButton.addActionListener(e -> {
                try {
                    familyHistory.setRelativeDegree((String) degreeCombo.getSelectedItem());
                    familyHistory.setRelativeRelationship(relationField.getText());
                    familyHistory.setDiseaseAsthma(asthmaBox.isSelected());
                    familyHistory.setDiseaseEczema(eczemaBox.isSelected());
                    familyHistory.setDiseaseRhinitis(rhinitisBox.isSelected());
                    familyHistory.setDiseaseFoodAllergy(foodAllergyBox.isSelected());

                    boolean success = familyHistoryService.updateFamilyHistorySurvey(familyHistory);
                    if (success) {
                        JOptionPane.showMessageDialog(dialog, "更新成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                        loadFamilyHistoryData();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "更新失败", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "更新失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
            });

            cancelButton.addActionListener(e -> dialog.dispose());

            dialog.add(panel);
            dialog.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "获取家族史记录失败: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteFamilyHistory() {
        int selectedRow = familyHistoryTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的家族史记录", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(this,
                "确定要删除选中的家族史记录吗？",
                "确认删除",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            int familyHistoryId = (Integer) familyHistoryTableModel.getValueAt(selectedRow, 0);
            try {
                boolean success = familyHistoryService.deleteFamilyHistorySurvey(familyHistoryId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    loadFamilyHistoryData();
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

    // 家庭环境操作方法
    private void addHomeEnvironment() {
        // 检查是否选择了参与者
        int selectedRow = participantTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择一个流调参与者", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int participantId = (Integer) participantTableModel.getValueAt(selectedRow, 0);

        // 创建简单的家庭环境添加对话框
        JDialog dialog = new JDialog(this, "新增家庭环境记录", true);
        dialog.setSize(600, 500);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // 住房类型
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("住房类型:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> housingCombo = new JComboBox<>(new String[]{"平房", "楼房", "别墅", "其他"});
        panel.add(housingCombo, gbc);

        // 供暖类型
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("供暖类型:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> heatingCombo = new JComboBox<>(new String[]{"集中供暖", "电暖器", "燃气壁挂炉", "其他"});
        panel.add(heatingCombo, gbc);

        // 烹饪燃料
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("烹饪燃料:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> cookingCombo = new JComboBox<>(new String[]{"天燃气", "煤气", "生物质燃料"});
        panel.add(cookingCombo, gbc);

        // 宠物信息
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("宠物:"), gbc);
        gbc.gridx = 1;
        JPanel petPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JCheckBox catBox = new JCheckBox("猫");
        JCheckBox dogBox = new JCheckBox("狗");
        JCheckBox birdBox = new JCheckBox("鸟");
        petPanel.add(catBox);
        petPanel.add(dogBox);
        petPanel.add(birdBox);
        panel.add(petPanel, gbc);

        // 按钮
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("保存");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);

        saveButton.addActionListener(e -> {
            try {
                HomeEnvironmentExposure homeEnv = new HomeEnvironmentExposure();
                homeEnv.setParticipantId(participantId);
                homeEnv.setHousingType((String) housingCombo.getSelectedItem());
                homeEnv.setHeatingType((String) heatingCombo.getSelectedItem());
                homeEnv.setCookingFuelType((String) cookingCombo.getSelectedItem());
                homeEnv.setHasPets(catBox.isSelected() || dogBox.isSelected() || birdBox.isSelected());

                // 设置宠物类型
                StringBuilder petTypes = new StringBuilder();
                if (catBox.isSelected()) petTypes.append("猫 ");
                if (dogBox.isSelected()) petTypes.append("狗 ");
                if (birdBox.isSelected()) petTypes.append("鸟 ");
                homeEnv.setPetTypes(petTypes.toString().trim());

                int newId = homeEnvironmentService.createHomeEnvironmentExposure(homeEnv);
                if (newId > 0) {
                    JOptionPane.showMessageDialog(dialog, "保存成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    loadHomeEnvironmentData();
                } else {
                    JOptionPane.showMessageDialog(dialog, "保存失败", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "保存失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void editHomeEnvironment() {
        int selectedRow = homeEnvironmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要编辑的家庭环境记录", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int homeEnvId = (Integer) homeEnvironmentTableModel.getValueAt(selectedRow, 0);

        try {
            HomeEnvironmentExposure homeEnv = homeEnvironmentService.getHomeEnvironmentExposureById(homeEnvId);
            if (homeEnv == null) {
                JOptionPane.showMessageDialog(this, "未找到家庭环境记录", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 创建编辑对话框
            JDialog dialog = new JDialog(this, "编辑家庭环境记录", true);
            dialog.setSize(600, 500);
            dialog.setLocationRelativeTo(this);

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.WEST;

            // 住房类型
            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(new JLabel("住房类型:"), gbc);
            gbc.gridx = 1;
            JComboBox<String> housingCombo = new JComboBox<>(new String[]{"平房", "楼房", "别墅", "其他"});
            housingCombo.setSelectedItem(homeEnv.getHousingType());
            panel.add(housingCombo, gbc);

            // 供暖类型
            gbc.gridx = 0; gbc.gridy = 1;
            panel.add(new JLabel("供暖类型:"), gbc);
            gbc.gridx = 1;
            JComboBox<String> heatingCombo = new JComboBox<>(new String[]{"集中供暖", "电暖器", "燃气壁挂炉", "其他"});
            heatingCombo.setSelectedItem(homeEnv.getHeatingType());
            panel.add(heatingCombo, gbc);

            // 烹饪燃料
            gbc.gridx = 0; gbc.gridy = 2;
            panel.add(new JLabel("烹饪燃料:"), gbc);
            gbc.gridx = 1;
            JComboBox<String> cookingCombo = new JComboBox<>(new String[]{"天燃气", "煤气", "生物质燃料"});
            cookingCombo.setSelectedItem(homeEnv.getCookingFuelType());
            panel.add(cookingCombo, gbc);

            // 宠物信息
            gbc.gridx = 0; gbc.gridy = 3;
            panel.add(new JLabel("宠物:"), gbc);
            gbc.gridx = 1;
            JPanel petPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JCheckBox catBox = new JCheckBox("猫");
            JCheckBox dogBox = new JCheckBox("狗");
            JCheckBox birdBox = new JCheckBox("鸟");

            // 设置当前值
            String petTypes = homeEnv.getPetTypes();
            if (petTypes != null) {
                catBox.setSelected(petTypes.contains("猫"));
                dogBox.setSelected(petTypes.contains("狗"));
                birdBox.setSelected(petTypes.contains("鸟"));
            }

            petPanel.add(catBox);
            petPanel.add(dogBox);
            petPanel.add(birdBox);
            panel.add(petPanel, gbc);

            // 按钮
            gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton saveButton = new JButton("保存");
            JButton cancelButton = new JButton("取消");
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);
            panel.add(buttonPanel, gbc);

            saveButton.addActionListener(e -> {
                try {
                    homeEnv.setHousingType((String) housingCombo.getSelectedItem());
                    homeEnv.setHeatingType((String) heatingCombo.getSelectedItem());
                    homeEnv.setCookingFuelType((String) cookingCombo.getSelectedItem());
                    homeEnv.setHasPets(catBox.isSelected() || dogBox.isSelected() || birdBox.isSelected());

                    // 设置宠物类型
                    StringBuilder petTypesBuilder = new StringBuilder();
                    if (catBox.isSelected()) petTypesBuilder.append("猫 ");
                    if (dogBox.isSelected()) petTypesBuilder.append("狗 ");
                    if (birdBox.isSelected()) petTypesBuilder.append("鸟 ");
                    homeEnv.setPetTypes(petTypesBuilder.toString().trim());

                    boolean success = homeEnvironmentService.updateHomeEnvironmentExposure(homeEnv);
                    if (success) {
                        JOptionPane.showMessageDialog(dialog, "更新成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                        loadHomeEnvironmentData();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "更新失败", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "更新失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
            });

            cancelButton.addActionListener(e -> dialog.dispose());

            dialog.add(panel);
            dialog.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "获取家庭环境记录失败: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteHomeEnvironment() {
        int selectedRow = homeEnvironmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的家庭环境记录", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(this,
                "确定要删除选中的家庭环境记录吗？此操作不可撤销。",
                "确认删除",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            int homeEnvId = (Integer) homeEnvironmentTableModel.getValueAt(selectedRow, 0);
            try {
                boolean success = homeEnvironmentService.deleteHomeEnvironmentExposure(homeEnvId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    loadHomeEnvironmentData();
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

    // 数据导出功能
    private void exportData() {
        String[] options = {"流调参与者数据", "家族史数据", "家庭环境数据", "全部数据"};
        int choice = JOptionPane.showOptionDialog(this,
                "请选择要导出的数据类型:",
                "数据导出",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice >= 0) {
            switch (choice) {
                case 0:
                    exportParticipantData();
                    break;
                case 1:
                    exportFamilyHistoryData();
                    break;
                case 2:
                    exportHomeEnvironmentData();
                    break;
                case 3:
                    exportAllData();
                    break;
            }
        }
    }

    private void exportParticipantData() {
        try {
            List<SurveyParticipant> participants = surveyParticipantService.getAllSurveyParticipants();
            if (participants.isEmpty()) {
                JOptionPane.showMessageDialog(this, "没有数据可导出", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder csv = new StringBuilder();
            csv.append("ID,患者ID,调查编号,参与者姓名,性别,年龄,居住地类型,调查时间,创建时间\n");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (SurveyParticipant participant : participants) {
                csv.append(participant.getId()).append(",")
                   .append(participant.getPatientId()).append(",")
                   .append(participant.getSurveyIdentifier()).append(",")
                   .append(participant.getName()).append(",")
                   .append(participant.getGender()).append(",")
                   .append(participant.getAgeAtSurvey()).append(",")
                   .append(participant.getResidenceType()).append(",")
                   .append(participant.getSurveyDate() != null ? dateFormat.format(participant.getSurveyDate()) : "").append(",")
                   .append(participant.getCreatedAt() != null ? dateFormat.format(participant.getCreatedAt()) : "").append("\n");
            }

            saveToFile("流调参与者数据.csv", csv.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "导出失败: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportFamilyHistoryData() {
        JOptionPane.showMessageDialog(this, "家族史数据导出功能开发中...", "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportHomeEnvironmentData() {
        JOptionPane.showMessageDialog(this, "家庭环境数据导出功能开发中...", "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportAllData() {
        JOptionPane.showMessageDialog(this, "全部数据导出功能开发中...", "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveToFile(String fileName, String content) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new java.io.File(fileName));

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                java.io.File file = fileChooser.getSelectedFile();
                java.nio.file.Files.write(file.toPath(), content.getBytes("UTF-8"));
                JOptionPane.showMessageDialog(this,
                        "数据已成功导出到: " + file.getAbsolutePath(),
                        "导出成功",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "保存文件失败: " + e.getMessage(),
                        "错误",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 创建其他查询面板的方法
    private JPanel createSurveyInvestigatorPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 创建标题面板
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createTitledBorder("流调员信息 (查询模式)"));

        JLabel infoLabel = new JLabel("此模块为查询模式，暂不支持增删改操作");
        infoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        infoLabel.setForeground(new Color(102, 102, 102));
        titlePanel.add(infoLabel);

        panel.add(titlePanel, BorderLayout.NORTH);

        // 创建表格
        String[] columnNames = {"ID", "参与者ID", "调查员姓名", "职称", "创建时间"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(240, 248, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setShowGrid(true);

        // 设置表头样式
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(248, 249, 250));
        table.getTableHeader().setForeground(new Color(51, 51, 51));

        // 加载真实数据
        loadSurveyInvestigatorData(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        scrollPane.setBackground(Color.WHITE);

        panel.add(scrollPane, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JButton refreshButton = new JButton("刷新数据");
        refreshButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        refreshButton.setPreferredSize(new Dimension(100, 35));
        refreshButton.setBackground(new Color(92, 184, 92));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(BorderFactory.createEmptyBorder());
        refreshButton.addActionListener(e -> loadSurveyInvestigatorData(tableModel));

        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadSurveyInvestigatorData(DefaultTableModel tableModel) {
        try {
            var investigators = surveyInvestigatorService.getAllSurveyInvestigators();
            tableModel.setRowCount(0);

            if (investigators.isEmpty()) {
                tableModel.addRow(new Object[]{"", "暂无流调员信息数据", "", "", ""});
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                for (var investigator : investigators) {
                    Object[] row = {
                        investigator.getId(),
                        investigator.getSurveyParticipantId(),
                        investigator.getInvestigatorName(),
                        investigator.getInvestigatorTitle(),
                        investigator.getCreatedAt() != null ? dateFormat.format(investigator.getCreatedAt()) : ""
                    };
                    tableModel.addRow(row);
                }
            }
        } catch (Exception e) {
            tableModel.setRowCount(0);
            tableModel.addRow(new Object[]{"", "数据加载失败: " + e.getMessage(), "", "", ""});
        }
    }

    private JPanel createWorkStudyEnvironmentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 创建标题面板
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createTitledBorder("工作学习环境 (查询模式)"));

        JLabel infoLabel = new JLabel("此模块为查询模式，暂不支持增删改操作");
        infoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        infoLabel.setForeground(new Color(102, 102, 102));
        titlePanel.add(infoLabel);

        panel.add(titlePanel, BorderLayout.NORTH);

        // 创建表格
        String[] columnNames = {"ID", "参与者ID", "位置类型", "通风情况", "PM2.5暴露", "花粉暴露", "创建时间"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(240, 248, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setShowGrid(true);

        // 设置表头样式
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(248, 249, 250));
        table.getTableHeader().setForeground(new Color(51, 51, 51));

        // 加载真实数据
        loadWorkStudyEnvironmentData(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        scrollPane.setBackground(Color.WHITE);

        panel.add(scrollPane, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JButton refreshButton = new JButton("刷新数据");
        refreshButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        refreshButton.setPreferredSize(new Dimension(100, 35));
        refreshButton.setBackground(new Color(92, 184, 92));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(BorderFactory.createEmptyBorder());
        refreshButton.addActionListener(e -> loadWorkStudyEnvironmentData(tableModel));

        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadWorkStudyEnvironmentData(DefaultTableModel tableModel) {
        try {
            var workStudyEnvironments = workStudyEnvironmentService.getAllWorkStudyEnvironmentExposures();
            tableModel.setRowCount(0);

            if (workStudyEnvironments.isEmpty()) {
                tableModel.addRow(new Object[]{"", "暂无工作学习环境数据", "", "", "", "", ""});
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                for (var workStudyEnv : workStudyEnvironments) {
                    Object[] row = {
                        workStudyEnv.getId(),
                        workStudyEnv.getSurveyParticipantId(),
                        workStudyEnv.getLocationType(),
                        workStudyEnv.getRoomVentilationStatus(),
                        workStudyEnv.getPm25ExposureAnnualAvg(),
                        workStudyEnv.getPollenExposurePeakConcentration(),
                        workStudyEnv.getCreatedAt() != null ? dateFormat.format(workStudyEnv.getCreatedAt()) : ""
                    };
                    tableModel.addRow(row);
                }
            }
        } catch (Exception e) {
            tableModel.setRowCount(0);
            tableModel.addRow(new Object[]{"", "数据加载失败: " + e.getMessage(), "", "", "", "", ""});
        }
    }

    private JPanel createUrbanRuralEnvironmentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 创建标题面板
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createTitledBorder("城乡环境监测 (查询模式)"));

        JLabel infoLabel = new JLabel("此模块为查询模式，暂不支持增删改操作");
        infoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        infoLabel.setForeground(new Color(102, 102, 102));
        titlePanel.add(infoLabel);

        panel.add(titlePanel, BorderLayout.NORTH);

        // 创建表格
        String[] columnNames = {"ID", "参与者ID", "监测地点类型", "PM2.5浓度", "花粉类型", "其他污染物", "创建时间"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(240, 248, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setShowGrid(true);

        // 设置表头样式
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(248, 249, 250));
        table.getTableHeader().setForeground(new Color(51, 51, 51));

        // 加载真实数据
        loadUrbanRuralEnvironmentData(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        scrollPane.setBackground(Color.WHITE);

        panel.add(scrollPane, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JButton refreshButton = new JButton("刷新数据");
        refreshButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        refreshButton.setPreferredSize(new Dimension(100, 35));
        refreshButton.setBackground(new Color(92, 184, 92));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(BorderFactory.createEmptyBorder());
        refreshButton.addActionListener(e -> loadUrbanRuralEnvironmentData(tableModel));

        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadUrbanRuralEnvironmentData(DefaultTableModel tableModel) {
        try {
            var urbanRuralEnvironments = urbanRuralEnvironmentService.getAllUrbanRuralEnvironmentMonitorings();
            tableModel.setRowCount(0);

            if (urbanRuralEnvironments.isEmpty()) {
                tableModel.addRow(new Object[]{"", "暂无城乡环境监测数据", "", "", "", "", ""});
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                for (var urbanRuralEnv : urbanRuralEnvironments) {
                    Object[] row = {
                        urbanRuralEnv.getId(),
                        urbanRuralEnv.getSurveyParticipantId(),
                        urbanRuralEnv.getMonitoringLocationType(),
                        urbanRuralEnv.getCityPm25AnnualAvgSeasonalChange(),
                        urbanRuralEnv.getCityPollenMainTypesMonthlyDistPeak(),
                        urbanRuralEnv.getCityOtherPollutants(),
                        urbanRuralEnv.getCreatedAt() != null ? dateFormat.format(urbanRuralEnv.getCreatedAt()) : ""
                    };
                    tableModel.addRow(row);
                }
            }
        } catch (Exception e) {
            tableModel.setRowCount(0);
            tableModel.addRow(new Object[]{"", "数据加载失败: " + e.getMessage(), "", "", "", "", ""});
        }
    }

    private JPanel createPotentialConfoundingFactorsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 创建标题面板
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createTitledBorder("潜在混杂因素 (查询模式)"));

        JLabel infoLabel = new JLabel("此模块为查询模式，暂不支持增删改操作");
        infoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        infoLabel.setForeground(new Color(102, 102, 102));
        titlePanel.add(infoLabel);

        panel.add(titlePanel, BorderLayout.NORTH);

        // 创建表格
        String[] columnNames = {"ID", "参与者ID", "饮食习惯", "维生素D", "压力水平", "疫苗接种", "创建时间"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(240, 248, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setShowGrid(true);

        // 设置表头样式
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(248, 249, 250));
        table.getTableHeader().setForeground(new Color(51, 51, 51));

        // 加载真实数据
        loadPotentialConfoundingFactorsData(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        scrollPane.setBackground(Color.WHITE);

        panel.add(scrollPane, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JButton refreshButton = new JButton("刷新数据");
        refreshButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        refreshButton.setPreferredSize(new Dimension(100, 35));
        refreshButton.setBackground(new Color(92, 184, 92));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(BorderFactory.createEmptyBorder());
        refreshButton.addActionListener(e -> loadPotentialConfoundingFactorsData(tableModel));

        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadPotentialConfoundingFactorsData(DefaultTableModel tableModel) {
        try {
            var potentialFactors = potentialConfoundingFactorsService.getAllPotentialConfoundingFactors();
            tableModel.setRowCount(0);

            if (potentialFactors.isEmpty()) {
                tableModel.addRow(new Object[]{"", "暂无潜在混杂因素数据", "", "", "", "", ""});
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                for (var factor : potentialFactors) {
                    Object[] row = {
                        factor.getId(),
                        factor.getSurveyParticipantId(),
                        factor.getDietaryHabits(),
                        factor.getVitaminDDailyIu(),
                        factor.getLongTermStressLevelPss10(),
                        factor.getVaccinationHistoryOnSchedule() != null ? (factor.getVaccinationHistoryOnSchedule() ? "是" : "否") : "",
                        factor.getCreatedAt() != null ? dateFormat.format(factor.getCreatedAt()) : ""
                    };
                    tableModel.addRow(row);
                }
            }
        } catch (Exception e) {
            tableModel.setRowCount(0);
            tableModel.addRow(new Object[]{"", "数据加载失败: " + e.getMessage(), "", "", "", "", ""});
        }
    }

    private JPanel createEnvironmentalMonitoringMethodsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 创建标题面板
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createTitledBorder("环境监测方法 (查询模式)"));

        JLabel infoLabel = new JLabel("此模块为查询模式，暂不支持增删改操作");
        infoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        infoLabel.setForeground(new Color(102, 102, 102));
        titlePanel.add(infoLabel);

        panel.add(titlePanel, BorderLayout.NORTH);

        // 创建表格
        String[] columnNames = {"ID", "参与者ID", "PM2.5检测仪", "花粉采样方法", "尘螨检测方法", "创建时间"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(240, 248, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setShowGrid(true);

        // 设置表头样式
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(248, 249, 250));
        table.getTableHeader().setForeground(new Color(51, 51, 51));

        // 加载真实数据
        loadEnvironmentalMonitoringMethodsData(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        scrollPane.setBackground(Color.WHITE);

        panel.add(scrollPane, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JButton refreshButton = new JButton("刷新数据");
        refreshButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        refreshButton.setPreferredSize(new Dimension(100, 35));
        refreshButton.setBackground(new Color(92, 184, 92));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(BorderFactory.createEmptyBorder());
        refreshButton.addActionListener(e -> loadEnvironmentalMonitoringMethodsData(tableModel));

        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadEnvironmentalMonitoringMethodsData(DefaultTableModel tableModel) {
        try {
            var environmentalMethods = environmentalMonitoringMethodsService.getAllEnvironmentalMonitoringMethodsSurveys();
            tableModel.setRowCount(0);

            if (environmentalMethods.isEmpty()) {
                tableModel.addRow(new Object[]{"", "暂无环境监测方法数据", "", "", "", ""});
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                for (var method : environmentalMethods) {
                    Object[] row = {
                        method.getId(),
                        method.getSurveyParticipantId(),
                        method.getPm25DetectorModel(),
                        method.getPollenSamplingMethod(),
                        method.getDustMiteDetectionMethod(),
                        method.getCreatedAt() != null ? dateFormat.format(method.getCreatedAt()) : ""
                    };
                    tableModel.addRow(row);
                }
            }
        } catch (Exception e) {
            tableModel.setRowCount(0);
            tableModel.addRow(new Object[]{"", "数据加载失败: " + e.getMessage(), "", "", "", ""});
        }
    }



    // 主方法用于测试
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ClinicalVisitManageView().setVisible(true);
        });
    }
}