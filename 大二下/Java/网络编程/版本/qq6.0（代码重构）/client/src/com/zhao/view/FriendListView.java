package com.zhao.view;

import com.zhao.common.Message;
import com.zhao.common.MessageType;
import com.zhao.po.User;
import com.zhao.util.SocketUtil;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.List;
public class FriendListView extends JFrame {

    String username;
    private static final int PORT=8888;
    private static final String HOST="127.0.0.1";


    //可以滑动的好友列表
    JScrollPane jScrollPane = null;
    //放置好友列表的面板
    JPanel jPanel = null;
    //好友列表信息
    List<User> users;

    public FriendListView(String username) {
        this.username = username;
    }

    public void createFrame(){


        try {
            Socket socket = new Socket(HOST, PORT);
            Message message = new Message();
            message.setMsgType(MessageType.GET_USERS);

            //拿到和服务端建立的连接，获取输出流，向服务端输出message
            SocketUtil.getInstance().sendMessgae(socket, message);
            //从服务都得到好友信息
           Message responseMessage=SocketUtil.getInstance().getMessgae(socket);
            users = responseMessage.getUsers();

        }catch (Exception e){
            e.printStackTrace();
        }

        int userNum=users.size();
        jPanel = new JPanel(new GridLayout(userNum,1,15,15));
        for(int i = 0; i < userNum; i++){
            ImageIcon icon = new ImageIcon(FriendListView.class.getClassLoader().getResource("com/zhao/images/QQlogo4.png"));
            icon.setImage(icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));//100x100，不缩放
            JLabel jLabel = new JLabel(users.get(i).getUsername(),icon,JLabel.LEFT);
            jPanel.add(jLabel);
        }

        //网格布局放入可滚动的面板
        jScrollPane = new JScrollPane(jPanel);//把装载好网格的jPanel放进去
        this.add(jScrollPane,BorderLayout.CENTER);

        this.setTitle(this.username+"好友列表");
        this.setBounds(530,60,300,650);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
