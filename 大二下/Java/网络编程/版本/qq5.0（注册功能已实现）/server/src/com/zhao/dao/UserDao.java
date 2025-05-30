package com.zhao.dao;

import com.zhao.po.User;
import com.zhao.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {

    public User login(String username, String pwd) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try{
            //获取数据库连接
            conn= JDBCUtil.getInstance().getConnection();
            //准备数据库语句
            StringBuffer sql = new StringBuffer("select id,user_name,pwd,real_name from user where user_name=? and pwd=?");//注意，数据库中字段名为user_name而不是username
            ps=conn.prepareStatement(sql.toString());
            ps.setString(1, username);
            ps.setString(2, pwd);
            //执行sql
            rs=ps.executeQuery();
            //遍历信息
            if(rs.next()){//如果rs有信息，说明user找到了
                user=new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("user_name"));
                user.setPwd(rs.getString("pwd"));
                user.setRealname("real_name");
                return user;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /*
    user信息存入数据库的方法
     */
    public User insertUser(User user) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            //获取数据库连接
            conn= JDBCUtil.getInstance().getConnection();
            //准备数据库语句
            StringBuffer sql = new StringBuffer("insert into user(user_name,pwd,real_name) value(?,?,?)");//注意，数据库中字段名为user_name而不是username
            ps=conn.prepareStatement(sql.toString());
            ps.setString(1,user.getUsername());
            ps.setString(2, user.getPwd());
            ps.setString(3, user.getRealname());
            //执行sql
           // rs=ps.executeQuery();
            ps.executeUpdate();
            //遍历信息

            return user;

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }


    /*
    根据username获取user信息，看是否已经存在
     */
    public User getByUsername(String username) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try{
            //获取数据库连接
            conn= JDBCUtil.getInstance().getConnection();
            //准备数据库语句
            StringBuffer sql = new StringBuffer("select id,user_name,pwd,real_name from user where user_name=? ");//注意，数据库中字段名为user_name而不是username
            ps=conn.prepareStatement(sql.toString());
            ps.setString(1, username);
            //执行sql
            rs=ps.executeQuery();
            //遍历信息
            if(rs.next()){//如果rs有信息，说明user找到了
                user=new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("user_name"));
                user.setPwd(rs.getString("pwd"));
                user.setRealname("real_name");
                return user;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }



}
