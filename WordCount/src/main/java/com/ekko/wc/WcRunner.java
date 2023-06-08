package com.ekko.wc;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
/***
 * 1:用来描述一个特定的作业
 *       比如,该作业使用哪个类作为逻辑处理中的map,那个作为reduce
 * 2:还可以指定该作业要处理的数据所在的路径
 *        还可以指定改作业输出的结果放到哪个路径
 * @author Administrator
 *
 */
public class WcRunner{
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //创建配置文件
        Configuration conf = new Configuration();
        //获取一个作业
        Job job = Job.getInstance(conf);
        
        //设置整个job所用的那些类在哪个jar包
        job.setJarByClass(WcRunner.class);
        
        //本job使用的mapper和reducer的类
        job.setMapperClass(WcMap.class);
        job.setReducerClass(WcReduce.class);
        
        //指定reduce的输出数据key-value类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        
        
        //指定mapper的输出数据key-value类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        
        //指定要处理的输入数据存放路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        
        //指定处理结果的输出数据存放路径
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        //将job提交给集群运行 
        job.waitForCompletion(true);
    } 
}