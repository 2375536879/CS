package com.zhao.service;

import com.zhao.common.Message;
import com.zhao.common.MessageType;
import com.zhao.po.User;
import com.zhao.util.SocketUtil;

import java.io.*;
import java.net.Socket;

public class UserService {

    private static final int PORT=8888;
    private static final String HOST="127.0.0.1";
    public boolean login(User user) {
        try {
            //和本机建立连接通信
            Socket socket = new Socket(HOST, PORT);

            Message message = new Message();
            message.setMsgType(MessageType.LOGIN);
            message.setUser(user);
            //获取输出流，向外输出内容
            SocketUtil.getInstance().sendMessgae(socket, message);
            //通过socket连接的输入流，读入服务器的返回信息
            Message responseMessage= SocketUtil.getInstance().getMessgae(socket);

            if(responseMessage.getMsgType() == MessageType.LOGIN_SUCCESS){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



}
