package com.zhao.view;

import com.zhao.Service.BookService;
import com.zhao.po.Book;
import com.zhao.po.BookQueryCondition;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class QueryBookView extends JFrame {

    // 成员变量声明
    private JTable table;  // 将 table 提升为成员变量
    private BookService bookService = new BookService();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QueryBookView queryBookView = new QueryBookView();
            queryBookView.createFrame();
        });
    }

    public void createFrame() {
        this.setTitle("图书查询");
        this.setBounds(850, 350, 900, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        // 北部面板 - 查询条件
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(7, 2, 10, 10));
        northPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField isbnField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField authorsField = new JTextField();
        JTextField publisherField = new JTextField();
        JTextField pubDateField = new JTextField();
        JTextField typeField = new JTextField();

        String[] sortOptions = {"请选择排序依据", "ISBN", "Title", "Authors", "Publisher", "PublicationDate", "Type"};
        JComboBox<String> sortComboBox = new JComboBox<>(sortOptions);

        JButton searchButton = new JButton("查询");

        // 添加组件到 northPanel
        northPanel.add(new JLabel("ISBN："));
        northPanel.add(isbnField);

        northPanel.add(new JLabel("标题："));
        northPanel.add(titleField);

        northPanel.add(new JLabel("作者："));
        northPanel.add(authorsField);

        northPanel.add(new JLabel("出版社："));
        northPanel.add(publisherField);

        northPanel.add(new JLabel("出版日期："));
        northPanel.add(pubDateField);

        northPanel.add(new JLabel("类型："));
        northPanel.add(typeField);

        // 新增一行：排序依据 + 查询按钮
        JPanel bottomRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomRow.add(new JLabel("排序依据："));
        bottomRow.add(sortComboBox);
        bottomRow.add(searchButton);
        northPanel.add(bottomRow);

        this.add(northPanel, BorderLayout.NORTH);

        // 中间面板 - 查询结果表格
        String[] columnNames = {"ISBN", "标题", "作者", "出版社", "出版日期", "类型"};
        Object[][] data = {}; // 初始空数据

        table = new JTable(data, columnNames);  // 初始化成员变量 table
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        this.setVisible(true);

        // 查询按钮点击事件
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BookQueryCondition condition = new BookQueryCondition();
                condition.setISBN(isbnField.getText().trim());
                condition.setTitle(titleField.getText().trim());
                condition.setAuthors(authorsField.getText().trim());
                condition.setPublisher(publisherField.getText().trim());
                condition.setPublicationDate(pubDateField.getText().trim());
                condition.setType(typeField.getText().trim());

                List<Book> bookList = bookService.queryBooks(condition);

                // 更新表格
                updateTable(bookList);
            }
        });
    }

    /**
     * 更新 JTable 数据的方法
     */
    private void updateTable(List<Book> bookList) {
        String[] columnNames = {"ISBN", "标题", "作者", "出版社", "出版日期", "类型"};

        if (bookList == null || bookList.isEmpty()) {
            // 如果没有数据，显示空表格
            table.setModel(new DefaultTableModel(new Object[0][0], columnNames));
            return;
        }

        // 创建二维数组填充数据
        Object[][] data = new Object[bookList.size()][columnNames.length];
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            data[i][0] = book.getISBN();
            data[i][1] = book.getTitle();
            data[i][2] = book.getAuthors();
            data[i][3] = book.getPublisher();
            data[i][4] = book.getPublicationDate();
            data[i][5] = book.getType();
        }

        // 设置新的表格模型
        table.setModel(new DefaultTableModel(data, columnNames));
    }
}