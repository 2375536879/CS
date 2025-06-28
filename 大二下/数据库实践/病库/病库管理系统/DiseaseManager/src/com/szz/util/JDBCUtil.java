package com.szz.util;

import java.sql.*;

import static java.lang.Class.forName;

public class JDBCUtil {

    private static JDBCUtil jdbcUtil = null;

    public JDBCUtil() {
    }

    //单例模式
    public static JDBCUtil getInstance() {
        if (jdbcUtil == null) {
            jdbcUtil = new JDBCUtil();
        }
        return jdbcUtil;
    }

    //注册驱动
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    //获取数据库连接
    public static Connection getConnection() throws Exception {
        //url jdbc:mysql是一个协议
        return DriverManager.getConnection(PropertiesUtil.getInstance().getValue("url"), PropertiesUtil.getInstance().getValue("username"), PropertiesUtil.getInstance().getValue("password"));

    }

    //关闭数据库连接
    public void closeConnection(ResultSet resultSet, Statement statement, Connection connection) {
        // 关闭资源顺序：ResultSet -> Statement -> Connection
        // 每个资源独立处理，避免相互影响

        // 1. 关闭ResultSet
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace(); // 实际项目中可替换为日志记录
            }
        }

        // 2. 关闭Statement
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace(); // 实际项目中可替换为日志记录
            }
        }

        // 3. 关闭Connection
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); // 实际项目中可替换为日志记录
            }
        }
    }

}


