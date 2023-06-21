package cn.colins.rpc.core.cluster;

import cn.colins.rpc.common.entiy.EasyRpcInvokeInfo;
import cn.colins.rpc.common.exception.EasyRpcRunException;
import cn.colins.rpc.common.extension.EasyRpcExtensionLoader;
import cn.colins.rpc.core.cluster.balance.EasyRpcLoadBalance;
import cn.colins.rpc.core.cluster.route.EasyRpcRouter;
import cn.colins.rpc.common.entiy.ServiceInstance;
import cn.hutool.core.collection.CollectionUtil;

import java.util.List;

/**
 * @Description 集群相关策略获取执行
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/20
 */
public class EasyRpcClusterFactory {

    /**
     * @Author czl
     * @Description SPI机制选择一个路由策略，并执行
     * @Date 2023/6/20 15:20
     * @Param [serviceInstances, invokeInfo]
     * @return java.util.List<cn.colins.rpc.core.domain.ServiceInstance>
     **/
    public static List<ServiceInstance> getRouterStrategy(List<ServiceInstance> serviceInstances, EasyRpcInvokeInfo invokeInfo){
        EasyRpcExtensionLoader<EasyRpcRouter> extensionLoader = EasyRpcExtensionLoader.getExtensionLoader(EasyRpcRouter.class);
        EasyRpcRouter extensionByAlias = extensionLoader.getExtensionByAlias(invokeInfo.getRouter());
        List<ServiceInstance> route = extensionByAlias.route(serviceInstances, invokeInfo);
        if(CollectionUtil.isEmpty(route)){
            throw new EasyRpcRunException(" No instance matching the route was found ");
        }
        return route;
    }

    /**
     * @Author czl
     * @Description SPI机制选择一个负载均衡策略 并执行
     * @Date 2023/6/20 15:21
     * @Param [serviceInstances, invokeInfo]
     * @return cn.colins.rpc.core.domain.ServiceInstance
     **/
    public static ServiceInstance getLoadBalanceStrategy(List<ServiceInstance> serviceInstances, EasyRpcInvokeInfo invokeInfo){
        EasyRpcExtensionLoader<EasyRpcLoadBalance> extensionLoader = EasyRpcExtensionLoader.getExtensionLoader(EasyRpcLoadBalance.class);
        EasyRpcLoadBalance extensionByAlias = extensionLoader.getExtensionByAlias(invokeInfo.getLoadBalance());
        return extensionByAlias.balance(serviceInstances, invokeInfo);
    }


}
