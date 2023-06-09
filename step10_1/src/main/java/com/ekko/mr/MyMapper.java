package com.ekko.mr;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @Author YLL
 * @Date 2023/6/9 15:36
 * @PackageName:com.ekko.mr
 * @ClassName: MyMapper
 * @Description: TODO
 * @Version 1.0
 */
public class MyMapper extends Mapper<Object,Text, LongWritable, NullWritable> {
    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        context.write(new LongWritable(Long.parseLong(key.toString())), NullWritable.get());

    }


}
