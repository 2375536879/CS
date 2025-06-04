package com.zhao.dao;

import com.zhao.common.Message;
import com.zhao.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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



    //获取数据库里面的留言信息
    public List<Message> getMessages(String userName, String friendName){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Message> messages = null;

        try {
            //获取数据库连接
            conn= JDBCUtil.getInstance().getConnection();
            //准备数据库语句
            //谁发给谁的未读信息
            StringBuffer sql=new StringBuffer("select id,user_name,friend_name,is_read,content from message where is_read=0 and user_name=? and friend_name=?");
            ps=conn.prepareStatement(sql.toString());
            ps.setString(1,userName);
            ps.setString(2,friendName);
            //执行sql
            rs=ps.executeQuery();
            //容器生成
            messages=new ArrayList<>();
            //遍历信息
            while(rs.next()){
                Message message=new Message();
                message.setUserName(rs.getString("user_name"));
                message.setFriendName(rs.getString("friend_name"));
                message.setContent(rs.getString("content"));
                messages.add(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtil.getInstance().closeConnection(rs,ps,conn);
        }

        return messages;
    }



}
