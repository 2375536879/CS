package main.java.client;

public class TestClientDemo {
    public static void main(String[] args) {
        ClientDemo client = new ClientDemo();
        client.sendMessage();
        client.close();

    }
}
