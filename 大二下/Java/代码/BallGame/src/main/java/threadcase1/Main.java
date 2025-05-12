package main.java.threadcase1;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater( () -> {
            BallGame ballGame=new BallGame();
            ballGame.setVisible(true);
        });
    }
}
