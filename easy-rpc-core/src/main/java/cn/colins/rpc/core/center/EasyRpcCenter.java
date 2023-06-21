package cn.colins.rpc.core.center;

import cn.colins.rpc.common.entiy.ServiceInstance;

import java.util.List;

public interface EasyRpcCenter {

    /** 注册 */
    void registerInstance(ServiceInstance serviceInstance);

    /** 销毁 */
    void deregisterInstance(ServiceInstance serviceInstance);

    /** 获取 */
    List<ServiceInstance> getAllInstances(String serviceId);

    /** 订阅 */
    void subscribeInstance(String serviceId);
}
