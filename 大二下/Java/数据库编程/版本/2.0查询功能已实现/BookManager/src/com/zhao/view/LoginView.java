package com.zhao.view;

import com.zhao.Service.UserService;
import com.zhao.po.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginView extends JFrame {


    public static void main(String[] args) {

        LoginView loginView = new LoginView();
        loginView.createFrame();
    }


    //北部的组件
    JPanel northPanel = null;
    JLabel photoLabel = null;

    //中间的组件
    JPanel centerPanel = null;
    JLabel userNameLabel = null;
    JTextField userNameTextField = null;
    JLabel passwordLabel = null;
    JPasswordField passwordTextField = null;

    //南部的组件
    JPanel southPanel = null;
    JButton loginButton = null;
    JButton registerButton = null;



    public void createFrame(){

        //北部的图片
        ImageIcon imageIcon=new ImageIcon(LoginView.class.getClassLoader().getResource("com/zhao/images/Login.png"));
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(200, 150, Image.SCALE_DEFAULT));
        photoLabel=new JLabel("",imageIcon,JLabel.CENTER);
        northPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.add(photoLabel);
        this.add(northPanel,BorderLayout.NORTH);

        //中部的组件
        userNameLabel=new JLabel("账号");
        userNameTextField=new JTextField(30);
        passwordLabel=new JLabel("密码");
        passwordTextField=new JPasswordField(30);
        centerPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(userNameLabel);
        centerPanel.add(userNameTextField);
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordTextField);
        this.add(centerPanel,BorderLayout.CENTER);

        //南部的组件
        loginButton=new JButton("登录");
        loginButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                String  userName=userNameTextField.getText();
                String  password=passwordTextField.getText();

                User user=new User();
                user.setUserName(userName);
                user.setPassword(password);

                UserService userService=new UserService();
                if(userService.login(user)){

                    LoginView.this.dispose();
                    //启动书城
                    HomeView homeView=new HomeView();
                    homeView.createFrame();
                    System.out.println("登录成功");


                }


            }



        });



        registerButton=new JButton("注册");





        southPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.add(loginButton);
        southPanel.add(registerButton);
        this.add(southPanel,BorderLayout.SOUTH);


        this.setTitle("书城登录");
        this.setBounds(505,300,350,350);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



    }





}
