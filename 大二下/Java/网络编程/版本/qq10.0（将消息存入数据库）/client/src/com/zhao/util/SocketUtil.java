package com.zhao.util;
//工具类

import com.zhao.common.Message;

import java.io.*;
import java.net.Socket;

public class SocketUtil {

    //单例模式
    //确保内存中同一时间内只有一个对象
    private static SocketUtil instance;
    private SocketUtil(){

    }

    /*
    获取单例对象的方法
     */
    public static SocketUtil getInstance(){
        if(instance==null){
            instance=new SocketUtil();
        }
        return instance;
    }

    //发送message
    public  void sendMessgae(Socket socket, Message requestMessage)throws Exception{
        OutputStream out= null;
        out = socket.getOutputStream();
        ObjectOutputStream oos=new ObjectOutputStream(out);
        oos.writeObject(requestMessage);
        oos.flush();
    }

    //获取message
    public Message getMessgae(Socket socket)throws Exception{

        InputStream in=socket.getInputStream();
        ObjectInputStream ois=new ObjectInputStream(in);
        Message responseMessage=(Message)ois.readObject();
        return responseMessage;
    }


}
