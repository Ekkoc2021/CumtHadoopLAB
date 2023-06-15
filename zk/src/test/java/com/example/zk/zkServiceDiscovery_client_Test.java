package com.example.zk;

import org.apache.zookeeper.ZooKeeper;

import java.util.List;

/**
 * @Author YLL
 * @Date 2023/6/15 21:23
 * @PackageName:com.example.zk
 * @ClassName: zkServiceDiscoveryTest
 * @Description: 测试服务注册功能
 * @Version 1.0
 */
public class zkServiceDiscovery_client_Test {

    public static void main(String[] args) {
        ZooKeeper zooKeeper = zkUtils.getZooKeeper();
        zkServiceDiscovery zkServiceDiscoveryTest = new zkServiceDiscovery(zooKeeper, "zkServiceDiscoveryTest", "client_01");
        List<String> serveiceList = zkServiceDiscoveryTest.getServeiceList("service_01","client_01");
        for (String s : serveiceList) {
            System.out.println(s);
        }
        // 获取服务列表
        while(true){

        }
    }
}
