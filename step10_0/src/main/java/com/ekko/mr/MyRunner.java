package com.ekko.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @Author YLL
 * @Date 2023/6/9 15:10
 * @PackageName:com.ekko.mr
 * @ClassName: MyRunner
 * @Description: 完成交集文件的输出
 * 第一点要排序
 * 第二点求交集并集
 *  交集:以数字为key(第一步完成),value为文件名称:combine去重或者reduce去重
 *  并集:去重后,reduce输出所有结果就行
 *  outputFormat似乎能够解决输出两个文件,但..
 *
 * @Version 1.0
 */
public class MyRunner {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration=new Configuration();
        Job job=Job.getInstance(configuration);

        job.setJarByClass(MyRunner.class);
        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);

        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(NullWritable.class);

        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job,new Path(args[0]));
//        FileInputFormat.setInputPaths(job,new Path("E:\\workspace\\hadoop\\twofile"));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));
//        FileOutputFormat.setOutputPath(job,new Path("E:\\workspace\\hadoop\\twofile\\output"));

        job.waitForCompletion(true);

    }
}
