package main.java.util;
/*
数据库工具类
 */

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {
    private String dbUrl="jdbc:mysql://localhost:3306/db_book";//数据库连接地址
    private String dbUser="root";
    private String dbPass="123456";
    private String jdbcName="com.mysql.jdbc.Driver";//驱动名称    连接要有jdbc的驱动类

    //获取数据库连接
public Connection getCon()throws Exception{
    Class.forName(jdbcName);
    Connection con= DriverManager.getConnection(dbUrl,dbUser,dbPass);
    return con;
}
//关闭数据库连接
    public void closeCon(Connection con)throws Exception{
    if(con!=null){
        con.close();
    }
    }

    public static void main(String[] args) {
        DbUtil dbUtil=new DbUtil();
        try {
            dbUtil.getCon();
            System.out.println("数据库连接成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }

    }

}

