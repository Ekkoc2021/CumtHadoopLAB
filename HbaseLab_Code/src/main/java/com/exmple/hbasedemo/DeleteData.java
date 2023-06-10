package com.exmple.hbasedemo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import java.io.IOException;
import java.util.Scanner; 

public class DeleteData{
    public static Configuration configuration;
    public static Connection connection;
    public static Admin admin;
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
    public static void main(String[] args)throws IOException{
        init();
        Scanner in = new Scanner(System.in);
        boolean loop = true;
        while(loop) {
            System.out.println("Input 0～4 for:\n\t0. Exit the program"
                + "\n\t1. Delete a cell." + "\n\t2. Delete a cluster."
                + "\n\t3. Delete a row.");
            int input = in.nextInt();
            switch(input) {
                case 1:
                    DeleteCell("student","1606001","Course","Math");
                    System.out.println("A cell is deleted!");
                    break;
                case 2:
                    DeleteFamliy("student","1606001","Course");
                    System.out.println("A cluster is deleted!");
                    break;
                case 3:
                    DeleteRow("student","1606001");
                    System.out.println("A row is deleted!");
                break;
                case 0:
                default:
                    loop = false;
                    break;
            }
        }
        close();
    } 

    public static void DeleteCell(String tableName, String rowKey, String family,
            String qualifier) {
    try {
            Table t = connection.getTable(TableName.valueOf(tableName));
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            delete.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
            t.delete(delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void DeleteFamliy(String tableName, String rowKey, String family) {
        try {
            Table t = connection.getTable(TableName.valueOf(tableName));
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            delete.addFamily(Bytes.toBytes(family));
            t.delete(delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void DeleteRow(String tableName, String rowKey) {
         try {
             Table t = connection.getTable(TableName.valueOf(tableName));
             Delete delete = new Delete(Bytes.toBytes(rowKey));
             t.delete(delete);
         } catch (IOException e) {
             e.printStackTrace();
         }
    }
}