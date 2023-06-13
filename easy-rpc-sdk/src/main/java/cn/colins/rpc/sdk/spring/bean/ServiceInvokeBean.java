package cn.colins.rpc.sdk.spring.bean;

import cn.colins.rpc.core.cache.EasyRpcInstanceCache;
import cn.colins.rpc.core.domain.ServiceInstance;
import cn.colins.rpc.core.exception.EasyRpcException;
import cn.colins.rpc.core.proxy.CglibInvokeBeanProxyFactory;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @Description 工厂Bean
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/13
 */
public class ServiceInvokeBean<T> implements FactoryBean {

    private final String serviceId;

    private final Class<T> interfaceClass;

    public ServiceInvokeBean(String serviceId, Class<T> interfaceClass) {
        this.serviceId = serviceId;
        this.interfaceClass = interfaceClass;
    }

    @Override
    public Object getObject() throws Exception {
        return CglibInvokeBeanProxyFactory.getProxy(interfaceClass,serviceId);
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }
}
