package com.ekko.mr;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
/***
 * 
 * 1:reduce的四个参数，第一个key-value是map的输出作为reduce的输入,第二个key-value是输出祖父母和孩子的关系，所以
 *      是Text,Text的格式；
 */
public class MyReducer extends Reducer<Text, Text, Text, Text> {
    //继承Reducer之后重写reduce方法
    //第一个参数是key，第二个参数是集合。
    //框架在map处理完成之后，将所有key-value对缓存起来，进行分组，然后传递一个组<key,valus{}>，调用一次reduce方法
    
    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        //1:创建两个List保存祖父母和孩子
        ArrayList<Text> grandparent = new ArrayList<Text>();
        ArrayList<Text> grandchild = new ArrayList<Text>();
        
        //2:对各个values中的值进行处理,key的父母保存到祖父母列表中，key的子女保存在孩子孩子列表中
        for (Text t : values) {
            String s = t.toString();
            if (s.startsWith("-")) {
                grandparent.add(new Text(s.substring(1)));
            } else {
                grandchild.add(new Text(s.substring(1)));
            }
        }
        //3:再将grandparent与grandchild中的东西，一一对应输出。
        for (int i = 0; i < grandchild.size(); i++) {
            for (int j = 0; j < grandparent.size(); j++) {
                context.write(new Text(grandchild.get(i) + " "), grandparent.get(j));
            }
        }
    }
}