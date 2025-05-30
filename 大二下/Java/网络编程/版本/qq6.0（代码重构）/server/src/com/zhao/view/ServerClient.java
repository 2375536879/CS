package com.zhao.view;

import com.zhao.common.Message;
import com.zhao.common.MessageType;
import com.zhao.dao.UserDao;
import com.zhao.po.User;
import com.zhao.service.ServerClientService;
import com.zhao.util.SocketUtil;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class ServerClient extends JFrame {

    public static void main(String[] args) {
       ServerClient serverClient = new ServerClient();
       serverClient.createJFrame();
    }

    private static final int PORT = 8888;
    ServerClientService service = new ServerClientService();

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
            service.startServer();
        } catch (Exception e) {
            e.printStackTrace();//打出异常元婴
        }

    }




}
