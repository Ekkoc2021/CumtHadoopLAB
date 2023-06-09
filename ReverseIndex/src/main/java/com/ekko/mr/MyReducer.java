package com.ekko.mr;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @Author YLL
 * @Date 2023/6/9 14:49
 * @PackageName:com.ekko.mr
 * @ClassName: MyReduce
 * @Description: TODO
 * @Version 1.0
 */
public class MyReducer extends Reducer<Text, Text, Text, Text> {
    private Text result = new Text();

    // reduce阶段主要完成不同文件(块)的合并
    public void reduce(Text key, Iterable<Text> values, Context context) throws
            IOException, InterruptedException {
        String value = new String();
        for (Text value1 : values) {
            value += value1.toString() + " ; ";
        }
        result.set(value);
        context.write(key, result);
    }
}
