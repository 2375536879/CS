package com.zhao.view;

import com.zhao.po.Book;
import com.zhao.po.Reader;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class QueryBookView extends JFrame{

    public static void main(String[] args) {
        QueryBookView queryBookView = new QueryBookView();
        queryBookView.createFrame();
    }

    public void createFrame(){

        this.setTitle("图书查询");
        this.setBounds(850,350,900, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       // this.setLocationRelativeTo(null); // 居中显示
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setVisible(true);

        // 北部面板 - 查询条件
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(7, 2, 10, 10)); // 每行两个组件
        northPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 输入框定义
        JTextField isbnField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField authorsField = new JTextField();
        JTextField publisherField = new JTextField();
        JTextField pubDateField = new JTextField();
        JTextField typeField = new JTextField();

        // 排序下拉框
        String[] sortOptions = {"请选择排序依据", "ISBN", "Title", "Authors", "Publisher", "PublicationDate", "Type"};
        JComboBox<String> sortComboBox = new JComboBox<>(sortOptions);

        // 查询按钮
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
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        this.setVisible(true);



        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              Book book=new Book();
              

            }
        });



    }




}
