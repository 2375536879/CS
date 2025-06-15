package com.zhao.test;

public class Test {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver 加载成功！");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver 加载失败！");
            e.printStackTrace();
        }
    }
}
