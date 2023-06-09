package com.ekko.mr;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @Author YLL
 * @Date 2023/6/8 23:57
 * @PackageName:java.com.ekko.mr
 * @ClassName: MyRunner
 * @Description: TODO
 * @Version 1.0
 */
public class MyRunner {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration=new Configuration();
        Job job= Job.getInstance(configuration);

        job.setJarByClass(MyRunner.class);

        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReduce.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);


        FileInputFormat.setInputPaths(job,new Path(args[0]));
//        FileInputFormat.setInputPaths(job,new Path("E:\\workspace\\hadoop\\PhoneData"));

        FileOutputFormat.setOutputPath(job,new Path(args[1]));
//        FileOutputFormat.setOutputPath(job,new Path("E:\\workspace\\hadoop\\PhoneData\\output"));


        job.waitForCompletion(true);
    }
}
