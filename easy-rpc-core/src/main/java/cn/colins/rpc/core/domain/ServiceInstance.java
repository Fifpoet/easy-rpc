package cn.colins.rpc.core.domain;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ConcurrentHashSet;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Set;

/**
 * @Description 服务实例 目前简单点就IP、端口
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/13
 */
public class ServiceInstance {

    private String ip;
    private int port;

    private Set<ServiceMetaData> metaDataSet;

    public ServiceInstance(int port) {
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.port = port;
        this.metaDataSet = new ConcurrentHashSet<>(32);
    }

    public ServiceInstance(int port, Set<ServiceMetaData> metaDataSet) {
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.port = port;
        this.metaDataSet = CollectionUtil.isEmpty(metaDataSet) ? new ConcurrentHashSet<>(32) : metaDataSet;
    }

    public ServiceInstance(String ip, int port, Set<ServiceMetaData> metaDataSet) {
        this.ip = ip;
        this.port = port;
        this.metaDataSet = CollectionUtil.isEmpty(metaDataSet) ? new ConcurrentHashSet<>(32) : metaDataSet;
    }

    public ServiceInstance(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.metaDataSet = new ConcurrentHashSet<>(32);
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public Set<ServiceMetaData> getMetaDataSet() {
        return metaDataSet;
    }
}
