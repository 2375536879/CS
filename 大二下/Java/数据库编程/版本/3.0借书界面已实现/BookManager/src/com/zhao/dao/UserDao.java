package com.zhao.dao;

import com.zhao.po.User;
import com.zhao.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {

    public User login(String username, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = JDBCUtil.getInstance().getConnection();
            String sql = "SELECT id, user_name, password FROM user WHERE user_name = ? AND password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.getInstance().closeConnection(rs, ps, conn);
        }

        return user;
    }

    public boolean register(User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = JDBCUtil.getInstance().getConnection();
            String sql = "INSERT INTO user (user_name, password) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            
            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.getInstance().closeConnection(null, ps, conn);
        }

        return success;
    }
}
