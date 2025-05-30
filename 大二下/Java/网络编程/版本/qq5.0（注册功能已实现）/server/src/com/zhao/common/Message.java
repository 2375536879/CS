package com.zhao.common;

import com.zhao.po.User;

import java.io.Serializable;

public class Message implements Serializable {//可序列化接口

    private int msgType;

    private User user;

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
