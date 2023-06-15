package com.example.zk;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author YLL
 * @Date 2023/6/15 17:21
 * @PackageName:com.example.zk
 * @ClassName: zkServiceDiscovery
 * @Description: zk 实现服务注册中心
 * 客户端 能够从zk上拉取对应服务列表
 * 服务端 能够将自己的ip注册到zk上
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class zkServiceDiscovery {
    private ZooKeeper zk;
    private String appName;
    private String serviceName;
    private static boolean isCreateAppPath = false;
    private static boolean isCreateServicePath = false;


    /**
     * @author YLL
     * @date 2023/6/15 18:35
     * @Description: 服务端注册接口
     * @param addr ip:port
     * @return boolean
     */
    public boolean registerService(String addr){
        return register(addr,null,false);
    }

    /**
     * @author YLL
     * @date 2023/6/15 18:36
     * @Description: 客户端注册接口
     * @param addr 客户端ip:端口
     * @param servName 注册的服务端名称
     * @return boolean 是否注册成功
     */
    public boolean registerClient(String addr, String servName) throws Exception {
        if (servName==null){
            throw new Exception("serviceName can not be null");
        }
        return register(addr,servName,true);
    }

    /**
     * @author YLL
     * @date 2023/6/15 17:27
     * @Description: 服务端和调用端都要在zk上进行注册
     * /appname/serviceName/service/serviceID
     * /appname/serviceName/client/clientId
     * @param value 服务地址,ip:port
     * @return boolean
     */
    private boolean register(String value, String servName,boolean isClient){
        // 确保appname/serviceName的创建,双重加锁
        try {
            if (!isCreateAppPath) {
                createAppPath();
            }
            if (!isCreateServicePath) {
                createServicePath();
            }
            if (isClient) {
                //如果是调用端:每次调用之前
                zk.create("/" + appName + "/" + servName + "/clients" + serviceName, value.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                return true;
            }
            zk.create("/" + appName + "/" + serviceName + "/service" + serviceName, value.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @author YLL
     * @date 2023/6/15 17:52
     * @Description: 创建appPath, 每个系统只能调用一次该方法
     */
    private synchronized void createAppPath() throws Exception {
        if (!isCreateAppPath) {
            //系统首次启动,服务尚未创建
            Stat exists = zk.exists("/" + appName, false);
            if (exists == null) {
                String s = zk.create("/" + appName, appName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.CONTAINER);
            }
        }
    }

    /**
     * @author YLL
     * @date 2023/6/15 17:52
     * @Description: 创建servicePath, 每个系统只调用一次
     */
    private synchronized void createServicePath() throws Exception {
        if (!isCreateServicePath) {
            Stat exists = zk.exists("/" + appName + "/" + serviceName, false);
            if (exists == null) {
                String s = zk.create("/" + appName + appName + "/" + serviceName, serviceName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.CONTAINER);
            }
        }
    }

    /**
     * @author YLL
     * @date 2023/6/15 21:21
     * @Description: 获取服务列表
     * @param servName 服务名称
     * @return List<String>
     */
    public List<String> getServeiceList(String servName){
        List<String> serviveList = new ArrayList<>();
        updateServiceList(servName,serviveList);
        return serviveList;
    }

    /**
     * @author YLL
     * @date 2023/6/15 21:14
     * @Description: TODO
     * @param servName
     * @param serviveList
     */
    public void updateServiceList(String servName,List<String> serviveList){

        try {
            String pa="/" + appName + "/" + servName + "/service";
            List<String> children = zk.getChildren(pa, false);
            for (String child : children) {
                String childPath = pa + "/" + child;
                // 使用 getData() 方法获取节点的值
                byte[] data = zk.getData(childPath, false, null);
                String value = new String(data);
                serviveList.add(value);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
