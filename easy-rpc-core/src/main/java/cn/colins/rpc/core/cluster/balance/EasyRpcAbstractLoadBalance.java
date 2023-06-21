package cn.colins.rpc.core.cluster.balance;

import cn.colins.rpc.common.entiy.EasyRpcInvokeInfo;
import cn.colins.rpc.common.entiy.ServiceInstance;
import cn.colins.rpc.common.entiy.ServiceMetaData;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/20
 */
public abstract class EasyRpcAbstractLoadBalance implements EasyRpcLoadBalance{
    @Override
    public ServiceInstance balance(List<ServiceInstance> serviceInstances, EasyRpcInvokeInfo rpcInvokeInfo) {

        return serviceInstances.size()==1 ? serviceInstances.get(0) : this.selectInstance(serviceInstances,rpcInvokeInfo);
    }

    protected abstract ServiceInstance selectInstance(List<ServiceInstance> serviceInstances, EasyRpcInvokeInfo rpcInvokeInfo);


    protected int getWeight(ServiceInstance serviceInstance,EasyRpcInvokeInfo rpcInvokeInfo){
        // 先用type找
        List<ServiceMetaData> collect = serviceInstance.getMetaDataSet().stream()
                .filter(item -> item.getInterfaceName().equals(rpcInvokeInfo.getInterfaceName())).collect(Collectors.toList());
        if(collect.size() == 1){
            return Math.max(collect.get(0).getWeight(),0);
        }
        // 再用bean name找
        List<ServiceMetaData> collect1 = collect.stream()
                .filter(item -> item.getBeanRefName().equals(rpcInvokeInfo.getBeanRef())).collect(Collectors.toList());

        return Math.max(collect1.get(0).getWeight(),0);
    }
}
