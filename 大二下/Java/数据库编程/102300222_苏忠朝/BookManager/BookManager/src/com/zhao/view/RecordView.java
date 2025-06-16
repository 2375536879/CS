package com.zhao.view;

import com.zhao.Service.RecordService;
import com.zhao.dao.RecordDao;
import com.zhao.po.Record;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RecordView extends JFrame {


    private JTextField readerIdField;
    private JButton queryButton;
    private JPanel northPanel;
    private JPanel centerPanel;
    private JTable recordTable;
    private List<Record> recordList;
    RecordService recordService;

    public void createFrame(){

       recordService=new RecordService();


        this.setTitle("借阅记录查询");
        this.setBounds(500,200,600,600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 关闭窗口不退出程序
        this.setLayout(new BorderLayout(10, 10)); // 主布局使用 BorderLayout


        //北部面板
        northPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel("读者ID：");
        readerIdField = new JTextField(15); // 输入框长度为15字符

        queryButton = new JButton("查询");
        queryButton.addActionListener(e->handleRecord());

        northPanel.add(label);
        northPanel.add(readerIdField);
        northPanel.add(queryButton);

        this.add(northPanel,BorderLayout.NORTH);

        //中间面板
        centerPanel=new JPanel();
        String[] columnNames = {"RecordID", "ISBN", "ReaderID", "BorrowingDate", "ReturnDate"};
        Object[][] data = {}; // 初始空数据

        recordTable = new JTable(data, columnNames);  // 初始化成员变量 table
        JScrollPane scrollPane = new JScrollPane(recordTable);
        centerPanel.add(scrollPane);

        this.add(centerPanel,BorderLayout.CENTER);



        this.setVisible(true);

    }

    public void handleRecord() {

        int readerId = -1;
        readerId = Integer.valueOf(readerIdField.getText());
        recordList = new ArrayList<>();
        recordList = recordService.getReaderRecords(readerId);
        recordService.update(recordList,recordTable);

    }







}
