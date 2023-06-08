package com.ekko.mr;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*map过程*/
public class MyMapper extends Mapper<Object,Text,Text,Text>{        
    private String id;
    private float pr;       
    private int count;
    private float average_pr;       
    public void map(Object key,Text value,Context context)
        throws IOException,InterruptedException{            
        StringTokenizer str = new StringTokenizer(value.toString());//对value进行解析
        id =str.nextToken();//id为解析的第一个词，代表当前网页
        pr = Float.parseFloat(str.nextToken());//pr为解析的第二个词，转换为float类型，代表PageRank值
    count = str.countTokens();//count为剩余词的个数，代表当前网页的出链网页个数
    average_pr = pr/count;//求出当前网页对出链网页的贡献值
    String linkids ="&";//下面是输出的两类，分别有'@'和'&'区分
    while(str.hasMoreTokens()){
        String linkid = str.nextToken();
        context.write(new Text(linkid),new Text("@"+average_pr));//输出的是<出链网页，获得的贡献值>
        linkids +=" "+ linkid;
    }       
    context.write(new Text(id), new Text(linkids));//输出的是<当前网页，所有出链网页>
    }       
}
