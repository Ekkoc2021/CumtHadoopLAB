package com.ekko.wc;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
/***
 * 
 * @author Administrator
 * 1:reduce的四个参数，第一个key-value是map的输出作为reduce的输入,第二个key-value是输出单词和次数，所以
 *      是Text,LongWritable的格式；
 */
public class WcReduce extends Reducer<Text, LongWritable, Text, LongWritable>{
    //继承Reducer之后重写reduce方法
    //第一个参数是key，第二个参数是集合。
    //框架在map处理完成之后，将所有key-value对缓存起来，进行分组，然后传递一个组<key,valus{}>，调用一次reduce方法
    //<hello,{1,1,1,1,1,1.....}>
    @Override
    protected void reduce(Text key, Iterable<LongWritable> values,Context context) 
            throws IOException, InterruptedException {
        //将values进行累加操作，进行计数
        long count = 0;
        //遍历value的list，进行累加求和
        for(LongWritable value : values){
            
            count += value.get();
        }
        
        //输出这一个单词的统计结果
        //输出放到hdfs的某一个目录上面，输入也是在hdfs的某一个目录
        context.write(key, new LongWritable(count));
    }
}