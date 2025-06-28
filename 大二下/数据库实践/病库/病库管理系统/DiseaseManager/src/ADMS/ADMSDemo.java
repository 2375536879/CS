package ADMS;

import javax.swing.*;

/**
 * 主程序入口
 * Allergic Disease Management System Demo
 */
public class ADMSDemo {
    public ADMSDemo(){
        init();
    }
    public void init(){
        SwingUtilities.invokeLater(() -> new ADMSFrame().init());
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ADMSFrame().init());
    }
}