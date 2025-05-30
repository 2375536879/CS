package com.zhao.common;

public interface MessageType {

    //interface里面是不能有变量的，只能有静态常量
    //登录成功 1 ，登录失败 0
    public static final int LOGIN=5;
    public static final int LOGIN_SUCCESS = 1;//static静态 final常量
    public static final int LOGIN_FAIL=0;

    public static final int REGISTER=4;//在发起注册
    public static final int REGISTER_SUCCESS=2;
    public static final int REGISTER_FAIL=3;

}
