package com.exmple.hbasedemo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import java.io.IOException; 
public class DeleteTable{
    public static Configuration configuration;
    public static Connection connection;
    public static Admin admin;
    public static void main(String[] args)throws IOException{
        if(args.length != 1) {
            System.out.println("args not valid!");
            System.exit(1);
        }
        init();
        String tableName = args[0];
        TableName tn = TableName.valueOf(tableName);
        if (admin.tableExists(tn)) {
            admin.disableTable(tn);
            admin.deleteTable(tn);
            System.out.println("table " + tableName + " is deleted successfully!");
        } else {
            System.out.println("Table " + tableName + " doesn't exist!");
        }
        close();
    } 
    //建立连接
    public static void init(){
    //初始化Configuration 对象
        configuration  = HBaseConfiguration.create();
        try{
        //根据 Configuration对象初始化Connection 对象
            connection = ConnectionFactory.createConnection(configuration);
            //获取 Admin 对象实例
            admin = connection.getAdmin();
        }catch (IOException e){
            e.printStackTrace();
        }
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