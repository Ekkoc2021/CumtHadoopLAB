package com.ekko.mr;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @Author YLL
 * @Date 2023/6/9 21:05
 * @PackageName:com.ekko.mr
 * @ClassName: MyMapper
 * @Description: TODO
 * @Version 1.0
 */
public class MyMapper extends Mapper<Object,Text,Text,Text> {
    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // key 是单个人 value是人脉
        // A-B 记为 A +B B -A
        // 就可以得到人脉的集合,reduce只要简单的将:-的作为key +的作为value输出即可
        // C-A-B  C-D-B 这种情况,去重?...
        String A = value.toString().split("\\s+")[0];
        String B = value.toString().split("\\s+")[1];
        //2:产生正序与逆序的key-value同时压入context
        context.write(new Text(A), new Text("+" + B));
        context.write(new Text(B), new Text("-" + A));
//        System.out.println("------------------");
    }
}
