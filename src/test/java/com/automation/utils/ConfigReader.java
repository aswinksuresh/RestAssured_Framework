package com.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    static Properties prop;
    public  static void initConfig(){
        prop=new Properties();
        try {
            prop.load(new FileInputStream("src/test/resources/config/config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getConfig(String key){
       return prop.getProperty(key);
    }
    public static void setProperty(String key, String value){
        prop.setProperty(key, value);
    }
    public static void setObject(String key, Object pojo){
        prop.put(key,pojo);
    }
    public static Object getObject(String key){
        return prop.get(key);
    }
}
