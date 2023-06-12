package com.example.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;

/**
 * @Author YLL
 * @Date 2023/6/12 18:12
 * @PackageName:com.example.zk
 * @ClassName: ZkTest
 * @Description: TODO
 * @Version 1.0
 */
public class ZkTest {
    private static final String ZOOKEEPER_HOST = "47.100.220.147:2181";
    private static final int SESSION_TIMEOUT = 40000;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345";

    @Test
    public void testConnect() {
        try {
            // 创建一个Watcher对象，用于处理ZooKeeper事件
            Watcher watcher = watchedEvent -> {
                // 处理事件的逻辑
                System.out.println("Received event: " + watchedEvent);
            };

            // 创建一个连接到ZooKeeper的实例，并使用用户名和密码进行身份验证
            ZooKeeper zooKeeper = new ZooKeeper(ZOOKEEPER_HOST, SESSION_TIMEOUT, watcher);
//            zooKeeper.addAuthInfo("digest", (USERNAME + ":" + PASSWORD).getBytes());

            // 连接成功，执行自定义的操作
            System.out.println("Connected to ZooKeeper");

            // ... 在这里执行您的操作 ...
//            String s1 = zooKeeper.create("/java", "hello world".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.CONTAINER);
//            System.out.println(s1);

            byte[] data = zooKeeper.getData("/java", false, new Stat());
            String s = new String(data, "UTF-8");
            System.out.println(s);

            long l = System.currentTimeMillis();
            for (int i = 0; i < 10; i++) {
                data = zooKeeper.getData("/java", false, new Stat());
                s=new String(data, "UTF-8");
                System.out.println(s);
                Thread.sleep(2000);
            }
            long l1 = System.currentTimeMillis();
            System.out.println(l1-1);


            // 关闭ZooKeeper连接
            zooKeeper.close();
        } catch (InterruptedException | IOException | KeeperException e) {
            e.printStackTrace();
        }

    }
}
