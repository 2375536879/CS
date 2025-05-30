package com.zhao.service;

import com.zhao.common.Message;
import com.zhao.common.MessageType;
import com.zhao.po.User;

import java.io.*;
import java.net.Socket;

public class UserService {

    private static final int PORT=8888;
    private static final String HOST="127.0.0.1";
    public boolean login(User user) {

        try {
            //和本机建立连接通信
            Socket socket=new Socket(HOST,PORT);

            Message message=new Message();
            message.setMsgType(MessageType.LOGIN);
            message.setUser(user);
            //获取输出流，向外输出内容
            OutputStream out=socket.getOutputStream();
            ObjectOutputStream oos=new ObjectOutputStream(out);
            oos.writeObject(message);
            oos.flush();

            //通过socket连接的输入流，读入服务器的返回信息
            InputStream in=socket.getInputStream();
            ObjectInputStream ois=new ObjectInputStream(in);
            try {
                Message acceptMessage=(Message)ois.readObject();
                if(acceptMessage.getMsgType()== MessageType.LOGIN_SUCCESS){
                    return true;
                }else{
                    return false;
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
