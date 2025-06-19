package com.szz.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static java.lang.Class.forName;

public class JDBCUtil {

    private static JDBCUtil jdbcUtil = null;
    public JDBCUtil() {
    }

    //单例模式
    public static JDBCUtil getInstance(){
        if(jdbcUtil==null){
            jdbcUtil = new JDBCUtil();
        }
        return jdbcUtil;
    }

    //注册驱动
    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    //获取数据库连接
    public static Connection getConnection()throws Exception{
        //url jdbc:mysql是一个协议
        return DriverManager.getConnection(PropertiesUtil.getInstance().getValue("url"),PropertiesUtil.getInstance().getValue("username"),PropertiesUtil.getInstance().getValue("password"));

    }

    //关闭数据库连接
    public void closeConnection(ResultSet resultSet, Statement statement, Connection connection){

        //3重try catch,最终关的是connection
        try{
            resultSet.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                statement.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            finally {
                try{
                    connection.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }


    }

}
