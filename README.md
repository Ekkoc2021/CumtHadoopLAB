# CumtHadoopLAB



实验一docker搭建hadoop的脚本，尽可能的简化步骤，有些小伙伴可能因为桌面还原导致各个别实验之间出现了环境隔离，需要一直重复做实验一，下面这个几个脚本可能对你重新做实验一有帮助。



如果你的环境是完全新的环境，你需要加载老师提供的镜像，如果不是且你需要重做实验一，你只要把原来master，slave容器删除即可，按照你的需要执行下面两行命令。

```bash
docker load < /cg/images/hadoop_node.tar.gz # 载入的镜像
```

```bash
docker rm -f master slave1 slave2 slave3 # 删除容器
```



## 开始


```bash
# 安装git，装过可以跳过安装这一步。
apt install -y git 
# 拉取仓库到本地
# 执行完命令后，你可以在你执行clone命令的这个目录看到一个新的目录CumtHadoopLAB
git clone https://github.com/Ekkoc2021/CumtHadoopLAB.git
```
可以在CumtHadoopLAB目录下看到当前文档，直接在实验的环境里面copy，效率会高一点：
```bash
# 用一个新窗口打开，CumtHadoopLAB目录下的README.md文件，按照步骤copy命令即可。
cat CumtHadoopLAB/README.md
```



```bash
# 执行第一个脚本
chmod 777 CumtHadoopLAB/env.sh
./CumtHadoopLAB/env.sh

# 第一个脚本任务完成，且自动进入了第一步创建好的容器，下面完成，执行second.sh脚本，同时免密登录自己

# 执行第二个脚本
chmod 777 /course/second.sh
./course/second.sh

# 免密登录。免密登录本来打算在在第二个脚本里面自动完成，但调试的时候没有成功！单独执行。
ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
chmod 0600 ~/.ssh/authorized_keys

# 退出容器
exit

# 执行第三个脚本
chmod 777 CumtHadoopLAB/third.sh
./CumtHadoopLAB/third.sh
```

完成！接下来就是实验一的启动Hadoop集群。

**但是还是要注意一点，**你第一次免密登录需要输入yes，这个**过程可能会导致启动失败**，建议进入master后先进行ssh登录其他三个节点。

下面是实验一的启动Hadoop集群

```bash
docker exec -it master	/bin/bash

# 这一步是使用掉首次ssh登录，输入yes，由于要输入yes，要一条一条复制的执行
ssh master jps
ssh slave1 jps
ssh slave2 jps
ssh slave3 jps

#这几个命令好像只能一条一条的执行
hdfs namenode -format

#也能写成脚本一次运行，下次重启节点只要运行下面这条命令就行
start-dfs.sh
start-yarn.sh
mr-jobhistory-daemon.sh start historyserver

# 分别查看 各个节点的java进程情况，看看是否和实验要求一致！
ssh master jps
ssh slave1 jps
ssh slave2 jps
ssh slave3 jps

```

------

------

------

后续因为某些原因导致集群环境出现**完全不可逆转**的问题，你直接执行我提供第4个的脚本，再次完成上面的启动Hadoop集群即可。

第4个脚本主要是通过第3个脚本创建的镜像，启一个新的集群。

**注意：这个脚本会自动删除原先的master，slave容器。**

```bash
# 执行第四个脚本
chmod 777 CumtHadoopLAB/restart.sh
./CumtHadoopLAB/restart.sh
```



## 脚本完成了那些事情？

最初是想用一个脚本完成所有任务，但能力有限，只能拆分成三个脚本。

提供的脚本分别做了这些事情：

- env.sh:将在容器内需要运行的脚本移动到挂载的目录,启动一个容器,进入容器 。
- second.sh:在容器内运行,解压java,hadoop的包,完成环境变量配置，同时将挂载的配置文件移动到hadoop对应的文件夹下。
- third.sh:停止容器运行,制作镜像,删除容器,通过镜像启动集群。
- restart.sh:通过第3个脚本创建的镜像，启一个新的集群，当然你也可以自己敲命令启动。



## Hbase部署脚本

还原桌面===>每个小实验都是新环境，吐了。

启动完集群后部署hbase：

```bash
docker exec -it master	/bin/bash

chmod 777 /course/hbaseIni.sh
./course/hbaseIni.sh

source ~/.bashrc
source /usr/local/hbase/conf/hbase-env.sh

# 回到实验步骤启动hbase
start-hbase.sh
hbase shell
```

