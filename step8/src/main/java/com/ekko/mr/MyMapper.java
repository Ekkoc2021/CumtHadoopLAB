package com.ekko.mr;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @Author YLL
 * @Date 2023/6/9 14:08
 * @PackageName:java.com.ekko.mr
 * @ClassName: MyMapper
 * @Description: TODO
 * @Version 1.0
 */
public class MyMapper extends Mapper<Object, Text,Text, LongWritable> {
    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        //13726230503    00    120    i02    2481    24681    200
        String string = value.toString();
        String[] split = string.split("\\s+");

        long l = Long.parseLong(split[5])+Long.parseLong(split[4]);
        context.write(new Text(split[0]),new LongWritable(l));
    }
}
