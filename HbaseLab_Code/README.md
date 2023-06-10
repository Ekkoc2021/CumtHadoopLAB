# hbase编程实验

让jar包直接在hadoop集群执行，需要在配置相关的hbase的对应库的环境变量。将hbase的安装目录的lib的变量添加到hadoop-env.sh中即可运行。

```
export HADOOP_CLASSPATH=/usr/local/hbase/lib/*
```

需要根据运行的主类设置pom文件,然后再重新打包

