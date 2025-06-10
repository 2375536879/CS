package com.zhao.dao;

import com.zhao.po.User;
import com.zhao.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {



    public User login(String username, String password){

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn= JDBCUtil.getInstance().getConnection();
            String sql="select id,user_name,password from user where user_name=? and password=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            rs=ps.executeQuery();
            if(rs.next()){
                user=new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));

                return user;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.getInstance().closeConnection(rs,ps,conn);
        }

        return null;
    }


}
