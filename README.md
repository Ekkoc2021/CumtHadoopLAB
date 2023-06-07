# CumtHadoopLAB

实验一docker搭建hadoop的脚本，已经尽可能简化命令输入了，欢迎和我交流。



如果你的环境是完全新的环境，你需要加载老师提供的镜像，如果不是而且你需要重做实验一，你只要把原来master，slave容器删除即可，按照你的需要执行下面两行命令。

```bash
docker load < /cg/images/hadoop_node.tar.gz # 载入的镜像
```

```bash
docker rm -f master slave1 slave2 slave3 # 删除容器
```



如果没有安装git，先安装git

```bash
apt install -y git 
```



## 开始

```bash
# 拉取仓库到本地
# 执行完命令后，你可以在你执行clone命令的这个目录看到一个新的目录CumtHadoopLAB
git clone https://github.com/Ekkoc2021/CumtHadoopLAB.git

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
chmod 777 /CumtHadoopLAB/third.sh
./CumtHadoopLAB/third.sh
```

完成！接下来就可以从实验一的启动Hadoop集群接着做就行。











后续因为某些原因导致集群环境出现**完全不可逆转**的问题，你直接执行我提供第4个的脚本即可。

第4个脚本主要是通过第3个脚本创建的镜像，启一个新的集群。

**注意：这个脚本会自动删除原先的master，slave容器。**

```bash
# 执行第四个脚本
chmod 777 /CumtHadoopLAB/restart.sh
./CumtHadoopLAB/restart.sh
```



## 脚本完成了那些事情？

最初是想用一个脚本完成所有任务，但能力有限，只能拆分成三个脚本，他们分别做了这些事情：

- env.sh:将在容器内需要运行的脚本移动到挂载的目录,启动一个容器,进入容器 。
- second.sh:在容器内运行,解压java,hadoop的包,完成环境变量配置。
- third.sh:停止容器运行,制作镜像,删除容器,通过镜像启动集群。
- restart.sh:通过第3个脚本创建的镜像，启一个新的集群，当然你也可以自己敲命令启动。



