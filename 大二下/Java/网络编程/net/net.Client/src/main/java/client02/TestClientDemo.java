package main.java.client02;

public class TestClientDemo {
    public static void main(String[] args) {
        ClientDemo client = new ClientDemo();

       new Thread(new Runnable(){
           @Override
           public void run(){
               client.sendMessage();
           }
       }).start();

       new Thread(new Runnable() {
           @Override
           public void run() {
               client.receiveMessage();
           }
       }).start();


       // client.sendMessage();
        //client.close();

    }
}
