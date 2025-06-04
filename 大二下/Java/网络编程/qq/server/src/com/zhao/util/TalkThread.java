package com.zhao.util;

import com.zhao.common.Message;
import com.zhao.common.MessageType;
import com.zhao.dao.MessageDao;

import java.net.Socket;

public class TalkThread extends Thread {

    //从客户端来的socket,来一个建立一个线程
    Socket socket=null;

    Boolean isRun=true;

    public TalkThread(Socket socket) {
        this.socket=socket;
    }

    MessageDao messageDao=new MessageDao();

    @Override
    public void run() {

        while(isRun){
            //已经建立了连接，双击时建立的
            try {
                Message requestMessage=SocketUtil.getInstance().getMessgae(socket);

                if(requestMessage.getMsgType()== MessageType.TALK){
                    //聊天message
                   System.out.println("@@"+requestMessage.getContent());

                    TalkThread friendThread = TalkThreadCache.talkThreadCache.get(requestMessage.getFriendName()+"-"+requestMessage.getUserName());

                    if(friendThread!=null&&friendThread.socket != null){
                        Socket friendSocket=friendThread.socket;
                        Message responseMessage=new Message();
                        responseMessage.setMsgType(MessageType.TALK);
                        responseMessage.setContent(requestMessage.getContent());

                        SocketUtil.getInstance().sendMessgae(friendSocket,responseMessage);
                    }else{
                        //如果朋友没有登录或者点开我的聊天窗口，将信息保存到数据库,UserName,FriendName,content
                        //0代表未读
                        requestMessage.setIsRead(0);
                        messageDao.insertMessage(requestMessage);


                    }


                }else if(requestMessage.getMsgType()== MessageType.TALK_CLOSE){
                    this.isRun=false;

                    //告知客户端服务端的多线程程序已经结束
                    Socket currentSocket=TalkThreadCache.talkThreadCache.get(requestMessage.getUserName()+"-"+requestMessage.getFriendName()).socket;
                    //找的不是FriendSocket,找的是原来的Socket
                    Message responseMessage=new Message();
                    responseMessage.setMsgType(MessageType.TALK_CLOSE);
                    SocketUtil.getInstance().sendMessgae(currentSocket,responseMessage);

                    TalkThreadCache.talkThreadCache.remove(requestMessage.getUserName()+"-"+requestMessage.getFriendName());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
