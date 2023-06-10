package com.exmple.hbasedemo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import java.io.IOException; 

public class CreateTable{
    public static Configuration configuration;
    public static Connection connection;
    public static Admin admin;
    public static void main(String[] args)throws IOException{
        if(args.length != 1) {
            System.out.println("args not valid");
            System.exit(1);
        }
        init();

        String tableName = args[0];
        System.out.println("begin to create table " + tableName);
        createTable(tableName,new String[]{"name","age","sex","score"});
        System.out.println("table is created successfully! " + tableName);

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
    //建表
    public static void createTable(String myTableName,String[] colFamily) throws IOException { 
        TableName tableName = TableName.valueOf(myTableName); 
        HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
        for(String str:colFamily){
            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(str);
            hTableDescriptor.addFamily(hColumnDescriptor);
        }
        admin.createTable(hTableDescriptor);
    }
}