package com.szz.view.Login;

import ADMS.BioSampleManageUI;
import ADMS.FollowUpManageUI;
import com.szz.view.clinicalVisitManageView.ClinicalVisitManageView;
import com.szz.view.patientView.PatientManageView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 系统主框架窗口
 */
public class ADMSFrame extends JFrame {
    private JFrame frame;
    private JMenuBar menuBar;
    private JPanel panel;
    private JLabel label;

    /**
     * 初始化主窗口基本属性
     */
    private void setMyFrame() {
        frame = new JFrame();
        frame.setLocation(580, 250);
        frame.setSize(700, 500);
        frame.setTitle("过敏性疾病专病库管理系统");
        frame.setResizable(false);
        label = new JLabel("专病库管理系统");
        label.setFont(new Font("黑体", Font.BOLD, 50));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(400, 100));
        frame.add(label, BorderLayout.NORTH);
    }

    /**
     * 创建主界面按钮面板
     */
    private void setMyButtons() {
        panel = new JPanel();
        JButton patientManageButton = new JButton("临床数据管理");
        JButton clinicalVisitManageButton = new JButton("流调数据管理");
        JButton bioSampleManageButton = new JButton("生物样本管理");
        JButton followUpManageButton = new JButton("随访数据管理");

        // 患者管理按钮事件
        patientManageButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            PatientManageView pv=new PatientManageView();
            pv.setVisible(true);
            pv.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        }));

        // 临床数据管理按钮事件
        clinicalVisitManageButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            ClinicalVisitManageView cv=new ClinicalVisitManageView();
            cv.setVisible(true);
            cv.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        }));

        // 生物样本管理按钮事件
        bioSampleManageButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            BioSampleManageUI bsui = new BioSampleManageUI();
            bsui.setVisible(true);
            bsui.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }));

        // 随访数据管理按钮事件
        followUpManageButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            FollowUpManageUI fuui = new FollowUpManageUI();
            fuui.setVisible(true);
            fuui.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }));

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Dimension buttonSize = new Dimension(180, 50);
        patientManageButton.setPreferredSize(buttonSize);
        clinicalVisitManageButton.setPreferredSize(buttonSize);
        bioSampleManageButton.setPreferredSize(buttonSize);
        followUpManageButton.setPreferredSize(buttonSize);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(patientManageButton, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(clinicalVisitManageButton, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(bioSampleManageButton, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(followUpManageButton, gbc);

        frame.add(panel, BorderLayout.CENTER);
    }

    /**
     * 初始化菜单栏
     */
    private void setMyMenuBar() {
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("设置(S)");
        menu.setMnemonic(KeyEvent.VK_S);
        JMenuItem menuItem1 = new JMenuItem("关于(A)", KeyEvent.VK_A);
        menu.setFont(new Font("黑体", Font.CENTER_BASELINE, 15));
        menuItem1.setFont(new Font("黑体", Font.CENTER_BASELINE, 15));
        menuItem1.addActionListener(e -> {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            JTextArea textArea = new JTextArea("版本：过敏性疾病专病库管理系统 Ver1.0\nCopyright <c> 2025-2025. All Rights Reserved.\n当前日期、时间:" + formattedDateTime);
            textArea.setFont(new Font("黑体", Font.PLAIN, 15));
            textArea.setEditable(false);
            textArea.setOpaque(false);
            JOptionPane.showMessageDialog(null, textArea, "关于", JOptionPane.PLAIN_MESSAGE);
        });
        menu.add(menuItem1);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
    }

    /**
     * 主界面初始化方法
     */
    public void init() {
        setMyFrame();
        setMyButtons();
        setMyMenuBar();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
