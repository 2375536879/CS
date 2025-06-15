package com.zhao.view;

import com.zhao.Service.RecordService;
import com.zhao.po.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

public class BorrowView extends JFrame {
    private JTable bookTable;
    private JTextField readerIdField;
    private JTextField isbnField;
    private JButton borrowButton;
    private JButton returnButton;
    private RecordService recordService;

  //  private int currentReaderId;


    public static void main(String[] args) {
        BorrowView borrowView=new BorrowView();
        borrowView.createFrame();
    }

    public void createFrame() {
        recordService = new RecordService();
        setTitle("借阅管理");
        setBounds(550, 400, 600, 200); // 调整高度为200，去除多余空白
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 添加外边距

        // 输入区域
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 左对齐排列
        readerIdField = new JTextField("请输入读者ID", 15);
        isbnField = new JTextField(15);

        // 添加提示文字处理
        setupPlaceholder(readerIdField, "请输入读者ID");
        setupPlaceholder(isbnField, "请输入ISBN");

        northPanel.add(new JLabel("读者ID:"));
        northPanel.add(readerIdField);
        northPanel.add(new JLabel("ISBN:"));
        northPanel.add(isbnField);

        // 按钮区域
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        borrowButton = new JButton("借阅");
        returnButton = new JButton("归还");

        buttonPanel.add(borrowButton);
        buttonPanel.add(returnButton);

        // 组合输入区和按钮区
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(buttonPanel);

        // 加入主面板
        mainPanel.add(northPanel, BorderLayout.PAGE_START); // 靠顶部显示
        mainPanel.add(centerPanel, BorderLayout.CENTER);    // 按钮居中显示

        // 事件监听
        borrowButton.addActionListener(e -> handleBorrow());
        returnButton.addActionListener(e -> handleReturn());

        add(mainPanel);
        pack(); // 自动调整窗口大小以适应内容
        setVisible(true);
    }

    private void setupPlaceholder(JTextField field, String placeholder) {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                }
            }
        });
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



    private void refreshBookList() {
//        List<Book> books = recordService.getAvailableBooks();
//        setBookList(books);
    }
}