package com.zhao.common;

import com.zhao.po.User;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {//可序列化接口

    private int msgType;

    private User user;

    private List<User> users;



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

}
