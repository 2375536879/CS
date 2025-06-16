package com.zhao.view;

import com.zhao.po.Book;

import javax.swing.*;
import java.awt.*;

public class BookDialog extends JDialog {
    private JTextField isbnField;
    private JTextField titleField;
    private JTextField authorsField;
    private JTextField publisherField;
    private JTextField editionField;
    private JTextField dateField;
    private JTextField typeField;
    
    private JButton okButton;
    private JButton cancelButton;
    
    private Book book;
    
    public BookDialog(Frame owner, String title, Book book) {
        super(owner, title, true);
        this.book = book;
        
        setSize(400, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));
        
        // 创建表单面板
        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.CENTER);
        
        // 创建按钮面板
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        
        // 如果是编辑模式，填充现有数据
        if (book != null) {
            fillFormWithBookData();
        }
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(new JLabel("ISBN:"));
        isbnField = new JTextField();
        panel.add(isbnField);
        
        panel.add(new JLabel("书名:"));
        titleField = new JTextField();
        panel.add(titleField);
        
        panel.add(new JLabel("作者:"));
        authorsField = new JTextField();
        panel.add(authorsField);
        
        panel.add(new JLabel("出版社:"));
        publisherField = new JTextField();
        panel.add(publisherField);
        
        panel.add(new JLabel("版次:"));
        editionField = new JTextField();
        panel.add(editionField);
        
        panel.add(new JLabel("出版日期:"));
        dateField = new JTextField();
        panel.add(dateField);
        
        panel.add(new JLabel("类型:"));
        typeField = new JTextField();
        panel.add(typeField);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        okButton = new JButton("确定");
        cancelButton = new JButton("取消");
        
        okButton.addActionListener(e -> handleOK());
        cancelButton.addActionListener(e -> handleCancel());
        
        panel.add(okButton);
        panel.add(cancelButton);
        
        return panel;
    }
    
    private void fillFormWithBookData() {
        isbnField.setText(book.getISBN());
        titleField.setText(book.getTitle());
        authorsField.setText(book.getAuthors());
        publisherField.setText(book.getPublisher());
        editionField.setText(String.valueOf(book.getEditionNumber()));
        dateField.setText(book.getPublicationDate());
        typeField.setText(book.getType());
        
        // 编辑模式下ISBN不可修改
        isbnField.setEditable(false);
    }
    
    private void handleOK() {
        // 验证必填字段
        if (isbnField.getText().trim().isEmpty() || 
            titleField.getText().trim().isEmpty() || 
            authorsField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ISBN、书名和作者为必填项", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // 验证版次
        try {
            Integer.parseInt(editionField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "版次必须是数字", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // 创建或更新Book对象
        if (book == null) {
            book = new Book();
        }
        
        book.setISBN(isbnField.getText().trim());
        book.setTitle(titleField.getText().trim());
        book.setAuthors(authorsField.getText().trim());
        book.setPublisher(publisherField.getText().trim());
        book.setEditionNumber(Integer.parseInt(editionField.getText().trim()));
        book.setPublicationDate(dateField.getText().trim());
        book.setType(typeField.getText().trim());
        
        dispose();
    }
    
    private void handleCancel() {
        book = null;
        dispose();
    }
    
    public Book getBook() {
        return book;
    }
} 