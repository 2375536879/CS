package com.zhao.util;

import com.zhao.common.Message;
import com.zhao.common.MessageType;

import javax.swing.*;
import java.net.Socket;
import java.util.List;

//等待服务端转发消息过来
public class TalkThread extends Thread {
   Socket socket;

   JTextArea textArea;  //服务端发来的消息展示

    Boolean isRun=true;

    public TalkThread(Socket socket, JTextArea textArea) {
        this.socket = socket;
        this.textArea = textArea;
    }

    @Override
    public void run() {

        while(isRun){

            try {
                //接收服务端发俩的消息
                Message responseMessage =  SocketUtil.getInstance().getMessgae(socket);
                if(responseMessage.getMsgType()== MessageType.TALK){
                    System.out.println("@@"+responseMessage.getContent());
                    textArea.append(responseMessage.getContent()+"\n");
                }else if(responseMessage.getMsgType()== MessageType.TALK_LEAVING){
                    List<Message>messgaeList=responseMessage.getMessages();
                    for(int i=0;i<messgaeList.size();i++){
                        Message message=messgaeList.get(i);
                        textArea.append(message.getContent()+"\n");
                    }

                }else if(responseMessage.getMsgType()==MessageType.TALK_CLOSE){
                    isRun=false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



}
