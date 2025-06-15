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
    private JTextField readerIdField;
    private JTextField isbnField;
    private JButton borrowButton;
    private JButton returnButton;
    private RecordService recordService;

  //  private int currentReaderId;


    public void createFrame() {
        recordService = new RecordService();
        setTitle("借阅管理");
        setBounds(550,400,800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 初始化表格模型与表格
        String[] columnNames = {"ISBN", "书名", "作者", "出版社", "版次", "出版日期", "类型"};
        tableModel = new DefaultTableModel(columnNames, 0);
        bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);


        // 操作面板
        JPanel operationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        readerIdField = new JTextField("请输入读者ID", 15);
        // 添加焦点监听器
        readerIdField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                JTextField textField = (JTextField) evt.getComponent();
                if (textField.getText().equals("请输入读者ID")) {
                    textField.setText(""); // 清空提示文字
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                JTextField textField = (JTextField) evt.getComponent();
                if (textField.getText().isEmpty()) {
                    textField.setText("请输入读者ID"); // 恢复提示文字
                }
            }
        });

      //  this.currentReaderId=Integer.valueOf(readerIdField.getText());

        isbnField = new JTextField(15);
        borrowButton = new JButton("借阅");
        returnButton = new JButton("归还");

        operationPanel.add(new JLabel("读者ID:"));
        operationPanel.add(readerIdField);
        operationPanel.add(new JLabel("ISBN:"));
        operationPanel.add(isbnField);
        operationPanel.add(borrowButton);
        operationPanel.add(returnButton);

        mainPanel.add(operationPanel, BorderLayout.SOUTH);

        // 添加事件监听
        borrowButton.addActionListener(e -> handleBorrow());
        returnButton.addActionListener(e -> handleReturn());

        this.add(mainPanel);
        setVisible(true);
    }

    private void handleBorrow() {
        String isbn = isbnField.getText().trim();
        Integer currentReaderId=Integer.valueOf(readerIdField.getText());
        if (isbn.isEmpty()) {
            showErrorMessage("请输入有效的 ISBN");
            return;
        }

        String result = recordService.borrowBook(isbn, currentReaderId);
        JOptionPane.showMessageDialog(this, result);

        if ("借书成功".equals(result)) {
            isbnField.setText("");
            refreshBookList();
        }
    }

    private void handleReturn() {
        String isbn = isbnField.getText().trim();
        Integer currentReaderId=Integer.valueOf(readerIdField.getText());

        if (isbn.isEmpty()) {
            showErrorMessage("请输入有效的 ISBN");
            return;
        }

        String result = recordService.returnBook(isbn, currentReaderId);
        JOptionPane.showMessageDialog(this, result);

        if ("还书成功".equals(result)) {
            isbnField.setText("");
            refreshBookList();
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "输入错误", JOptionPane.WARNING_MESSAGE);
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
//        List<Book> books = recordService.getAvailableBooks();
//        setBookList(books);
    }
}