package com.zhao.util;

import com.zhao.common.Message;
import com.zhao.common.MessageType;

import javax.swing.*;
import java.net.Socket;

//等待服务端转发消息过来
public class TalkThread extends Thread {
   Socket socket;

   JTextArea textArea;  //服务端发来的消息展示

    public TalkThread(Socket socket, JTextArea textArea) {
        this.socket = socket;
        this.textArea = textArea;
    }

    @Override
    public void run() {

        while(true){

            try {
                //接收服务端发俩的消息
                Message responseMessage =  SocketUtil.getInstance().getMessgae(socket);
                if(responseMessage.getMsgType()== MessageType.TALK){
                    System.out.println("@@"+responseMessage.getContent());
                    textArea.append(responseMessage.getContent()+"\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



}
