package com.ekko.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @Author YLL
 * @Date 2023/6/9 21:45
 * @PackageName:com.ekko.mr
 * @ClassName: MyRunner
 * @Description: TODO
 * @Version 1.0
 */
public class MyRunner {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(MyRunner.class);
        job.setMapperClass(FriendMapper.class);
        job.setReducerClass(FriendReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
//        FileInputFormat.setInputPaths(job,new Path("E:\\workspace\\hadoop\\Lab5"));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
//        FileOutputFormat.setOutputPath(job,new Path("E:\\workspace\\hadoop\\Lab5\\output"));


        job.waitForCompletion(true);
    }

    public static class FriendMapper extends Mapper<LongWritable, Text, Text, Text> {
        private Text person = new Text();
        private Text friends = new Text();

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] tokens = value.toString().split(" ");
            String friend1 = tokens[0];
            String friend2 = tokens[1];

            // 发送键值对，以friend1为键，friend2为值
            person.set(friend1);
            friends.set(friend2);
            context.write(person, friends);

            // 发送键值对，以friend2为键，friend1为值
            person.set(friend2);
            friends.set(friend1);
            context.write(person, friends);
        }
    }

    public static  class FriendReducer extends Reducer<Text, Text, Text, Text> {
        private Text result = new Text();

        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException, IOException {
            StringBuilder sb = new StringBuilder();
            for (Text friend : values) {
                sb.append(friend.toString()).append(",");
            }
            result.set(sb.toString());
            context.write(key, result);
        }
    }
}
