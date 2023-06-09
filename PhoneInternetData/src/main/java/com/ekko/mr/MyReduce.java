package com.ekko.mr;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @Author YLL
 * @Date 2023/6/9 14:15
 * @PackageName:java.com.ekko.mr
 * @ClassName: MyReduce
 * @Description: TODO
 * @Version 1.0
 */
public class MyReduce extends Reducer<Text, LongWritable,Text,LongWritable> {
    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        Long sum=0l;
        for (LongWritable v:values) {
            sum=sum+v.get();
        }
        context.write(key,new LongWritable(sum));
    }
}
