package com.exmple.hbasedemo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UpdateData{
    public static Configuration configuration;
    public static Connection connection;
    public static Admin admin;
    public static void main(String[] args)throws IOException{
        init();
        updateData("student");
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
        if(admin.tableExists(tableName)){
            System.out.println("Talbe is exists, going to delete the table...");
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
        }
        HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
        for(String str:colFamily){
            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(str);
            hTableDescriptor.addFamily(hColumnDescriptor);
        }
        admin.createTable(hTableDescriptor);
    }

    public static void updateData(String tableName) throws IOException {
        String currentDirectory = System.getProperty("user.dir");
        FileReader reader = new FileReader(currentDirectory+"/data/input.dat");

        BufferedReader br = new BufferedReader(reader);
        String str = null;
        Table table = connection.getTable(TableName.valueOf(tableName));
        while((str = br.readLine()) != null) {
        System.out.println("processing line:" + str);
        String[] cols = str.split(",");
        Put put = new Put(cols[0].getBytes());
        put.addColumn("Name".getBytes(), "".getBytes(), cols[1].getBytes());
        put.addColumn("Sex".getBytes(), "".getBytes(), cols[2].getBytes());
        put.addColumn("Age".getBytes(), "".getBytes(), cols[3].getBytes());
        put.addColumn("Course".getBytes(), "Math".getBytes(), cols[4].getBytes());
        put.addColumn("Course".getBytes(), "English".getBytes(), cols[5].getBytes());
        put.addColumn("Course".getBytes(), "Chinese".getBytes(), cols[6].getBytes());
        table.put(put);
        }
        table.close();
        br.close();
        reader.close();
    }
}