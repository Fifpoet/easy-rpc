package cn.colins.rpc.common.entiy;

import java.util.Objects;

/**
 * @Description 服务实例的元数据
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/13
 */
public class ServiceMetaData {

    private String interfaceName;

    private String beanRefName;

    private String version;

    private int weight;

    public ServiceMetaData(){

    }

    public ServiceMetaData(String interfaceName, String beanRefName, int weight, String version) {
        this.beanRefName = beanRefName;
        this.interfaceName = interfaceName;
        this.weight = weight;
        this.version = version;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceMetaData that = (ServiceMetaData) o;
        return Objects.equals(interfaceName, that.interfaceName) &&
                Objects.equals(beanRefName, that.beanRefName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interfaceName, beanRefName);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getBeanRefName() {
        return beanRefName;
    }

    public void setBeanRefName(String beanRefName) {
        this.beanRefName = beanRefName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
}
