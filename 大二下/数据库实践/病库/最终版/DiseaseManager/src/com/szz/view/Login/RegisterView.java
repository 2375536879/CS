package com.szz.view.Login;


import com.szz.model.User;
import com.szz.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterView extends JFrame {

    //注册界面中部的所有组件
    JPanel centerJPanel=null;
    JLabel userNameLabel=null;
    JTextField userNameTextField=null;
    JLabel pwdLabel=null;
    JPasswordField pwdTextField=null;
    JLabel realNameLabel=null;
    JTextField realNameTextField=null;

    //南部组件包括提交按钮和重置按钮
    JPanel southJPanel=null;
    JButton submitButton=null;
    JButton resetButton=null;



    private static final int PORT=8888;
    private static final String HOST="127.0.0.1";



    public void createFrame() {
        //中部组件初始化
        centerJPanel=new JPanel(new GridLayout(0, 1, 10, 15));// 使用 GridLayout：每行一列，水平间距10，垂直间距10
        centerJPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 增加内边距，看起来更舒适
        userNameLabel=new JLabel("用户名");
        userNameTextField=new JTextField(18);
        userNameTextField.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
        pwdLabel=new JLabel("密码");
        pwdTextField=new JPasswordField(18);
        pwdTextField.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
        realNameLabel=new JLabel("真实姓名");
        realNameTextField=new JTextField(18);
        realNameTextField.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));

        //装载中部组件
        centerJPanel.add(userNameLabel);
        centerJPanel.add(userNameTextField);
        centerJPanel.add(pwdLabel);
        centerJPanel.add(pwdTextField);
        centerJPanel.add(realNameLabel);
        centerJPanel.add(realNameTextField);
        this.add(centerJPanel,BorderLayout.CENTER);

        //南部组件初始化
        southJPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitButton=new JButton("提交");
        resetButton=new JButton("重置");
        //装载南部组件
        southJPanel.add(submitButton);
        southJPanel.add(resetButton);
        this.add(southJPanel,BorderLayout.SOUTH);



        //添加按钮响应
        submitButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                //获取注册用户的信息
                String userName=userNameTextField.getText();
                String pwd=new String(pwdTextField.getPassword());
                String realName=realNameTextField.getText();
                User user=new User();
                user.setUsername(userName);
                user.setPwd(pwd);
                user.setRealname(realName);



                UserService userService=new UserService();
                boolean isSuccess = userService.register(user); // 假设 register 方法返回 boolean 表示是否成功

                if (isSuccess) {
                    JOptionPane.showMessageDialog(RegisterView.this, "注册成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    // 注册成功后清空输入框（可选）
                    userNameTextField.setText("");
                    pwdTextField.setText("");
                    realNameTextField.setText("");
                } else {
                    JOptionPane.showMessageDialog(RegisterView.this, "注册失败，请重试！", "错误", JOptionPane.ERROR_MESSAGE);
                }


            }
        });


        resetButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                userNameTextField.setText("");
                pwdTextField.setText("");
                realNameTextField.setText("");
            }

        });


        this.setTitle("注册");
        this.setBounds(705,300,350,350);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


    }
}