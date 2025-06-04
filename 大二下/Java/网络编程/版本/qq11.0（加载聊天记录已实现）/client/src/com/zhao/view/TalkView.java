package com.zhao.view;

import com.zhao.common.Message;
import com.zhao.common.MessageType;
import com.zhao.util.SocketUtil;
import com.zhao.util.TalkThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

public class TalkView extends JFrame {

    String currentName;
    String friendName;

    //聊天界面的组件
    JTextArea talkArea=null;//历史聊天记录
    JScrollPane talkAreaScrollPane=null;
    //输入信息和发送按钮
    JTextField jTextField=null;
    JButton sendButton=null;
    JPanel southJPanel=null;


    //和某个朋友的聊天信道
    Socket socket=null;

    private static final int PORT=8888;
    private static final String HOST="127.0.0.1";


    public TalkView(String currentName, String friendName) {
        this.currentName = currentName;
        this.friendName = friendName;
    }
    public void createFrame(){

        talkArea=new JTextArea();
        talkArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        talkArea.setEditable(false);
        talkAreaScrollPane=new JScrollPane(talkArea);
        //历史聊天记录放在中间
        this.add(talkAreaScrollPane,BorderLayout.CENTER);

        jTextField=new JTextField(13);
        jTextField.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
        sendButton=new JButton("发送");

        sendButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){

                String content=jTextField.getText();
                content=currentName+"对"+friendName+"说: "+content;
                talkArea.append(content+"\n");
                jTextField.setText("");


                Message requestMessage=new Message();
                requestMessage.setMsgType(MessageType.TALK);
                requestMessage.setUserName(currentName);
                requestMessage.setFriendName(friendName);
                requestMessage.setContent(content);

                try {
                    //直接发就行，双击打开窗口时已经建立了连接
                    SocketUtil.getInstance().sendMessgae(socket,requestMessage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }
        });

        southJPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        southJPanel.add(jTextField);
        southJPanel.add(sendButton);

        this.add(southJPanel,BorderLayout.SOUTH);



        this.setTitle(currentName+"和"+friendName+"聊天界面");
        this.setBounds(450 ,180 ,400,300);
        this.setVisible(true);
        this.setResizable(false);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        try {
            socket=new Socket(HOST,PORT);
            Message requestMessage=new Message();//专门用来建立连接

            //告知服务端用于和服务端建立socket连接，currentName和friendName用于给socket编号
            requestMessage.setMsgType(MessageType.TALK_CONNECTION);
            requestMessage.setUserName(currentName);
            requestMessage.setFriendName(friendName);

            //将requestMessage发送到服务端
            SocketUtil.getInstance().sendMessgae(socket,requestMessage);

            TalkThread talkThread=new TalkThread(socket,talkArea);
            talkThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //关闭聊天窗口的时候，通知服务端结束多线程的运行
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                Message requestMessage=new Message();

                requestMessage.setMsgType(MessageType.TALK_CLOSE);
                //要把谁和谁的socket连接关闭
                requestMessage.setUserName(currentName);
                requestMessage.setFriendName(friendName);

                //发送消息给服务器，告知客户端已经关闭连接
                try {
                    SocketUtil.getInstance().sendMessgae(socket,requestMessage);
                    //服务端线程TalkThread会接受message
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }
}
