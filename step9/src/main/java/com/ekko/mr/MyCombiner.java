package com.ekko.mr;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @Author YLL
 * @Date 2023/6/9 14:52
 * @PackageName:com.ekko.mr
 * @ClassName: MyCombiner
 * @Description: TODO
 * @Version 1.0
 */
public class MyCombiner extends Reducer<Text,Text,Text,Text> {

    //1:声明输出的value变量
    private Text info = new Text();

    //主要完成同文件(更准确点应该是块)行号合并:key 单词:文件名称  value 行号==>key:单词 文件:行号
    public void reduce(Text key,Iterable<Text>values,Context context)
            throws IOException, InterruptedException{
        // 取出所有行号,进行拼接

        String lines="";
        for(Text value:values){
            if("".equals(lines)) {
                lines = lines + value.toString();
                continue;
            }
            lines = lines +","+ value.toString();
        }
        //3:将从Map函数传过来的key值分割成单词和文件名保存在str中
        String record = key.toString();
        String[] str = record.split(" ");
        //4:新的key值设为单词，value设为“文件名+词频”
        key.set(str[0]);
        info.set(str[1]+":"+lines);
        context.write(key,info);
    }
}
