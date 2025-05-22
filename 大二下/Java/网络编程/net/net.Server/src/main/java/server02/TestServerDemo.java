package main.java.server02;

public class TestServerDemo {
    public static void main(String[] args) {
       ServerDemo server = new ServerDemo();//启动socket服务

       //匿名线程
       new Thread(new Runnable(){
       @Override
       public void run(){
            server.sendMessage();
            }
       }).start();

        new Thread(new Runnable(){
            @Override
            public void run(){
                server.receiveMessage();
            }
        }).start();

       // server.receiveMessage();
       // server.sendMessage();
        //server.close();

    }
}
