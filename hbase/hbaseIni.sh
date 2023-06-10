#!/bin/bash

cp /cgsrc/hbase-1.1.5-bin.tar.gz /usr/local/
cd /usr/local
tar -zxvf hbase-1.1.5-bin.tar.gz
rm -rf hbase-1.1.5-bin.tar.gz
mv hbase-1.1.5 hbase

echo -e "export PATH=\$PATH:/usr/local/hbase/bin" >> ~/.bashrc

source ~/.bashrc

hbase version

en="
export JAVA_CLASSPATH=.:\$JAVA_HOME/lib/dt.jar:\$JAVA_HOME/lib/tools.jar\n
export HBASE_MANAGES_ZK=true\n
export HADOOP_CLASSPATH="\$HADOOP_CLASSPATH:/usr/local/hbase/lib/*"\n
"
echo -e $en >> /usr/local/hbase/conf/hbase-env.sh

source /usr/local/hbase/conf/hbase-env.sh

"yes" | cp /course/hbase-site.xml /usr/local/hbase/conf/hbase-site.xml

echo "export HADOOP_CLASSPATH=/usr/local/hbase/lib/*" >> /usr/local/hadoop/etc/hadoop/hadoop-env.sh
