package com.ekko.mr;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * @Author YLL
 * @Date 2023/6/9 14:48
 * @PackageName:com.ekko.mr
 * @ClassName: MyMapper
 * @Description: TODO
 * @Version 1.0
 */
public class MyMapper extends Mapper<Object,Text,Text,Text>{
    //1:声明输出的key，value变量和文件路径变量
    private Text keyInfo = new Text();
    private Text valueInfo = new Text();
    private FileSplit split;

    private Long lineCount=1l;
    private Long offset=0l;

    //2:重写map函数
    public void map(Object key, Text value, Mapper.Context context)
            throws IOException,InterruptedException{
        //3:获取当前任务分割的单词所在的文件路径
        split = (FileSplit)context.getInputSplit();

        // 待解决:如果同一个文件在同一个块,共用一个mapper,这种通过偏移量增加,来判断行号的方法是有效的,不在同块则失效
        if(offset<((LongWritable)key).get()){
            //说明不是同一行
            lineCount++;
            offset=((LongWritable)key).get();
        }

        //4:声明StringTokenizer变量，用于分割单词
        StringTokenizer itr = new StringTokenizer(value.toString());
        while(itr.hasMoreTokens()){
            //5:key值设定为“单词+文件名”
            keyInfo.set(itr.nextToken()+" "+split.getPath().getName().toString());
            //6:value值设定为行号
            valueInfo.set(""+lineCount);
            context.write(keyInfo,valueInfo);
        }
    }
}
