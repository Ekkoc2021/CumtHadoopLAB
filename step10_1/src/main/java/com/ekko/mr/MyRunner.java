package com.ekko.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;


/**
 * @Author YLL
 * @Date 2023/6/9 15:40
 * @PackageName:com.ekko.mr
 * @ClassName: MyRunner
 * @Description: 并集
 * @Version 1.0
 */
public class MyRunner {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration= new Configuration();
        Job job=Job.getInstance(configuration);

        job.setJarByClass(MyRunner.class);
        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);

        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(NullWritable.class);

        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job,new Path(args[0]));
//        FileInputFormat.setInputPaths(job,new Path("E:\\workspace\\hadoop\\twofile"));

        FileOutputFormat.setOutputPath(job,new Path(args[1]));
//        FileOutputFormat.setOutputPath(job,new Path("E:\\workspace\\hadoop\\twofile\\output"));

        job.waitForCompletion(true);
    }
}
