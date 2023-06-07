#!/bin/bash
mkdir /usr/local/java
cp /cgsrc/jdk-8u171-linux-x64.tar.gz /usr/local/java/
tar -zxvf /usr/local/java/jdk-8u171-linux-x64.tar.gz -C /usr/local/java/
rm -f /usr/local/java/jdk-8u171-linux-x64.tar.gz

cp /cgsrc/hadoop-2.7.1.tar.gz /usr/local/
tar -zxvf /usr/local/hadoop-2.7.1.tar.gz -C /usr/local/
rm -f hadoop-2.7.1.tar.gz
mv /usr/local/hadoop-2.7.1/ /usr/local/hadoop


en="

export JAVA_HOME=/usr/local/java/jdk1.8.0_171\n

export CLASSPATH=.:\${JAVA_HOME}/jre/lib/rt.jar:\${JAVA_HOME}/lib/dt.jar:\${JAVA_HOME}/lib/tools.jar\n

export PATH=\$PATH:\${JAVA_HOME}/bin\n

ulimit -n 100000\n

export PATH=\$PATH:/usr/local/hadoop/bin:/usr/local/hadoop/sbin 

"
echo -e $en >> ~/.bashrc



echo "------------"
"yes" | cp /course/mapred-site.xml /usr/local/hadoop/etc/hadoop/mapred-site.xml 
"yes" | cp /course/core-site.xml /usr/local/hadoop/etc/hadoop/core-site.xml
"yes" | cp /course/slaves /usr/local/hadoop/etc/hadoop/slaves
"yes" | cp /course/yarn-site.xml /usr/local/hadoop/etc/hadoop/yarn-site.xml 
"yes" | cp /course/hdfs-site.xml /usr/local/hadoop/etc/hadoop/hdfs-site.xml

exit 
