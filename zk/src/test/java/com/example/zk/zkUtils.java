package com.example.zk;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @Author YLL
 * @Date 2023/6/15 21:27
 * @PackageName:com.example.zk
 * @ClassName: zkUtils
 * @Description: TODO
 * @Version 1.0
 */
public class zkUtils {
    private static final String ZOOKEEPER_HOST = "47.100.220.147:2181";
    private static final int SESSION_TIMEOUT = 50000;
    // 创建一个连接到ZooKeeper的实例，并使用用户名和密码进行身份验证
    private static ZooKeeper zooKeeper;

    public static ZooKeeper getZooKeeper(){
        if (zooKeeper==null){
            try {
                zooKeeper=new ZooKeeper(ZOOKEEPER_HOST, SESSION_TIMEOUT, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return zooKeeper;
    }
}
