 #!/bin/bash
 
"yes" | cp CumtHadoopLAB/second.sh /headless/course

"yes" | cp CumtHadoopLAB/core-site.xml /headless/course
"yes" | cp CumtHadoopLAB/hdfs-site.xml /headless/course
"yes" | cp CumtHadoopLAB/mapred-site.xml /headless/course

"yes" | cp CumtHadoopLAB/yarn-site.xml /headless/course

"yes" | cp CumtHadoopLAB/slaves /headless/course

"yes" | cp CumtHadoopLAB/hbase/hbaseIni.sh /headless/cours
"yes" | cp CumtHadoopLAB/hbase/hbase-site.xml /headless/cours

docker run --name master01 --privileged -itd -v /cgsrc:/cgsrc:ro -v /headless/course/:/course hadoop_node /service_start.sh

docker exec -it master01 /bin/bash
