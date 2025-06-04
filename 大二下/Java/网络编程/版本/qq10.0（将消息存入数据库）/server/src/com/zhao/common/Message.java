package com.zhao.common;

import com.zhao.po.User;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {//可序列化接口

    //谁发给谁
    private String userName;
    private String friendName;

    private  String content;


    private int msgType;

    private User user;

    private List<User> users;

    //状态位，表示消息是否读过
    private int isRead=0;

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    //接下来定义状态位
    //1代表成功，0代表失败



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
