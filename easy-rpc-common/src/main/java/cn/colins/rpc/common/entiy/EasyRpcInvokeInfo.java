package cn.colins.rpc.common.entiy;

/**
 * @program: easy-rpc
 * @description:
 * @author: colins
 * @create: 2023-06-19 22:50
 **/
public class EasyRpcInvokeInfo {
    private String beanRef;
    private String serviceId;
    private int weight;
    private String version;
    private String loadBalance;
    private String router;


    public String getBeanRef() {
        return beanRef;
    }

    public void setBeanRef(String beanRef) {
        this.beanRef = beanRef;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(String loadBalance) {
        this.loadBalance = loadBalance;
    }

    public String getRouter() {
        return router;
    }

    public void setRouter(String router) {
        this.router = router;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
