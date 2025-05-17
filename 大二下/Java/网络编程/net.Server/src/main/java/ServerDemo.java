package main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDemo {

    public static void main(String[] args) {
            int port=9527;
        try {
            System.out.println("启动服务器成功，监听端口中"+port);
            ServerSocket serverSocket= new ServerSocket(port);
            Socket socket= serverSocket.accept();
            System.out.println("创建链接成功  "+port);

            InputStream in=socket.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(in));
            String line=br.readLine();
            System.out.println("接收到的消息是:"+line);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
