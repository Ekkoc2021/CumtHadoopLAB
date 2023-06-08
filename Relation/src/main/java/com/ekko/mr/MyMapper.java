package com.ekko.mr;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;

/***
 * 
 * 1：4个泛型中，前两个是指定mapper输入数据的类型，KEYIN是输入的key的类型，VALUEIN是输入的value的值
 *       KEYOUT是输入的key的类型，VALUEOUT是输入的value的值 
 * 2：map和reduce的数据输入和输出都是以key-value的形式封装的。
 * 3：默认情况下，框架传递给我们的mapper的输入数据中，key是要处理的文本中一行的起始偏移量，这一行的内容作为value
 * 4：key-value数据是在网络中进行传递，节点和节点之间互相传递，在网络之间传输就需要序列化，但是jdk自己的序列化很冗余
 *    所以使用hadoop自己封装的数据类型，而不要使用jdk自己封装的数据类型；
 *    Long--->LongWritable
 *    String--->Text    
 */

public class MyMapper extends Mapper<LongWritable, Text, Text, Text> {
    //重写map这个方法
    //mapreduce框架每读一行数据就调用一次该方法
    public void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {
        //具体业务逻辑就写在这个方法体中，而且我们业务要处理的数据已经被框架传递进来，在方法的参数中key-value
        //key是这一行数据的起始偏移量，value是这一行的文本内容
        
        //1:切分名字，用空格隔开，前面的是孩子，后面的是父母
        String child = value.toString().split("\\s+")[0];
        String parent = value.toString().split("\\s+")[1];
        //2:产生正序与逆序的key-value同时压入context
        context.write(new Text(child), new Text("-" + parent));
        context.write(new Text(parent), new Text("+" + child));
    }
}