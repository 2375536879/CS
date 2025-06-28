package ADMS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * 随访数据管理界面
 */
public class FollowUpManageUI extends JFrame {

    private JTabbedPane tabbedPane;

    private JTable visitsTable;
    private JTextField searchVisitIdField, searchPatientIdField, searchProviderNameField;

    private JTextField detailVisitIdField;
    private JTable symptomsTable, signsTable, diagnosesTable, medicationsTable, questionnairesTable;

    public FollowUpManageUI() {
        setTitle("管理随访数据");
        setSize(1200, 800);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("随访记录管理", createVisitSearchPanel());
        tabbedPane.addTab("随访详情", createDetailPanel());

        add(tabbedPane);
    }

    private JPanel createVisitSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        
        JPanel searchCriteriaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchCriteriaPanel.add(new JLabel("随访ID:"));
        searchVisitIdField = new JTextField(10);
        searchCriteriaPanel.add(searchVisitIdField);
        searchCriteriaPanel.add(new JLabel("患者ID:"));
        searchPatientIdField = new JTextField(10);
        searchCriteriaPanel.add(searchPatientIdField);
        searchCriteriaPanel.add(new JLabel("服务者姓名:"));
        searchProviderNameField = new JTextField(10);
        searchCriteriaPanel.add(searchProviderNameField);

        JButton searchButton = new JButton("查询随访记录");
        searchCriteriaPanel.add(searchButton);
        
        panel.add(searchCriteriaPanel, BorderLayout.NORTH);

        visitsTable = new JTable();
        visitsTable.getTableHeader().setReorderingAllowed(false);
        // --- 添加横向滚动条的关键设置 ---
        visitsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        panel.add(new JScrollPane(visitsTable), BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            DefaultTableModel model = DatabaseManager.searchFollowUpVisits(
                    searchVisitIdField.getText(),
                    searchPatientIdField.getText(),
                    searchProviderNameField.getText()
            );
            visitsTable.setModel(model);
        });
        
        return panel;
    }

    private JPanel createDetailPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("输入要查询详情的随访ID (followup_visit_id):"));
        detailVisitIdField = new JTextField(10);
        topPanel.add(detailVisitIdField);
        JButton searchDetailsButton = new JButton("查询详情");
        topPanel.add(searchDetailsButton);
        panel.add(topPanel, BorderLayout.NORTH);

        JTabbedPane detailTabs = new JTabbedPane();
        
        symptomsTable = new JTable();
        signsTable = new JTable();
        diagnosesTable = new JTable();
        medicationsTable = new JTable();
        questionnairesTable = new JTable();

        // --- 为所有详情表格添加横向滚动条的关键设置 ---
        symptomsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        signsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        diagnosesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        medicationsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        questionnairesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        detailTabs.addTab("症状", new JScrollPane(symptomsTable));
        detailTabs.addTab("体征", new JScrollPane(signsTable));
        detailTabs.addTab("诊断", new JScrollPane(diagnosesTable));
        detailTabs.addTab("用药", new JScrollPane(medicationsTable));
        detailTabs.addTab("问卷", new JScrollPane(questionnairesTable));

        panel.add(detailTabs, BorderLayout.CENTER);

        searchDetailsButton.addActionListener(e -> {
            String visitId = detailVisitIdField.getText();
            if (visitId == null || visitId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入随访ID！", "输入错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int id = Integer.parseInt(visitId);
                
                symptomsTable.setModel(DatabaseManager.getFollowUpSymptoms(id));
                signsTable.setModel(DatabaseManager.getFollowUpSigns(id));
                diagnosesTable.setModel(DatabaseManager.getFollowUpDiagnoses(id));
                medicationsTable.setModel(DatabaseManager.getFollowUpMedications(id));
                questionnairesTable.setModel(DatabaseManager.getFollowUpQuestionnaires(id));

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "随访ID必须是数字！", "输入错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }
}