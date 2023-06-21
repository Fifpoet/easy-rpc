package cn.colins.rpc.core.cluster.route.impl;

import cn.colins.rpc.common.entiy.EasyRpcInvokeInfo;
import cn.colins.rpc.core.cluster.route.EasyRpcAbstractRouter;
import cn.colins.rpc.common.entiy.ServiceInstance;
import cn.colins.rpc.common.entiy.ServiceMetaData;
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
public class EasyRpcVersionRouter extends EasyRpcAbstractRouter {

    @Override
    protected List<ServiceInstance> doRoute(List<ServiceInstance> routeInstance, EasyRpcInvokeInfo rpcInvokeInfo) {
        List<ServiceInstance> versionRouterInstance=new ArrayList<>(routeInstance.size());
        for (ServiceInstance serviceInstance:routeInstance){
            List<ServiceMetaData> collect = serviceInstance.getMetaDataSet().stream()
                    .filter(item -> item.getVersion().equals(rpcInvokeInfo.getVersion())).collect(Collectors.toList());
            if(CollectionUtil.isNotEmpty(collect)){
                versionRouterInstance.add(serviceInstance);
            }
        }
        return versionRouterInstance;
    }
}
