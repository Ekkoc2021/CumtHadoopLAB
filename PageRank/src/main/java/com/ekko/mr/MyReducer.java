package com.ekko.mr;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/*reduce过程*/
public class MyReducer extends Reducer<Text,Text,Text,Text>{     
    public void reduce(Text key,Iterable<Text> values,Context context)
            throws IOException,InterruptedException{            
        String lianjie = "";
        float pr = 0;
        /*对values中的每一个val进行分析，通过其第一个字符是'@'还是'&'进行判断
        通过这个循环，可以 求出当前网页获得的贡献值之和，也即是新的PageRank值；同时求出当前
        网页的所有出链网页 */
        for(Text val:values){
            if(val.toString().substring(0,1).equals("@")){
                pr += Float.parseFloat(val.toString().substring(1));
            }
            else if(val.toString().substring(0,1).equals("&")){
                lianjie += val.toString().substring(1);
            }
        }

        pr = 0.8f*pr + 0.2f*0.25f;//加入跳转因子，进行平滑处理           
        String result = pr+lianjie;
        context.write(key, new Text(result));           
    }
}