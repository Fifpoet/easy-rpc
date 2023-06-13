package cn.colins.rpc.core.config;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/13
 */
public class EasyRpcCenterConfig {

    /** 注册中心地址 */
    private String address;
    /** 注册中心端口 */
    private int port;
    /** 注册中心命名空间 */
    private String namespace;
    /** 注册中心分组 */
    private String group;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
