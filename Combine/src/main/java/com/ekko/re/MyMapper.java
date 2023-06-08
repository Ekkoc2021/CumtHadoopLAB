package com.ekko.re;

import java.io.*;
import java.util.StringTokenizer;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class MyMapper extends Mapper<Object,Text,Text,Text>{
    
    //1:声明输出的key，value变量和文件路径变量
    private Text keyInfo = new Text();
    private Text valueInfo = new Text();
    private FileSplit split;
    
    //2:重写map函数
    public void map(Object key,Text value,Context context) 
            throws IOException,InterruptedException{
        //3:获取当前任务分割的单词所在的文件路径
        split = (FileSplit)context.getInputSplit();
        //4:声明StringTokenizer变量，用于分割单词
        StringTokenizer itr = new StringTokenizer(value.toString());
        while(itr.hasMoreTokens()){
            //5:key值设定为“单词+文件名”
            keyInfo.set(itr.nextToken()+" "+split.getPath().getName().toString());
            //6:value值设定为字符“1”（不是数字1）
            valueInfo.set("1"); 
            context.write(keyInfo,valueInfo);
        }
    }
}