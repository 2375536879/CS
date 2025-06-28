package com.szz.view.patientView;

import javax.swing.*;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * 症状和体征详情对话框 - 重新设计的美观界面
 */
public class SymptomsSignsDetailDialog extends JDialog {
    private final ResultSet symptomRs;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public SymptomsSignsDetailDialog(Window parent, ResultSet symptomRs) {
        super(parent, "症状和体征详情", ModalityType.APPLICATION_MODAL);
        this.symptomRs = symptomRs;
        initializeUI();
    }

    private void initializeUI() {
        setSize(1200, 800);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // 主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        
        // 标题面板
        JPanel titlePanel = createTitlePanel();
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        // 内容面板
        JTabbedPane tabbedPane = createContentTabs();
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // 按钮面板
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        JLabel titleLabel = new JLabel("症状和体征详细信息");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        titleLabel.setForeground(new Color(51, 51, 51));

        JLabel subtitleLabel = new JLabel("详细的症状描述、体征记录和相关信息");
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
    
    private JTabbedPane createContentTabs() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        
        try {
            if (symptomRs != null) {
                // 基本信息标签页
                tabbedPane.addTab("基本信息", createBasicInfoPanel());
                
                // 哮喘症状标签页
                tabbedPane.addTab("哮喘症状", createAsthmaPanel());
                
                // 过敏性鼻炎症状标签页
                tabbedPane.addTab("过敏性鼻炎症状", createRhinitisPanel());
                
                // 湿疹/AD症状标签页
                tabbedPane.addTab("湿疹/AD症状", createEczemaPanel());
                
                // 体征信息标签页
                tabbedPane.addTab("体征信息", createSignsPanel());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "加载数据时出错: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
        
        return tabbedPane;
    }
    
    private JScrollPane createBasicInfoPanel() throws SQLException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 症状描述卡片
        JPanel descCard = createInfoCard("症状描述");
        addInfoItem(descCard, "症状描述", symptomRs.getString("symptom_description"));
        addInfoItem(descCard, "症状严重程度", symptomRs.getString("symptom_severity"));
        addInfoItem(descCard, "症状频率", symptomRs.getString("symptom_frequency"));
        if (symptomRs.getDate("symptom_start_date") != null) {
            addInfoItem(descCard, "症状开始时间", dateFormat.format(symptomRs.getDate("symptom_start_date")));
        }
        panel.add(descCard);
        panel.add(Box.createVerticalStrut(15));

        // 诱因和环境因素卡片
        JPanel triggerCard = createInfoCard("诱因和环境因素");
        addInfoItem(triggerCard, "症状诱因", symptomRs.getString("symptom_triggers_general"));
        addInfoItem(triggerCard, "环境因素", symptomRs.getString("environmental_factors_notes"));
        panel.add(triggerCard);
        
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }
    
    private JScrollPane createAsthmaPanel() throws SQLException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 哮喘症状卡片
        JPanel asthmaCard = createInfoCard("哮喘相关症状");
        addBooleanItem(asthmaCard, "喘息", symptomRs.getBoolean("asthma_wheezing"));
        addBooleanItem(asthmaCard, "咳嗽", symptomRs.getBoolean("asthma_coughing"));
        addBooleanItem(asthmaCard, "呼吸困难", symptomRs.getBoolean("asthma_dyspnea"));
        addBooleanItem(asthmaCard, "活动受限", symptomRs.getBoolean("asthma_activity_limitation"));
        addBooleanItem(asthmaCard, "夜间觉醒", symptomRs.getBoolean("asthma_night_awakening"));
        addBooleanItem(asthmaCard, "胸闷", symptomRs.getBoolean("asthma_chest_tightness"));
        addBooleanItem(asthmaCard, "夜间或晨间症状加重", symptomRs.getBoolean("asthma_night_morning_symptoms"));
        panel.add(asthmaCard);
        panel.add(Box.createVerticalStrut(15));

        // 哮喘触发因素卡片
        JPanel triggerCard = createInfoCard("哮喘触发因素");
        addInfoItem(triggerCard, "触发因素描述", symptomRs.getString("asthma_triggers"));
        panel.add(triggerCard);
        panel.add(Box.createVerticalStrut(15));

        // 哮喘体征卡片
        JPanel signsCard = createInfoCard("哮喘体征");
        addBooleanItem(signsCard, "听诊闻哮鸣音", symptomRs.getBoolean("asthma_auscultation_wheezing"));
        panel.add(signsCard);
        
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }
    
    private JScrollPane createRhinitisPanel() throws SQLException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 过敏性鼻炎症状卡片
        JPanel rhinitisCard = createInfoCard("过敏性鼻炎症状");
        addBooleanItem(rhinitisCard, "清水样鼻涕", symptomRs.getBoolean("ar_clear_nasal_discharge"));
        addBooleanItem(rhinitisCard, "鼻痒", symptomRs.getBoolean("ar_nasal_itching"));
        addBooleanItem(rhinitisCard, "鼻塞", symptomRs.getBoolean("ar_nasal_congestion"));
        addBooleanItem(rhinitisCard, "阵发性喷嚏", symptomRs.getBoolean("ar_paroxysmal_sneezing"));
        addBooleanItem(rhinitisCard, "嗅觉减退", symptomRs.getBoolean("ar_olfactory_decline"));
        addBooleanItem(rhinitisCard, "清咽喉", symptomRs.getBoolean("ar_throat_clearing"));
        addBooleanItem(rhinitisCard, "吸鼻子", symptomRs.getBoolean("ar_sniffing"));
        addBooleanItem(rhinitisCard, "刺激性干咳", symptomRs.getBoolean("ar_irritative_dry_cough"));
        addBooleanItem(rhinitisCard, "咽异物感", symptomRs.getBoolean("ar_globus_sensation"));
        panel.add(rhinitisCard);
        panel.add(Box.createVerticalStrut(15));

