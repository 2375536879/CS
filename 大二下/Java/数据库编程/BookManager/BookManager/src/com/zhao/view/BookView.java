package com.zhao.view;

import com.zhao.Service.BookService;
import com.zhao.po.Book;
import com.zhao.po.BookQueryCondition;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookView extends JFrame {
    private JTextField isbnField;
    private JTextField titleField;
    private JTextField authorsField;
    private JTextField publisherField;
    private JTextField editionField;
    private JTextField dateField;
    private JTextField typeField;
    
    private JTable bookTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    
    private BookService bookService;
    
    public void createFrame() {
        bookService = new BookService();
        
        setTitle("图书管理");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // 创建搜索面板
        JPanel searchPanel = createSearchPanel();
        add(searchPanel, BorderLayout.NORTH);
        
        // 创建表格
        createTable();
        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // 创建按钮面板
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        
        // 初始加载所有图书
        refreshBookList();
        
        setVisible(true);
    }
    
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 4, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // 添加搜索字段
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
        
        searchButton = new JButton("搜索");
        searchButton.addActionListener(e -> handleSearch());
        panel.add(searchButton);
        
        return panel;
    }
    
    private void createTable() {
        String[] columnNames = {"ISBN", "书名", "作者", "出版社", "版次", "出版日期", "类型"};
        bookTable = new JTable(new DefaultTableModel(columnNames, 0));
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        addButton = new JButton("添加");
        updateButton = new JButton("修改");
        deleteButton = new JButton("删除");
        
        addButton.addActionListener(e -> handleAdd());
        updateButton.addActionListener(e -> handleUpdate());
        deleteButton.addActionListener(e -> handleDelete());
        
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        
        return panel;
    }
    
    private void handleSearch() {
        BookQueryCondition condition = new BookQueryCondition();
        condition.setISBN(isbnField.getText().trim());
        condition.setTitle(titleField.getText().trim());
        condition.setAuthors(authorsField.getText().trim());
        condition.setPublisher(publisherField.getText().trim());
        
        String editionStr = editionField.getText().trim();
        if (!editionStr.isEmpty()) {
            try {
                condition.setEditionNumber(Integer.parseInt(editionStr));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "版次必须是数字", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        condition.setPublicationDate(dateField.getText().trim());
        condition.setType(typeField.getText().trim());
        
        List<Book> books = bookService.queryBooks(condition);
        updateTable(books);
    }
    
    private void handleAdd() {
        BookDialog dialog = new BookDialog(this, "添加图书", null);
        dialog.setVisible(true);
        if (dialog.getBook() != null) {
            if (bookService.addBook(dialog.getBook())) {
                JOptionPane.showMessageDialog(this, "添加成功！");
                refreshBookList();
            } else {
                JOptionPane.showMessageDialog(this, "添加失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void handleUpdate() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要修改的图书", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Book book = getBookFromSelectedRow();
        BookDialog dialog = new BookDialog(this, "修改图书", book);
        dialog.setVisible(true);
        
        if (dialog.getBook() != null) {
            if (bookService.updateBook(dialog.getBook())) {
                JOptionPane.showMessageDialog(this, "修改成功！");
                refreshBookList();
            } else {
                JOptionPane.showMessageDialog(this, "修改失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void handleDelete() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的图书", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String isbn = (String) bookTable.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, 
            "确定要删除这本图书吗？", 
            "确认删除", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (bookService.deleteBook(isbn)) {
                JOptionPane.showMessageDialog(this, "删除成功！");
                refreshBookList();
            } else {
                JOptionPane.showMessageDialog(this, "删除失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private Book getBookFromSelectedRow() {
        int row = bookTable.getSelectedRow();
        Book book = new Book();
        book.setISBN((String) bookTable.getValueAt(row, 0));
        book.setTitle((String) bookTable.getValueAt(row, 1));
        book.setAuthors((String) bookTable.getValueAt(row, 2));
        book.setPublisher((String) bookTable.getValueAt(row, 3));
        book.setEditionNumber(Integer.parseInt(bookTable.getValueAt(row, 4).toString()));
        book.setPublicationDate((String) bookTable.getValueAt(row, 5));
        book.setType((String) bookTable.getValueAt(row, 6));
        return book;
    }
    
    private void refreshBookList() {
        BookQueryCondition condition = new BookQueryCondition();
        List<Book> books = bookService.queryBooks(condition);
        updateTable(books);
    }
    
    private void updateTable(List<Book> books) {
        DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
        model.setRowCount(0);
        
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
            model.addRow(row);
        }
    }
}
