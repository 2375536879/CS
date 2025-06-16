package com.zhao.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomeView extends JFrame {
    private int currentReaderId;

    public HomeView(int readerId) {
        this.currentReaderId = readerId;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomeView homeView = new HomeView(1); // 默认读者ID为1
            homeView.createFrame();
        });
    }

    public void createFrame() {
        setTitle("图书管理系统");
        setBounds(350, 350, 800, 600);
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
        JButton viewRecordsButton = new JButton("借阅记录查询");
        JButton manageBooksButton = new JButton("图书管理");
        JButton manageReadersButton = new JButton("读者管理");
        // 设置按钮字体
        Font btnFont = new Font("微软雅黑", Font.PLAIN, 16);
        for (JButton btn : new JButton[]{
                queryBookButton, borrowBookButton, returnBookButton,
                viewRecordsButton, manageBooksButton, manageReadersButton}) {
            btn.setFont(btnFont);
            btn.setPreferredSize(new Dimension(200, 60));
            buttonPanel.add(btn);
        }

        // 添加按钮面板到中间区域
        add(buttonPanel, BorderLayout.CENTER);

        // 显示窗口
        setVisible(true);

        // 图书查询按钮点击事件
        queryBookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                QueryBookView queryBookView = new QueryBookView();
                queryBookView.createFrame();
            }
        });

        // 借书按钮点击事件
        borrowBookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BorrowView borrowView = new BorrowView();
                borrowView.createFrame();
                borrowView.setVisible(true);
            }
        });

        // 还书按钮点击事件
        returnBookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BorrowView returnView = new BorrowView();
                returnView.createFrame();
                returnView.setVisible(true);
            }
        });



        // 借阅记录查询按钮点击事件
        viewRecordsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO: 实现借阅记录查询功能
                //JOptionPane.showMessageDialog(HomeView.this, "借阅记录查询功能待实现");
                RecordView recordView=new RecordView();
                recordView.createFrame();
            }
        });

        // 图书管理按钮点击事件
        manageBooksButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO: 实现图书管理功能
                //JOptionPane.showMessageDialog(HomeView.this, "图书管理功能待实现");
                BookView bookView=new BookView();
                bookView.createFrame();
            }
        });


        manageReadersButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                // TODO: 实现读者管理功能
               // JOptionPane.showMessageDialog(HomeView.this, "读者管理功能待实现");
                ReaderView readerView =new ReaderView();
                readerView.createFrame();
            }

        });
    }
}



