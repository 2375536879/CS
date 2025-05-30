package com.zhao.view;

import com.zhao.common.Message;
import com.zhao.common.MessageType;
import com.zhao.dao.UserDao;
import com.zhao.po.User;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerClient extends JFrame {
        private static final int PORT = 8888;
        UserDao userDao = new UserDao();



    public static void main(String[] args) {
       ServerClient serverClient = new ServerClient();
       serverClient.createJFrame();
    }



    public void createJFrame(){

        JLabel label = new JLabel("服务器已启动，监听在"+PORT+"端口，等待客户端连接",JLabel.CENTER);
        this.add(label, BorderLayout.CENTER);

        this.setTitle("QQ服务器");
        this.setBounds(505,305,350,250);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //在创建服务端界面时，启动服务
        try {
            this.server();
        } catch (Exception e) {
            e.printStackTrace();//打出异常元婴
        }

    }

    //服务器端连接方法
    public  void server()throws Exception{

            //服务监听在8888端口上
            ServerSocket serverSocket = new ServerSocket(PORT);

            //通过循环来保障服务端一直有连接
            while(true){
                Socket socket= serverSocket.accept();

                //接收客户端的信息
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                Message requestMessage = (Message) objectInputStream.readObject();//客户端发来的请求

                switch(requestMessage.getMsgType()){
                    case MessageType.LOGIN:{
                        User user= requestMessage.getUser();
                        //根据输入的账号和密码确定message里面的内容
                        Message message=new Message();
                        //if(user.getUsername().equals("root")&&user.getPwd().equals("123456")){
                        if(userDao.login(user.getUsername(),user.getPwd())!=null){//查到了
                            message.setMsgType(MessageType.LOGIN_SUCCESS);

                        }else{
                            message.setMsgType(MessageType.LOGIN_FAIL);
                        }

                        //向客户端输出验证结果
                        OutputStream outputStream = socket.getOutputStream();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                        objectOutputStream.writeObject(message);
                        break;
                    }

                    case MessageType.REGISTER:{
                        //向客户端应答的message先new出来
                        Message responseMessage=new Message();
                        //通过客户端的请求message获取到注册的user信息
                        User user= requestMessage.getUser();

                        //通过getByUsername看是否Username已经被注册
                        if(userDao.getByUsername(user.getUsername())==null){
                            //将user信息存入数据库
                            userDao.insertUser(user);
                            responseMessage.setMsgType(MessageType.REGISTER_SUCCESS);
                        }else{
                            responseMessage.setMsgType(MessageType.REGISTER_FAIL);
                        }
                        OutputStream outputStream = socket.getOutputStream();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                        objectOutputStream.writeObject(responseMessage);
                        break;
                    }
                }




            }


    }


}
