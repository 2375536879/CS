package com.zhao.view;

import javax.swing.*;
import java.awt.*;

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
        southJPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        southJPanel.add(jTextField);
        southJPanel.add(sendButton);

        this.add(southJPanel,BorderLayout.SOUTH);



        this.setTitle(currentName+"和"+friendName+"聊天界面");
        this.setBounds(450 ,180 ,400,300);
        this.setVisible(true);
        this.setResizable(false);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
