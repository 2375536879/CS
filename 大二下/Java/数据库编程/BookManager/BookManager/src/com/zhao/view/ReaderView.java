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
    private JTextField readerIdField;

    private JButton saveButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private ReaderService readerService;
    public void createFrame(){

        readerService=new ReaderService();

        // 设置窗口标题、大小、位置
        this.setTitle("读者管理");
        this.setSize(400, 400);
        this.setLocationRelativeTo(null); // 居中显示
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout(10, 10));

        // 创建表单面板（使用 GridLayout）
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 添加读者ID字段
        formPanel.add(new JLabel("读者ID："));
        readerIdField = new JTextField();
        formPanel.add(readerIdField);

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
        deleteButton = new JButton("删除");
        saveButton.addActionListener(e->handleSave());
        cancelButton.addActionListener(e->handleCancle());
        deleteButton.addActionListener(e->handleDelete());

        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
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

    public void handleDelete() {
        String readerIdStr = readerIdField.getText().trim();
        if (readerIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入读者ID", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int readerId = Integer.parseInt(readerIdStr);
            int confirm = JOptionPane.showConfirmDialog(this, 
                "确定要删除该读者吗？", 
                "确认删除", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (readerService.deleteReader(readerId)) {
                    JOptionPane.showMessageDialog(this, "读者删除成功！");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "读者删除失败！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的读者ID", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        readerIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        addressField.setText("");
        phoneField.setText("");
        limitsField.setText("");
    }

}
