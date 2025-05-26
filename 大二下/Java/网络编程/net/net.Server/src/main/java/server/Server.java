package main.java.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT=9527;
    public List<Socket> socketLists = new ArrayList<Socket>();
    private Socket socket;
    public void startServer(){
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("启动服务端");
            while(true){
                socket=serverSocket.accept();
                System.out.println(socket.getInetAddress()+"登陆成功");
                socketLists.add(socket);
               new ClientThead(socket);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

   class ClientThead implements Runnable{
       private Socket socket;
       public ClientThead(Socket socket){
           this.socket=socket;
           new Thread(this).start();
       }
       @Override
       public void run() {

           try {
               BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
               while(true){
                   String message=br.readLine();
                   if(message!=null){
                       System.out.println(socket.getInetAddress()+":"+message);
                       changeSendMessage(socket,message);
                   }
               }
           } catch (IOException e) {
               //throw new RuntimeException(e);
               System.out.println(socket.getInetAddress()+":"+"已退出");
           }

       }
   }


public void changeSendMessage(Socket currentSocket,String message){

             try {

                 for(Socket socket:socketLists){
                     if(currentSocket==socket){
                         continue;
                     }
                     PrintWriter pw=new PrintWriter(socket.getOutputStream());
                     pw.println(message);
                     pw.flush();
                 }

             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
    }

}
