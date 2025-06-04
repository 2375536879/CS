package com.zhao.view;

import com.zhao.po.User;
import com.zhao.service.UserService;

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
    JPanel northJPanel = null;
    JLabel photoJLabel = null;

    //中间组件
    JLabel userNameJLabel = null;
    JTextField userNameJtextField = null;
    JLabel pwdJLabel = null;
    JPasswordField pwdJPasswordField = null;//密码框
    JPanel centerJPanel = null;

    //南部组件
    JButton loginJButton=null;
    JButton registerJButton=null;
     JPanel southJPanel = null;

    public void createFrame(){
        //北部的图片
        ImageIcon imageIcon=new ImageIcon(LoginView.class.getClassLoader().getResource("com/zhao/images/headshot.png"));
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        photoJLabel=new JLabel("",imageIcon,JLabel.CENTER);
        northJPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        northJPanel.add(photoJLabel);
        this.add(northJPanel,BorderLayout.NORTH);


        //中部的组件
        userNameJLabel=new JLabel("账号");
        userNameJtextField=new JTextField(30);
        pwdJLabel=new JLabel("密码");
        pwdJPasswordField=new JPasswordField(30);
        centerJPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerJPanel.add(userNameJLabel);
        centerJPanel.add(userNameJtextField);
        centerJPanel.add(pwdJLabel);
        centerJPanel.add(pwdJPasswordField);
        this.add(centerJPanel,BorderLayout.CENTER);


        //南部的按钮
        loginJButton=new JButton("登录");
        loginJButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String username=userNameJtextField.getText();
                String pwd=new String(pwdJPasswordField.getPassword());

                User user=new User();
                user.setUsername(username);
                user.setPwd(pwd);
                //在客户端进行登录验证还是在服务端进行登录验证？
                //肯定是服务器端  要用到service包

                UserService userService=new UserService();

              //  if(username.equals("root")&&pwd.equals("123456")){
                if(userService.login(user)==true){
                    LoginView.this.dispose();
                   // JOptionPane.showMessageDialog(LoginView.this,"用户登录成功","提示",JOptionPane.WARNING_MESSAGE);//警告窗体
                    FriendListView friendListView=new FriendListView(user.getUsername());
                    friendListView.createFrame();

                }else{
                    JOptionPane.showMessageDialog(LoginView.this,"账号或密码错误","提示",JOptionPane.WARNING_MESSAGE);//警告窗体
                }


            }
        });


        registerJButton=new JButton("注册");
        registerJButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
             RegisterView registerView=new RegisterView();
             registerView.createFrame();
            }


        });



        southJPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        southJPanel.add(loginJButton);
        southJPanel.add(registerJButton);
        this.add(southJPanel,BorderLayout.SOUTH);



        this.setTitle("QQ登录");
        this.setBounds(505,300,350,350);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
