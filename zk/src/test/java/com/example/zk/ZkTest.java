package com.example.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
    private static final int SESSION_TIMEOUT = 50000;

    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345";

    int count = 20;
    CountDownLatch countDownLatch = new CountDownLatch(30);

    /**
     * @author YLL
     * @date 2023/6/13 9:27
     * @Description: 不加锁的情况下,30人购买20张票,每个人都购买到了,最后还有余票,出现多人同时买到同一张票
     */
    @Test
    public void lockTest() throws InterruptedException {
        for (int i = 0; i < 30; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (count > 0) {
                        count--;
                        System.out.println(Thread.currentThread().getName() + ",购买到票!当前票数:" + count);
                    }else {
                        System.out.println(Thread.currentThread().getName() + ",未购买到票!当前票数:" + count);
                    }
                    countDownLatch.countDown();

                }
            }).start();
        }
        countDownLatch.await();
    }


    /**
     * @author YLL
     * @date 2023/6/13 9:29
     * @Description: 分布式锁解决,同时购票问题!
     * 30个线程,一个线程模拟一个分布式节点
     */
    @Test
    public void zkDistributedLockTest() {
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


            for (int i = 0; i < 30; i++) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DistributedZkLock distributedZkLock = new DistributedZkLock(zooKeeper);
                        if (distributedZkLock.zklock()) {
                            System.out.println("加锁成功!");
                            if (count > 0) {
                                count = count - 1;
                                System.out.println(Thread.currentThread().getName() + ",购买到票!购买后剩余票数:" + count);
                            } else {
                                System.out.println(Thread.currentThread().getName() + ",未购买到票!当前余票:" + count);
                            }

                            distributedZkLock.unzklock();
                            countDownLatch.countDown();

                        }
                    }
                }).start();
            }
            countDownLatch.await(200l, TimeUnit.SECONDS);
            // 关闭ZooKeeper连接
            zooKeeper.close();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }


}
