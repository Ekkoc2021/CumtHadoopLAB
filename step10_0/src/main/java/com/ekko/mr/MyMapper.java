package com.ekko.mr;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @Author YLL
 * @Date 2023/6/9 15:36
 * @PackageName:com.ekko.mr
 * @ClassName: MyMapper
 * @Description: key是行数据 value是文件名称
 * @Version 1.0
 */
public class MyMapper extends Mapper<Object,Text, LongWritable, Text> {
    private String filename;
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //初始化就获取文件名称
        filename = ((FileSplit)context.getInputSplit()).getPath().getName().toString();
    }

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // out key 数字 value 是文件名称
        long l = Long.parseLong(value.toString());
        context.write(new LongWritable(l),new Text(filename));
    }
}
