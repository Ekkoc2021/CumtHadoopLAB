package com.ekko.mr;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @Author YLL
 * @Date 2023/6/9 15:38
 * @PackageName:com.ekko.mr
 * @ClassName: MyReducer
 * @Description: TODO
 * @Version 1.0
 */
public class MyReducer extends Reducer<LongWritable, Text,LongWritable, NullWritable> {
    @Override
    protected void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //value文件名称的集合
        int i=0; //如果i=2 则表明两个文件名称都包含
        String v;

        String filename="";
        for (Text value : values) {
            //底层共用一个value对象....
            v= value.toString();
            if(!filename.equals(v)){
                filename=v;
                i++;
            }
            if(i==2){
                //第二次不同跳出循环
                context.write(key,NullWritable.get());
                break;
            }
        }

    }
}
