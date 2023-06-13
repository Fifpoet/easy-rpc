package cn.colins.rpc.core.domain;

import java.util.HashSet;
import java.util.Objects;

/**
 * @Description 服务实例的元数据
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/13
 */
public class ServiceMetaData {

    Class<?> interfaceClass;

    private String beanRefName;

    public ServiceMetaData(Class<?> interfaceClass, String beanRefName) {
        this.beanRefName = beanRefName;
        this.interfaceClass = interfaceClass;
    }

    public String getBeanRef() {
        return beanRefName;
    }


    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceMetaData that = (ServiceMetaData) o;
        return Objects.equals(interfaceClass, that.interfaceClass) &&
                Objects.equals(beanRefName, that.beanRefName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interfaceClass, beanRefName);
    }
}
