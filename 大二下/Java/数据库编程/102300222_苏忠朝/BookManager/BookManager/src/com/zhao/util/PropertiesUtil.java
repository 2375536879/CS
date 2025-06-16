package com.zhao.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    //单例模式
    private static PropertiesUtil propertiesUtil=null;

    //读取配置文件使用
    private Properties properties;

    private PropertiesUtil(){
        properties = new Properties();
        //Java反射
        InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("com/zhao/config.properties");


        //将配置加载到properties里面
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static PropertiesUtil getInstance(){
        if(propertiesUtil==null){
            propertiesUtil=new PropertiesUtil();
        }
        return propertiesUtil;
    }


    public String getValue(String key){
        return properties.getProperty(key);
    }

}
