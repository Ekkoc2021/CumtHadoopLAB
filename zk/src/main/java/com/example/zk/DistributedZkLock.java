package com.example.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author YLL
 * @Date 2023/6/12 21:08
 * @PackageName:com.example.zk
 * @ClassName: DistributedLock
 * @Description: zookeeper 实现分布式锁
 * 实现加锁,和释放锁的功能
 * 加锁: 在zk中locks节点下创建顺序编号节点lock-xxx节点,遍历locks下所有节点,判断当前节点是最小节点则然后获得锁,监听比自己小1的节点
 * 释放锁: 在zk中删除创建的节点
 * @Version 1.0
 */
public class DistributedZkLock {

    private String minimumValuePath;
    private ZooKeeper zk; // 需要提供
    private CountDownLatch countDownLatch=new CountDownLatch(1);
    //
    //如果进程的zk对象都是同一个,如果进程某个获取锁线程挂了,他的创建的锁无法被释放.
    // 如何确保线程的锁一定被释放? 如果每个zklock对象都不是同的zk对象,首次连接过慢?
    private String lockPath;
    public Boolean zklock() {
        try {
            // 创建节点
            lockPath = zk.create("/locks/lock-", "lock".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.CONTAINER);
            // 获取所有节点,无需关注在我们之后创建的节点
            List<String> children = zk.getChildren("/locks", false);
            int i = children.indexOf(lockPath);
            if (i == 0) {
                //可以获得锁
                return true;
            }
            //不用考虑-1的情况,-1表明列表中不存在节点信息,表明创建失败,创建失败抛出异常
            // 获取的所有节点中,有比当前编号更小的节点,创建监听
            String s = children.get(i - 1);

            //zk与客户端建立连接后,会在后台开启一个子线程,与客户端进行通信,并在获得服务端对应消息后进行调用监听函数.
            // 为了节省资源我们在 使用CountDownLatch这个同步工具类来完成线程等待和唤醒,在本线程进行等待,在zk的子线程调用监听函数时进行唤醒
            zk.getData("/locks/" + s,
                    new Watcher() {
                        @Override
                        public void process(WatchedEvent watchedEvent) {
                            if ((watchedEvent.getType() ==
                                    Event.EventType.NodeDeleted &&
                                    watchedEvent.getPath().equals(minimumValuePath))) {
                                // 监听到当前删除路径
                                countDownLatch.countDown(); //减少倒计时
                            }
                        }
                    }, new Stat());
            // 创建监听,如果过程中节点被删除则报错,说明可以获得锁

            // 暂停该线程,最长等待时间为5s,5s后主动释放锁,返回false,表明获取锁失败
            boolean await = countDownLatch.await(5l, TimeUnit.SECONDS);
            if (await){
                //获得锁成功
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //超时释放锁
        unzklock();
        return false;

    }

    public void unzklock() {
        try {
            zk.delete(lockPath, -1);
            zk.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
