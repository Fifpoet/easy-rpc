package cn.colins.rpc.core.cluster.route;

import cn.colins.rpc.common.entiy.EasyRpcInvokeInfo;
import cn.colins.rpc.common.exception.EasyRpcRunException;
import cn.colins.rpc.core.cluster.balance.EasyRpcLoadBalance;
import cn.colins.rpc.core.domain.ServiceInstance;
import cn.colins.rpc.core.domain.ServiceMetaData;
import cn.hutool.core.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/20
 */
public abstract class EasyRpcAbstractRouter implements EasyRpcRouter {

    @Override
    public List<ServiceInstance> route(List<ServiceInstance> serviceInstances, EasyRpcInvokeInfo rpcInvokeInfo) {
        List<ServiceInstance> routeInstance = new ArrayList<>(serviceInstances.size());
        for (ServiceInstance serviceInstance : serviceInstances) {
            List<ServiceMetaData> collect = serviceInstance.getMetaDataSet().stream()
                    .filter(item -> item.getInterfaceName().equals(rpcInvokeInfo.getInterfaceName())).collect(Collectors.toList());
            if (CollectionUtil.isEmpty(collect)) {
                continue;
            }
            if (collect.size() == 1) {
                routeInstance.add(serviceInstance);
                continue;
            }
            // 再用bean name找
            List<ServiceMetaData> collect1 = collect.stream()
                    .filter(item -> item.getBeanRefName().equals(rpcInvokeInfo.getBeanRef())).collect(Collectors.toList());
            if (CollectionUtil.isEmpty(collect1)) {
                continue;
            }
            routeInstance.add(serviceInstance);
        }
        return doRoute(routeInstance,rpcInvokeInfo);
    }

    protected abstract List<ServiceInstance> doRoute(List<ServiceInstance> routeInstance, EasyRpcInvokeInfo rpcInvokeInfo);

}
