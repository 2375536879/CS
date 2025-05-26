package main.java.server02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
socket服务端程序
1.实例化ServerSocket
2.监听端口
 */

public class ServerDemo {
    private   static final int PORT=9527;
    private Socket socket;

    //启动socket服务端程序
    //实例ServerSocket 监听socket
    public ServerDemo() {
        try {
            System.out.println("启动Socket服务端");
            ServerSocket serverSocket = new ServerSocket(PORT);//创建ServerSocket实例
            System.out.println("监听端口中");
            socket = serverSocket.accept();//监听端口

            System.out.println("Socket连接建立");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //读消息
public String readMessage(){
        String line=null;
    try {
        BufferedReader br =new BufferedReader(new InputStreamReader(socket.getInputStream()));
         line=br.readLine();


    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    return line;
}

//循环接受消息
    public void receiveMessage(){
        while(true){

         String message=   readMessage();
         if(message!=null){
             System.out.println("客户端:"+message);
         }

        }
    }



    //发送消息
    public void sendMessage(String message){
        try {
            PrintWriter sendPw=new PrintWriter(socket.getOutputStream());
            sendPw.println(message);
            sendPw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //从键盘读信息
    public String readMessageByKeyBoard(){
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        String line="";
        try {
            //System.out.println("服务端:");
            line=br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return line;
    }


    //从键盘读消息发送
    public void sendMessage(){

        while(true){
            String message=readMessageByKeyBoard();
            sendMessage(message);
        }
    }



    //关闭socket
    public void close(){
        try {
            if(socket!=null){
                socket.close();
            }
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}


