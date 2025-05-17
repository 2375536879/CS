package main.java;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientDemo {
    public static void main(String[] args) {
        int port =9527;
        String ip="127.0.0.1";
        try {
            Socket socket = new Socket(ip, port);
            OutputStream os=socket.getOutputStream();
            PrintWriter pw =new PrintWriter(os);
            pw.println("Hello World");
            pw.flush();

            pw.close();

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
