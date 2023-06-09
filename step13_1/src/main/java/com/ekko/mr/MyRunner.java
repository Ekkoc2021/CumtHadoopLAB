package com.ekko.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import java.io.IOException;

/**
 * @Author YLL
 * @Date 2023/6/9 22:24
 * @PackageName:com.ekko.mr
 * @ClassName: MyRunner
 * @Description: 二进制输出结果
 * @Version 1.0
 */
public class MyRunner {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration= new Configuration();
        Job job=Job.getInstance(configuration);

        job.setJarByClass(MyRunner.class);
        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);

        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(NullWritable.class);

        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(NullWritable.class);

//        配置二进制输出，将输出数据类型改为int类型 32位
        job.setOutputFormatClass(SequenceFileOutputFormat.class);


        FileInputFormat.setInputPaths(job,new Path(args[0]));
//        FileInputFormat.setInputPaths(job,new Path("E:\\workspace\\hadoop\\twofile"));

        FileOutputFormat.setOutputPath(job,new Path(args[1]));
//        FileOutputFormat.setOutputPath(job,new Path("E:\\workspace\\hadoop\\twofile\\output"));

        job.waitForCompletion(true);
    }
    public static class MyReducer extends Reducer<IntWritable, Text, IntWritable, NullWritable> {
        @Override
        protected void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            context.write(key,NullWritable.get());
        }
    }
    public static class MyMapper extends Mapper<Object,Text, IntWritable, NullWritable> {
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            context.write(new IntWritable(Integer.parseInt(key.toString())), NullWritable.get());

        }


    }

}
