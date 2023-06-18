package cn.colins.rpc.core;

import cn.colins.rpc.core.domain.ServiceInstance;

import java.util.List;

/**
 * @program: easy-rpc
 * @description:
 * @author: colins
 * @create: 2023-06-18 14:47
 **/
public class TestLoadBalance implements EasyRpcLoadBalance{
    @Override
    public ServiceInstance balance(List<ServiceInstance> serviceInstances) {
        System.out.println("1111111111111111");
        return null;
    }
}
