package com.zhao.view;

import com.zhao.Service.ReaderService;
import com.zhao.po.Reader;

import javax.swing.*;
import java.awt.*;

public class ReaderView extends JFrame {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField addressField;
    private JTextField phoneField;
    private JTextField limitsField;

    private JButton saveButton;
    private JButton cancelButton;
    private ReaderService readerService;
    public void createFrame(){

        readerService=new ReaderService();

        // 设置窗口标题、大小、位置
        this.setTitle("添加读者");
        this.setSize(400, 350);
        this.setLocationRelativeTo(null); // 居中显示
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout(10, 10));

        // 创建表单面板（使用 GridLayout）
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 添加各个字段
        formPanel.add(new JLabel("姓："));
        firstNameField = new JTextField();
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("名："));
        lastNameField = new JTextField();
        formPanel.add(lastNameField);

        formPanel.add(new JLabel("地址："));
        addressField = new JTextField();
        formPanel.add(addressField);

        formPanel.add(new JLabel("电话："));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        formPanel.add(new JLabel("借阅上限："));
        limitsField = new JTextField();
        formPanel.add(limitsField);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        saveButton = new JButton("保存");
        cancelButton = new JButton("取消");
        saveButton.addActionListener(e->handleSave());
        cancelButton.addActionListener(e->handleCancle());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // 将组件加入主窗口
        this.add(formPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        // 显示窗口
        this.setVisible(true);

    }

    //未对数据做异常处理
    Reader getReader(){
        Reader reader=new Reader();
        reader.setFirstName(firstNameField.getText());
        reader.setLastName(lastNameField.getText());
        reader.setAddress(addressField.getText());
        reader.setPhone(phoneField.getText());
        reader.setLimits(Integer.valueOf(limitsField.getText()));

        return reader;
    }

    public void handleSave(){

        Reader reader=getReader();
        if(readerService.addReader(reader)==true){
            JOptionPane.showMessageDialog(ReaderView.this,"读者添加成功！");
        }else {
            JOptionPane.showMessageDialog(ReaderView.this,"图书添加失败！");
        }
    }

    public void handleCancle(){

        this.dispose();
    }

}
