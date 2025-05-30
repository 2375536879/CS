package com.zhao.view;

import com.zhao.common.Message;
import com.zhao.common.MessageType;
import com.zhao.po.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;

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


    //
    private static final int PORT=8888;
    private static final String HOST="127.0.0.1";

    public static void main(String[] args) {
        RegisterView registerView=new RegisterView();
        registerView.createFrame();
    }

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


                //装载好了客户端与服务端的通信信息
                Message message=new Message();
                message.setMsgType(MessageType.REGISTER);
                message.setUser(user);


                try {
                    //和本机建立连接通信
                    Socket socket=new Socket(HOST,PORT);

                    //向服务端写注册信息
                    OutputStream outputStream=socket.getOutputStream();
                    ObjectOutputStream objectOutputStream=new ObjectOutputStream(outputStream);
                    objectOutputStream.writeObject(message);
                    objectOutputStream.flush();

                    //从服务端读注册是否成功的信息
                    InputStream inputStream=socket.getInputStream();
                    ObjectInputStream objectInputStream=new ObjectInputStream(inputStream);
                    Message acceptMessage=(Message)objectInputStream.readObject();

                    if(acceptMessage.getMsgType()==MessageType.REGISTER_SUCCESS){
                        JOptionPane.showMessageDialog(RegisterView.this,"注册成功","提示",JOptionPane.WARNING_MESSAGE);//警告窗体
                        RegisterView.this.dispose();//注册成功，当前页面应该关闭
                    }else{
                        JOptionPane.showMessageDialog(RegisterView.this,"注册失败,账号已被注册","提示",JOptionPane.WARNING_MESSAGE);//警告窗体
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
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


        this.setTitle("QQ注册");
        this.setBounds(705,300,350,350);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
