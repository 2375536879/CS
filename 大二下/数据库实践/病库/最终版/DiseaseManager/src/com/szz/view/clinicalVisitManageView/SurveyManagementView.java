package com.szz.view.clinicalVisitManageView;

import com.szz.model.Survey.SurveyParticipant;
import com.szz.service.Survey.SurveyParticipantService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class SurveyManagementView extends JPanel {
    private SurveyParticipantService surveyParticipantService;
    private JTable participantTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, viewButton;

    public SurveyManagementView() {
        this.surveyParticipantService = new SurveyParticipantService();
        initUI();
        loadParticipantData();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 创建标题面板
        JPanel titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);

        // 创建搜索面板
        JPanel searchPanel = createSearchPanel();
        add(searchPanel, BorderLayout.CENTER);

        // 创建表格面板
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel("流调数据管理");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        titleLabel.setForeground(new Color(51, 51, 51));

        JLabel subtitleLabel = new JLabel("管理流调参与者、家族史和家庭环境数据");
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

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel searchLabel = new JLabel("搜索:");
        searchLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));

        searchField = new JTextField(20);
        searchField.setFont(new Font("微软雅黑", Font.PLAIN, 12));

        JButton searchButton = new JButton("搜索");
        searchButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        searchButton.setBackground(new Color(51, 122, 183));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> performSearch());

        JButton refreshButton = new JButton("刷新");
        refreshButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        refreshButton.setBackground(new Color(92, 184, 92));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> loadParticipantData());

        panel.add(searchLabel);
        panel.add(searchField);
        panel.add(searchButton);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(refreshButton);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // 创建表格模型
        String[] columnNames = {
                "ID", "参与者姓名", "性别", "出生日期", "联系电话", 
                "教育水平", "职业", "婚姻状况", "创建时间"
        };

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // 创建表格
        participantTable = new JTable(tableModel);
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
        participantTable.getColumnModel().getColumn(1).setPreferredWidth(100); // 姓名
        participantTable.getColumnModel().getColumn(2).setPreferredWidth(60);  // 性别
        participantTable.getColumnModel().getColumn(3).setPreferredWidth(100); // 出生日期
        participantTable.getColumnModel().getColumn(4).setPreferredWidth(120); // 联系电话
        participantTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // 教育水平
        participantTable.getColumnModel().getColumn(6).setPreferredWidth(100); // 职业
        participantTable.getColumnModel().getColumn(7).setPreferredWidth(80);  // 婚姻状况
        participantTable.getColumnModel().getColumn(8).setPreferredWidth(120); // 创建时间

        JScrollPane scrollPane = new JScrollPane(participantTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        scrollPane.setBackground(Color.WHITE);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        addButton = new JButton("新增参与者");
        editButton = new JButton("编辑");
        deleteButton = new JButton("删除");
        viewButton = new JButton("查看详情");

        // 设置按钮样式
        JButton[] buttons = {addButton, editButton, deleteButton, viewButton};
        Color[] colors = {
                new Color(92, 184, 92),   // 绿色 - 新增
                new Color(51, 122, 183),  // 蓝色 - 编辑
                new Color(217, 83, 79),   // 红色 - 删除
                new Color(91, 192, 222)   // 浅蓝色 - 查看
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setFont(new Font("微软雅黑", Font.PLAIN, 12));
            buttons[i].setPreferredSize(new Dimension(100, 35));
            buttons[i].setBackground(colors[i]);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            buttons[i].setBorder(BorderFactory.createEmptyBorder());
            panel.add(buttons[i]);
            if (i < buttons.length - 1) {
                panel.add(Box.createHorizontalStrut(10));
            }
        }


        addButton.addActionListener(e -> showAddParticipantDialog());
        editButton.addActionListener(e -> showEditParticipantDialog());
        deleteButton.addActionListener(e -> deleteSelectedParticipant());
        viewButton.addActionListener(e -> showParticipantDetails());

        return panel;
    }

    private void loadParticipantData() {
        try {
            List<SurveyParticipant> participants = surveyParticipantService.getAllSurveyParticipants();
            updateTableData(participants);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "加载流调参与者数据失败: " + e.getMessage(), 
                    "错误", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTableData(List<SurveyParticipant> participants) {
        tableModel.setRowCount(0);
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
                    participant.getMaritalStatus(),
                    participant.getCreatedAt() != null ? dateFormat.format(participant.getCreatedAt()) : ""
            };
            tableModel.addRow(row);
        }
    }

    private void performSearch() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadParticipantData();
            return;
        }

        try {
            List<SurveyParticipant> results = surveyParticipantService.searchSurveyParticipants(keyword);
            updateTableData(results);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "搜索失败: " + e.getMessage(), 
                    "错误", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAddParticipantDialog() {
        SurveyParticipantDialog dialog = new SurveyParticipantDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this), 
                "新增流调参与者", 
                null, 
                surveyParticipantService
        );
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            loadParticipantData();
        }
    }

    private void showEditParticipantDialog() {
        int selectedRow = participantTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要编辑的参与者", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int participantId = (Integer) tableModel.getValueAt(selectedRow, 0);
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

    private void deleteSelectedParticipant() {
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
            int participantId = (Integer) tableModel.getValueAt(selectedRow, 0);
            try {
                boolean success = surveyParticipantService.deleteSurveyParticipant(participantId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    loadParticipantData();
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

    private void showParticipantDetails() {
        int selectedRow = participantTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要查看的参与者", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int participantId = (Integer) tableModel.getValueAt(selectedRow, 0);
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
}
