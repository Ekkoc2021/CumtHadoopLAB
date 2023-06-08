package com.ekko.re;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MyReducer extends Reducer<Text,Text,Text,Text>{
    private Text result = new Text();
    public void reduce(Text key,Iterable<Text>values,Context context) throws 

IOException, InterruptedException{
        String value =new String();
        for(Text value1:values){
            value += value1.toString()+" ; ";
        }
        result.set(value);
        context.write(key,result);
    }
}