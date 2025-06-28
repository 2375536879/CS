package ADMS;

import javax.swing.SwingUtilities;

/**
 * 主程序入口
 * Allergic Disease Management System Demo
 */
public class ADMSDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ADMSFrame().init());
    }
}