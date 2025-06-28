package com.szz.test;

// ... existing code ...
import com.szz.util.JDBCUtil;

import java.sql.Connection;

public class ConnectionTest {
    public static void main(String[] args) {
        testDatabaseConnection();
    }

    private static void testDatabaseConnection() {
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            if (connection != null && !connection.isClosed()) {
                System.out.println("数据库连接成功！");
            } else {
                System.out.println("数据库连接失败！");
            }
        } catch (Exception e) {
            System.out.println("发生异常：数据库连接失败！");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
