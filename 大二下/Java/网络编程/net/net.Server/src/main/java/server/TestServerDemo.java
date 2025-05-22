package main.java.server;

public class TestServerDemo {
    public static void main(String[] args) {
       ServerDemo server = new ServerDemo();
        server.receiveMessage();
        server.close();

    }
}
