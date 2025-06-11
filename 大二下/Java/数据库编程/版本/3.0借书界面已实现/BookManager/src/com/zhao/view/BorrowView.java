package com.zhao.view;

import com.zhao.Service.RecordService;
import com.zhao.po.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BorrowView extends JFrame {
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextField isbnField;
    private JButton borrowButton;
    private JButton returnButton;
    private RecordService recordService;
    private int currentReaderId;

    public BorrowView(int readerId) {
        this.currentReaderId = readerId;
        this.recordService = new RecordService();
        initComponents();
    }

    private void initComponents() {
        setTitle("借阅管理");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 创建表格
        String[] columnNames = {"ISBN", "书名", "作者", "出版社", "版次", "出版日期", "类型"};
        tableModel = new DefaultTableModel(columnNames, 0);
        bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 创建操作面板
        JPanel operationPanel = new JPanel();
        isbnField = new JTextField(15);
        borrowButton = new JButton("借阅");
        returnButton = new JButton("归还");

        operationPanel.add(new JLabel("ISBN:"));
        operationPanel.add(isbnField);
        operationPanel.add(borrowButton);
        operationPanel.add(returnButton);

        mainPanel.add(operationPanel, BorderLayout.SOUTH);

        // 添加事件监听器
        borrowButton.addActionListener(e -> borrowBook());
        returnButton.addActionListener(e -> returnBook());

        add(mainPanel);
    }

    private void borrowBook() {
        String isbn = isbnField.getText().trim();
        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入ISBN");
            return;
        }

        String result = recordService.borrowBook(isbn, currentReaderId);
        JOptionPane.showMessageDialog(this, result);
        
        if (result.equals("借书成功")) {
            isbnField.setText("");
            refreshBookList();
        }
    }

    private void returnBook() {
        String isbn = isbnField.getText().trim();
        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入ISBN");
            return;
        }

        String result = recordService.returnBook(isbn, currentReaderId);
        JOptionPane.showMessageDialog(this, result);
        
        if (result.equals("还书成功")) {
            isbnField.setText("");
            refreshBookList();
        }
    }

    public void setBookList(List<Book> books) {
        tableModel.setRowCount(0);
        for (Book book : books) {
            Object[] row = {
                book.getISBN(),
                book.getTitle(),
                book.getAuthors(),
                book.getPublisher(),
                book.getEditionNumber(),
                book.getPublicationDate(),
                book.getType()
            };
            tableModel.addRow(row);
        }
    }

    private void refreshBookList() {
        // 这里需要调用查询服务来刷新图书列表
        // 暂时留空，等待查询服务的实现
    }
}
