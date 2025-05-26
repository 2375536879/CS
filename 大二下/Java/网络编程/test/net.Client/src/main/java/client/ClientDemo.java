package main.java.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*socket客户端程序
1.实例化socket 发起socket连接
 */
public class ClientDemo {

     private static final String HOST ="127.0.0.1";
     private static final int PORT=9527;
     private Socket socket;

    public  ClientDemo() {
        try {
             socket = new Socket(HOST,PORT);
            //  socket = serverSocket.accept();//监听端口
             //服务端监听到的就是这个
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


     //发送消息
    public void sendMessage(String message){
        try {
            PrintWriter pw=new PrintWriter(socket.getOutputStream());
            //发消息的本质就是向socket输出流水里写东西
            pw.println(message);
            pw.flush();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

 //从键盘读消息发送
    public void sendMessage(){

        while(true){
            String message=readMessageByKeyBoard();
            sendMessage(message);
        }
    }


    //从键盘读信息
    public String readMessageByKeyBoard(){
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        String line="";
        try {
            System.out.println("请输入发送内容:");
           line=br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return line;
    }

    //关闭socket
    public void close(){
        try {
            if(socket!=null){
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
