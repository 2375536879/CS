package com.szz.view.clinicalVisitManageView;

import com.szz.model.SurveyParticipant;
import com.szz.model.FamilyHistorySurvey;
import com.szz.model.HomeEnvironmentExposure;
import com.szz.service.FamilyHistorySurveyService;
import com.szz.service.HomeEnvironmentExposureService;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class SurveyParticipantDetailDialog extends JDialog {
    private SurveyParticipant participant;
    private FamilyHistorySurveyService familyHistoryService;
    private HomeEnvironmentExposureService homeEnvironmentService;

    public SurveyParticipantDetailDialog(JFrame parent, SurveyParticipant participant) {
        super(parent, "流调参与者详情", true);
        this.participant = participant;
        this.familyHistoryService = new FamilyHistorySurveyService();
        this.homeEnvironmentService = new HomeEnvironmentExposureService();
        
        initUI();
        setSize(900, 700);
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // 创建标题面板
        JPanel titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);

        // 创建主内容面板
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 12));

        // 基本信息标签页
        tabbedPane.addTab("基本信息", createBasicInfoPanel());
        
        // 生活习惯标签页
        tabbedPane.addTab("生活习惯", createLifestylePanel());
        
        // 健康信息标签页
        tabbedPane.addTab("健康信息", createHealthInfoPanel());
        
        // 家族史标签页
        tabbedPane.addTab("家族史", createFamilyHistoryPanel());
        
        // 家庭环境标签页
        tabbedPane.addTab("家庭环境", createHomeEnvironmentPanel());

        add(tabbedPane, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        JLabel titleLabel = new JLabel("流调参与者详细信息");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        titleLabel.setForeground(new Color(51, 51, 51));

        JLabel nameLabel = new JLabel("参与者: " + (participant.getParticipantName() != null ? participant.getParticipantName() : "未知"));
        nameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        nameLabel.setForeground(new Color(102, 102, 102));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(nameLabel);

        panel.add(textPanel, BorderLayout.WEST);
        return panel;
    }

    private JPanel createBasicInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // 基本信息卡片
        JPanel basicCard = createInfoCard("基本信息");
        addInfoItem(basicCard, "参与者ID", String.valueOf(participant.getId()));
        addInfoItem(basicCard, "患者ID", String.valueOf(participant.getPatientId()));
        addInfoItem(basicCard, "参与者姓名", participant.getParticipantName());
        addInfoItem(basicCard, "性别", participant.getGender());
        
        if (participant.getDateOfBirth() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            addInfoItem(basicCard, "出生日期", dateFormat.format(participant.getDateOfBirth()));
        }
        
        addInfoItem(basicCard, "联系电话", participant.getContactPhone());
        addInfoItem(basicCard, "家庭住址", participant.getHomeAddress());
        addInfoItem(basicCard, "教育水平", participant.getEducationLevel());
        addInfoItem(basicCard, "职业", participant.getOccupation());
        addInfoItem(basicCard, "婚姻状况", participant.getMaritalStatus());
        addInfoItem(basicCard, "家庭收入", participant.getHouseholdIncome());

        panel.add(basicCard);
        return panel;
    }

    private JPanel createLifestylePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // 生活习惯卡片
        JPanel lifestyleCard = createInfoCard("生活习惯");
        addInfoItem(lifestyleCard, "吸烟状况", participant.getSmokingStatus());
        addInfoItem(lifestyleCard, "饮酒状况", participant.getDrinkingStatus());
        addInfoItem(lifestyleCard, "运动频率", participant.getExerciseFrequency());
        addInfoItem(lifestyleCard, "饮食习惯", participant.getDietHabits());
        addInfoItem(lifestyleCard, "睡眠质量", participant.getSleepQuality());
        addInfoItem(lifestyleCard, "压力水平", participant.getStressLevel());

        panel.add(lifestyleCard);
        return panel;
    }

    private JPanel createHealthInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // 健康信息卡片
        JPanel healthCard = createInfoCard("健康信息");
        addInfoItem(healthCard, "慢性疾病", participant.getChronicDiseases());
        addInfoItem(healthCard, "用药史", participant.getMedicationHistory());
        addInfoItem(healthCard, "过敏史", participant.getAllergyHistory());
        addInfoItem(healthCard, "家族病史", participant.getFamilyMedicalHistory());
        addInfoItem(healthCard, "环境暴露", participant.getEnvironmentalExposure());
        addInfoItem(healthCard, "职业暴露", participant.getOccupationalExposure());
        addInfoItem(healthCard, "备注", participant.getNotes());

        panel.add(healthCard);
        return panel;
    }

    private JPanel createFamilyHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        try {
            List<FamilyHistorySurvey> familyHistories = familyHistoryService.getFamilyHistorySurveysByParticipantId(participant.getId());
            
            if (familyHistories.isEmpty()) {
                JLabel noDataLabel = new JLabel("暂无家族史数据", JLabel.CENTER);
                noDataLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                noDataLabel.setForeground(new Color(102, 102, 102));
                panel.add(noDataLabel, BorderLayout.CENTER);
            } else {
                JPanel contentPanel = new JPanel();
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
                contentPanel.setBackground(Color.WHITE);

                for (FamilyHistorySurvey familyHistory : familyHistories) {
                    JPanel familyCard = createInfoCard("家族成员: " + 
                        (familyHistory.getFamilyMemberName() != null ? familyHistory.getFamilyMemberName() : "未知"));
                    
                    addInfoItem(familyCard, "与参与者关系", familyHistory.getRelationshipToParticipant());
                    addInfoItem(familyCard, "性别", familyHistory.getGender());
                    if (familyHistory.getAgeAtDiagnosis() != null) {
                        addInfoItem(familyCard, "诊断时年龄", String.valueOf(familyHistory.getAgeAtDiagnosis()));
                    }
                    addInfoItem(familyCard, "疾病状况", familyHistory.getMedicalCondition());
                    addInfoItem(familyCard, "诊断日期", familyHistory.getDiagnosisDate());
                    addInfoItem(familyCard, "当前状态", familyHistory.getCurrentStatus());
                    addInfoItem(familyCard, "治疗史", familyHistory.getTreatmentHistory());
                    addInfoItem(familyCard, "用药史", familyHistory.getMedicationHistory());
                    addInfoItem(familyCard, "过敏史", familyHistory.getAllergyHistory());
                    addInfoItem(familyCard, "吸烟史", familyHistory.getSmokingHistory());
                    addInfoItem(familyCard, "饮酒史", familyHistory.getDrinkingHistory());
                    addInfoItem(familyCard, "职业暴露", familyHistory.getOccupationalExposure());
                    addInfoItem(familyCard, "环境因素", familyHistory.getEnvironmentalFactors());
                    addInfoItem(familyCard, "基因检测结果", familyHistory.getGeneticTestingResults());
                    addInfoItem(familyCard, "备注", familyHistory.getNotes());

                    contentPanel.add(familyCard);
                    contentPanel.add(Box.createVerticalStrut(15));
                }

                JScrollPane scrollPane = new JScrollPane(contentPanel);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane.setBorder(null);
                panel.add(scrollPane, BorderLayout.CENTER);
            }
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("加载家族史数据失败: " + e.getMessage(), JLabel.CENTER);
            errorLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            errorLabel.setForeground(Color.RED);
            panel.add(errorLabel, BorderLayout.CENTER);
        }

        return panel;
    }

    private JPanel createHomeEnvironmentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        try {
            List<HomeEnvironmentExposure> homeEnvironments = homeEnvironmentService.getHomeEnvironmentExposuresByParticipantId(participant.getId());
            
            if (homeEnvironments.isEmpty()) {
                JLabel noDataLabel = new JLabel("暂无家庭环境数据", JLabel.CENTER);
                noDataLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                noDataLabel.setForeground(new Color(102, 102, 102));
                panel.add(noDataLabel, BorderLayout.CENTER);
            } else {
                JPanel contentPanel = new JPanel();
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
                contentPanel.setBackground(Color.WHITE);

                for (HomeEnvironmentExposure homeEnv : homeEnvironments) {
                    JPanel envCard = createInfoCard("家庭环境记录");
                    
                    addInfoItem(envCard, "住房类型", homeEnv.getHousingType());
                    if (homeEnv.getBuildingAge() != null) {
                        addInfoItem(envCard, "建筑年龄", String.valueOf(homeEnv.getBuildingAge()) + "年");
                    }
                    addInfoItem(envCard, "供暖类型", homeEnv.getHeatingType());
                    addInfoItem(envCard, "烹饪燃料类型", homeEnv.getCookingFuelType());
                    addBooleanItem(envCard, "有空调", homeEnv.getHasAirConditioning());
                    addBooleanItem(envCard, "有通风系统", homeEnv.getHasVentilationSystem());
                    addInfoItem(envCard, "湿度水平", homeEnv.getHumidityLevel());
                    addInfoItem(envCard, "温度控制", homeEnv.getTemperatureControl());
                    addBooleanItem(envCard, "有宠物", homeEnv.getHasPets());
                    addInfoItem(envCard, "宠物类型", homeEnv.getPetTypes());
                    addBooleanItem(envCard, "有室内植物", homeEnv.getHasIndoorPlants());
                    addInfoItem(envCard, "植物类型", homeEnv.getPlantTypes());
                    addBooleanItem(envCard, "室内吸烟", homeEnv.getHasSmokingIndoors());
                    addInfoItem(envCard, "吸烟频率", homeEnv.getSmokingFrequency());
                    addBooleanItem(envCard, "化学物质暴露", homeEnv.getHasChemicalExposure());
                    addInfoItem(envCard, "化学物质类型", homeEnv.getChemicalTypes());
                    addBooleanItem(envCard, "霉菌或潮湿", homeEnv.getHasMoldOrDampness());
                    addInfoItem(envCard, "霉菌位置", homeEnv.getMoldLocation());
                    addBooleanItem(envCard, "尘螨", homeEnv.getHasDustMites());
                    addInfoItem(envCard, "尘螨位置", homeEnv.getDustMiteLocation());
                    addBooleanItem(envCard, "有地毯", homeEnv.getHasCarpets());
                    addInfoItem(envCard, "地毯类型", homeEnv.getCarpetType());
                    addInfoItem(envCard, "清洁频率", homeEnv.getCleaningFrequency());
                    addInfoItem(envCard, "清洁产品", homeEnv.getCleaningProducts());
                    addBooleanItem(envCard, "有空气净化器", homeEnv.getHasAirPurifier());
                    addInfoItem(envCard, "空气净化器类型", homeEnv.getAirPurifierType());
                    addInfoItem(envCard, "水源", homeEnv.getWaterSource());
                    addInfoItem(envCard, "水质", homeEnv.getWaterQuality());
                    addInfoItem(envCard, "噪音水平", homeEnv.getNoiseLevel());
                    addInfoItem(envCard, "照明条件", homeEnv.getLightingConditions());
                    addInfoItem(envCard, "附近污染源", homeEnv.getNearbyPollutionSources());
                    addInfoItem(envCard, "社区环境", homeEnv.getNeighborhoodEnvironment());
                    addInfoItem(envCard, "备注", homeEnv.getNotes());

                    // 添加环境风险评估
                    String riskAssessment = homeEnvironmentService.getEnvironmentalRiskAssessment(homeEnv);
                    addInfoItem(envCard, "环境风险评估", riskAssessment);

                    contentPanel.add(envCard);
                    contentPanel.add(Box.createVerticalStrut(15));
                }

                JScrollPane scrollPane = new JScrollPane(contentPanel);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane.setBorder(null);
                panel.add(scrollPane, BorderLayout.CENTER);
            }
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("加载家庭环境数据失败: " + e.getMessage(), JLabel.CENTER);
            errorLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            errorLabel.setForeground(Color.RED);
            panel.add(errorLabel, BorderLayout.CENTER);
        }

        return panel;
    }

    private JPanel createInfoCard(String title) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setBackground(Color.WHITE);
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 添加标题
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        titleLabel.setForeground(new Color(51, 51, 51));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(15));

        return card;
    }

    private void addInfoItem(JPanel parent, String label, String value) {
        if (value == null || value.trim().isEmpty()) {
            return;
        }

        JPanel row = new JPanel(new GridBagLayout());
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        GridBagConstraints gbc = new GridBagConstraints();

        // 标签
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 0, 15);

        JLabel labelComponent = new JLabel(label + ":");
        labelComponent.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        labelComponent.setForeground(new Color(102, 102, 102));
        labelComponent.setPreferredSize(new Dimension(140, 20));
        row.add(labelComponent, gbc);

        // 值
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);

        JTextArea valueComponent = new JTextArea(value);
        valueComponent.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        valueComponent.setForeground(new Color(51, 51, 51));
        valueComponent.setOpaque(false);
        valueComponent.setEditable(false);
        valueComponent.setLineWrap(true);
        valueComponent.setWrapStyleWord(true);
        valueComponent.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        row.add(valueComponent, gbc);

        parent.add(row);
    }

    private void addBooleanItem(JPanel parent, String label, Boolean value) {
        if (value == null) {
            return;
        }

        JPanel row = new JPanel(new GridBagLayout());
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));

        GridBagConstraints gbc = new GridBagConstraints();

        // 标签
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 15);

        JLabel labelComponent = new JLabel(label + ":");
        labelComponent.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        labelComponent.setForeground(new Color(102, 102, 102));
        labelComponent.setPreferredSize(new Dimension(140, 20));
        row.add(labelComponent, gbc);

        // 值
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 0);

        JPanel valuePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        valuePanel.setOpaque(false);

        // 创建方框图标
        JLabel iconLabel = new JLabel(value ? "☑" : "☐");
        iconLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        iconLabel.setForeground(value ? new Color(76, 175, 80) : new Color(158, 158, 158));

        JLabel textLabel = new JLabel(" " + (value ? "是" : "否"));
        textLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        textLabel.setForeground(value ? new Color(76, 175, 80) : new Color(158, 158, 158));

        valuePanel.add(iconLabel);
        valuePanel.add(textLabel);

        row.add(valuePanel, gbc);
        parent.add(row);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JButton closeButton = new JButton("关闭");
        closeButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        closeButton.setPreferredSize(new Dimension(80, 35));
        closeButton.setBackground(new Color(108, 117, 125));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder());
        closeButton.addActionListener(e -> dispose());

        panel.add(closeButton);
        return panel;
    }
}
