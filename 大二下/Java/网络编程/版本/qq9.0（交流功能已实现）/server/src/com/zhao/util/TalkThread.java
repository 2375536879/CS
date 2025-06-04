package com.zhao.util;

import com.zhao.common.Message;
import com.zhao.common.MessageType;

import java.net.Socket;

public class TalkThread extends Thread {

    //从客户端来的socket,来一个建立一个线程
    Socket socket=null;

    public TalkThread(Socket socket) {
        this.socket=socket;
    }

    @Override
    public void run() {

        while(true){
            //已经建立了连接，双击时建立的
            try {
                Message requestMessage=SocketUtil.getInstance().getMessgae(socket);
                if(requestMessage.getMsgType()== MessageType.TALK){
                    //聊天message
                   System.out.println("@@"+requestMessage.getContent());

                    Socket friendSocket=TalkThreadCache.talkThreadCache.get(requestMessage.getFriendName()+"-"+requestMessage.getUserName()).socket;

                    Message responseMessage=new Message();
                    responseMessage.setMsgType(MessageType.TALK);
                    responseMessage.setContent(requestMessage.getContent());

                    SocketUtil.getInstance().sendMessgae(friendSocket,responseMessage);




                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