        // 眼部症状卡片
        JPanel eyeCard = createInfoCard("眼部症状");
        addBooleanItem(eyeCard, "眼痒", symptomRs.getBoolean("ar_eye_itching"));
        addBooleanItem(eyeCard, "眨眼", symptomRs.getBoolean("ar_blinking"));
        panel.add(eyeCard);
        panel.add(Box.createVerticalStrut(15));

        // 生活影响卡片
        JPanel impactCard = createInfoCard("生活影响");
        addBooleanItem(impactCard, "睡眠障碍", symptomRs.getBoolean("ar_sleep_disturbance"));
        addBooleanItem(impactCard, "日常活动受限", symptomRs.getBoolean("ar_daily_activity_limitation"));
        addBooleanItem(impactCard, "在校表现变差或工作能力下降", symptomRs.getBoolean("ar_school_work_performance_decline"));
        panel.add(impactCard);
        panel.add(Box.createVerticalStrut(15));

        // 过敏性鼻炎体征卡片
        JPanel signsCard = createInfoCard("过敏性鼻炎体征");
        addBooleanItem(signsCard, "鼻腔黏膜肿胀", symptomRs.getBoolean("ar_nasal_mucosa_swelling"));
        addBooleanItem(signsCard, "鼻腔黏膜色苍白或充血", symptomRs.getBoolean("ar_nasal_mucosa_pale_or_congested"));
        addBooleanItem(signsCard, "有鼻道分泌物", symptomRs.getBoolean("ar_nasal_discharge_observed"));
        addBooleanItem(signsCard, "鼻甲肥大", symptomRs.getBoolean("ar_turbinate_hypertrophy"));
        addBooleanItem(signsCard, "黑眼圈", symptomRs.getBoolean("ar_allergic_shiners"));
        addBooleanItem(signsCard, "过敏性敬礼", symptomRs.getBoolean("ar_allergic_salute"));
        addBooleanItem(signsCard, "过敏性鼻皱褶", symptomRs.getBoolean("ar_allergic_crease"));
        panel.add(signsCard);
        
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }
    
    private JScrollPane createEczemaPanel() throws SQLException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 湿疹/AD基本症状卡片
        JPanel basicCard = createInfoCard("湿疹/AD基本症状");
        addBooleanItem(basicCard, "慢性复发性瘙痒性皮疹", symptomRs.getBoolean("ad_chronic_recurrent_pruritic_rash"));
        addBooleanItem(basicCard, "有屈侧或面颊部皮炎史", symptomRs.getBoolean("ad_flexural_or_facial_dermatitis_history"));
        addBooleanItem(basicCard, "皮肤干燥", symptomRs.getBoolean("ad_dry_skin"));
        panel.add(basicCard);
        panel.add(Box.createVerticalStrut(15));

        // 皮肤特征卡片
        JPanel skinCard = createInfoCard("皮肤特征");
        addBooleanItem(skinCard, "干皮症", symptomRs.getBoolean("ad_xerosis"));
        addBooleanItem(skinCard, "耳根裂纹", symptomRs.getBoolean("ad_retroauricular_fissures"));
        addBooleanItem(skinCard, "鱼鳞病", symptomRs.getBoolean("ad_ichthyosis"));
        addBooleanItem(skinCard, "掌纹症", symptomRs.getBoolean("ad_palmar_hyperlinearity"));
        addBooleanItem(skinCard, "毛周角化症", symptomRs.getBoolean("ad_keratosis_pilaris"));
        addBooleanItem(skinCard, "有皮肤感染倾向", symptomRs.getBoolean("ad_skin_infection_tendency"));
        panel.add(skinCard);
        panel.add(Box.createVerticalStrut(15));

        // 面部特征卡片
        JPanel faceCard = createInfoCard("面部特征");
        addBooleanItem(faceCard, "旦尼-莫根眶下褶痕", symptomRs.getBoolean("ad_dennie_morgan_infraorbital_fold"));
        addBooleanItem(faceCard, "眶周黑晕", symptomRs.getBoolean("ad_periorbital_darkening"));
        addBooleanItem(faceCard, "苍白脸", symptomRs.getBoolean("ad_facial_pallor"));
        addBooleanItem(faceCard, "白色糠疹", symptomRs.getBoolean("ad_pityriasis_alba"));
        addBooleanItem(faceCard, "颈前皱褶", symptomRs.getBoolean("ad_anterior_neck_folds"));
        panel.add(faceCard);
        
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }
    
    private JScrollPane createSignsPanel() throws SQLException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 体征描述卡片
        JPanel descCard = createInfoCard("体征描述");
        addInfoItem(descCard, "体征描述", symptomRs.getString("sign_description"));
        panel.add(descCard);
        
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
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
    
    private void addBooleanItem(JPanel parent, String label, boolean value) {
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
        labelComponent.setPreferredSize(new Dimension(180, 20));
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
