package com.exmple.hbasedemo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;

/**
 * @Author YLL
 * @Date 2023/6/10 12:37
 * @PackageName:com.exmple.hbasedemo
 * @ClassName: Main
 * @Description: 测试链接hbase
 * @Version 1.0
 */
public class Main {
    public static Configuration configuration;
    public static Connection connection;
    public static Admin admin;
    public static void main(String[] args)throws IOException {
        init();
        close();
    }
    //建立连接
    public static void init(){
        //根据 hbase-site.xml文件初始化Configuration 对象
        configuration  = HBaseConfiguration.create();
        try{
            //根据 Configuration对象初始化Connection 对象
            connection = ConnectionFactory.createConnection(configuration);
            //获取Admin 对象实例
            admin = connection.getAdmin();
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Connect to HBase Successfully!");
    }
    //关闭连接
    public static void close(){
        try{
            if(admin != null){
                admin.close();
            }
            if(null != connection){
                connection.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
