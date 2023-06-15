package com.example.zk;

import org.apache.zookeeper.ZooKeeper;

/**
 * @Author YLL
 * @Date 2023/6/15 21:38
 * @PackageName:com.example.zk
 * @ClassName: zkServiceDiscovery_service_Test
 * @Description: 服务端测试
 * @Version 1.0
 */
public class zkServiceDiscovery_service_Test {
    public static void main(String[] args) {
        ZooKeeper zooKeeper = zkUtils.getZooKeeper();
        zkServiceDiscovery zkServiceDiscovery = new zkServiceDiscovery(zooKeeper,"zkServiceDiscoveryTest","service_01");
        zkServiceDiscovery.registerService("service1:8080");
        while (0==0){

        }
    }
}
