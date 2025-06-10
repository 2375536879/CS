package com.zhao.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomeView extends JFrame {



    public void createFrame(){

        setTitle("图书管理系统");
        setBounds(350,350,800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        // 居中显示
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 创建顶部标签
        JLabel titleLabel = new JLabel("图书管理系统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        this.add(titleLabel, BorderLayout.NORTH);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2, 20, 20)); // 3行2列，间距20
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 100, 100));

        // 添加按钮
        JButton queryBookButton = new JButton("图书查询");
        JButton borrowBookButton = new JButton("借书操作");
        JButton returnBookButton = new JButton("还书操作");
        JButton manageBooksButton = new JButton("图书管理");
        JButton viewRecordsButton = new JButton("借阅记录查询");

        // 设置按钮字体
        Font btnFont = new Font("微软雅黑", Font.PLAIN, 16);
        for (JButton btn : new JButton[]{
                queryBookButton, borrowBookButton, returnBookButton,
                manageBooksButton,viewRecordsButton}) {
            btn.setFont(btnFont);
            btn.setPreferredSize(new Dimension(200, 60));
            buttonPanel.add(btn);
        }

        // 添加按钮面板到中间区域
        add(buttonPanel, BorderLayout.CENTER);

        // 显示窗口
        setVisible(true);


        queryBookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                QueryBookView queryBookView = new QueryBookView();
                queryBookView.createFrame();
            }
        });




    }







}



