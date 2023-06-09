package com.ekko.mr;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @Author YLL
 * @Date 2023/6/9 15:38
 * @PackageName:com.ekko.mr
 * @ClassName: MyReducer
 * @Description: TODO
 * @Version 1.0
 */
public class MyReducer extends Reducer<LongWritable, Text,LongWritable, NullWritable> {
    @Override
    protected void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        context.write(key,NullWritable.get());
    }
}
