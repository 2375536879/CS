package com.zhao.dao;

import com.zhao.common.Message;
import com.zhao.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MessageDao {

    public void insertMessage(Message requestMessage) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn= JDBCUtil.getInstance().getConnection();
            StringBuffer sql=new StringBuffer(  "insert into message(user_name,friend_name,is_read,content) value(?,?,?,?)");
            ps=conn.prepareStatement(sql.toString());
            ps.setString(1,requestMessage.getUserName());
            ps.setString(2,requestMessage.getFriendName());
            ps.setInt(3,requestMessage.getIsRead());
            ps.setString(4,requestMessage.getContent());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtil.getInstance().closeConnection(rs,ps,conn);
        }
    }


}
