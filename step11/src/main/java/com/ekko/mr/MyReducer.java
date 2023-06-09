package com.ekko.mr;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @Author YLL
 * @Date 2023/6/9 21:05
 * @PackageName:com.ekko.mr
 * @ClassName: MyReducer
 * @Description: TODO
 * @Version 1.0
 */
public class MyReducer extends Reducer<Text,Text,Text,Text> {
    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        ArrayList<Text> A = new ArrayList<Text>();
        ArrayList<Text> B = new ArrayList<Text>();
        for (Text t : values) {
            String s = t.toString();
            if (s.startsWith("-")) {
                A.add(new Text(s.substring(1)));
            } else {
                B.add(new Text(s.substring(1)));
            }
        }
        //3:再将grandparent与grandchild中的东西，一一对应输出。
        for (int i = 0; i < A.size(); i++) {
            for (int j = 0; j < B.size(); j++) {
                context.write(new Text(A.get(i) + " "), B.get(j));
            }
        }
    }
}
