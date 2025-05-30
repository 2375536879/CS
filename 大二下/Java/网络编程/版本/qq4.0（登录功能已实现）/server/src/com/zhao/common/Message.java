package com.zhao.common;

import java.io.Serializable;

public class Message implements Serializable {//可序列化接口

    private int msgType;

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    //接下来定义状态位
    //1代表成功，0代表失败

}
