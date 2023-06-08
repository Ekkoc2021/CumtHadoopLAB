package com.ekko.mr;

import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class MyCombiner extends Reducer<Text,Text,Text,Text>{
    
    //1:声明输出的value变量
    private Text info = new Text();
    
    public void reduce(Text key,Iterable<Text>values,Context context) 
            throws IOException, InterruptedException{
        //2:声明计数变量sum并初始化为0，统计一个文件中每一个单词的出现次数
        int sum = 0;
        for(Text value:values){
            sum += Integer.parseInt(value.toString());
        }
        //3:将从Map函数传过来的key值分割成单词和文件名保存在str中
        String record = key.toString();
        String[] str = record.split(" ");
        //4:新的key值设为单词，value设为“文件名+词频”
        key.set(str[0]);
        info.set(str[1]+":"+sum);    
        context.write(key,info);
    }
}