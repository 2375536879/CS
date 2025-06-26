package com.szz.view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginView extends JFrame {

    //北部的组件
    private JPanel northPanel = null;
    private JLabel photoLabel = null;

    //中间的组件
    private JPanel centerPanel = null;
    private JLabel userNameLabel = null;
    private JTextField userNameTextField = null;
    private JLabel passwordLabel = null;
    private JPasswordField passwordTextField = null;

    //南部的组件
    private JPanel southPanel = null;
    private JButton loginButton = null;
    private JButton registerButton = null;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.createFrame();
        });
    }

    public void createFrame() {
        //北部的图片
        ImageIcon imageIcon = new ImageIcon(LoginView.class.getClassLoader().getResource("com/zhao/images/Login.png"));
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(200, 150, Image.SCALE_DEFAULT));
        photoLabel = new JLabel("", imageIcon, JLabel.CENTER);
        northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.add(photoLabel);
        this.add(northPanel, BorderLayout.NORTH);

        //中部的组件
        userNameLabel = new JLabel("账号");
        userNameTextField = new JTextField(30);
        passwordLabel = new JLabel("密码");
        passwordTextField = new JPasswordField(30);
        centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(userNameLabel);
        centerPanel.add(userNameTextField);
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordTextField);
        this.add(centerPanel, BorderLayout.CENTER);

        //南部的组件
        loginButton = new JButton("登录");
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO: 主界面

            }
        });

        registerButton = new JButton("注册");
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO: 实现注册功能
                RegisterView registerView=new RegisterView();
                registerView.createFrame();            }
        });

        southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.add(loginButton);
        southPanel.add(registerButton);
        this.add(southPanel, BorderLayout.SOUTH);

        this.setTitle("图书管理系统登录");
        this.setBounds(505, 300, 350, 350);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}