package com.exmple.hbasedemo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import java.io.IOException; 
public class TableExist{
    public static Configuration configuration;
    public static Connection connection;
    public static Admin admin; //调用入口
    public static void main(String[] args)throws IOException{
        init();
        if(args.length != 1) {
            System.out.println("args not valid");
            System.exit(1);
        }
        String tableName = args[0];
        TableName tn = TableName.valueOf(tableName);
        if(admin.tableExists(tn)) {
            System.out.println("table " + tableName + " does exist!");
        } else {
            System.out.println("table " + tableName + " don't exist!");
        }
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
        System.out.println("Connect to HBase successfully!");
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