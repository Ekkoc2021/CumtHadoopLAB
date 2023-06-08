package com.ekko.mr;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MyRunner {
    public static void main(String[] args) 
        throws IOException, ClassNotFoundException, InterruptedException {
        //创建配置文件
        Configuration conf = new Configuration();
        
        //指定输入数据输入地址
        String pathIn = args[0];
//        String pathIn = "E:\\workspace\\hadoop\\PageRank";

        //指定输出数据存放地址
        String pathOut = args[1];
//        String pathOut = "E:\\workspace\\hadoop\\PageRank\\output";

        //加入循环迭代
        for (int i = 1; i <= 5; i++) {
            //获取一个作业
            Job job = Job.getInstance(conf);
            //设置整个job所用的那些类在哪个jar包
            job.setJarByClass(MyRunner.class);
            //本job使用的mapper、combiner和reducer的类
            job.setMapperClass(MyMapper.class);
            job.setReducerClass(MyReducer.class);
            //指定reduce的输出数据key-value类型
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            //指定mapper的输出数据key-value类型
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            //指定要处理的输入数据存放路径
            FileInputFormat.setInputPaths(job, new Path(pathIn));
            //指定处理结果的输出数据存放路径
            FileOutputFormat.setOutputPath(job, new Path(pathOut));
            //把输出地址改为下次输入地址
            pathIn = pathOut;
            pathOut = pathOut + i;
            //将job提交给集群运行 
            job.waitForCompletion(true);
    }
    } 
}