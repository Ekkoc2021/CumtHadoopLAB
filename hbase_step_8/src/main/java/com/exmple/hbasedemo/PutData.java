package com.exmple.hbasedemo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PutData{
    public static Configuration configuration;
    public static Connection connection;
    public static Admin admin;
    public static void main(String[] args)throws IOException{
        init();
        putData("student");
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
    public static void putData(String tableName) throws IOException {
        FileReader reader = new FileReader("/data/input.dat");

        String currentDirectory = System.getProperty("user.dir");
        System.out.println("----==：" + currentDirectory);

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