package cn.colins.rpc.core.cluster.balance.impl;

import cn.colins.rpc.common.entiy.EasyRpcInvokeInfo;
import cn.colins.rpc.core.cluster.balance.EasyRpcAbstractLoadBalance;
import cn.colins.rpc.core.cluster.balance.EasyRpcLoadBalance;
import cn.colins.rpc.core.domain.ServiceInstance;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/20
 */
public class EasyRpcRandomLoadBalance extends EasyRpcAbstractLoadBalance {

    @Override
    protected ServiceInstance selectInstance(List<ServiceInstance> serviceInstances, EasyRpcInvokeInfo rpcInvokeInfo) {
        int length = serviceInstances.size();
        boolean sameWeight = true;
        int[] weights = new int[length];
        int totalWeight = 0;

        int offset;
        int i;
        for(offset = 0; offset < length; ++offset) {
            i = this.getWeight(serviceInstances.get(offset),rpcInvokeInfo);
            totalWeight += i;
            weights[offset] = totalWeight;
            if (sameWeight && totalWeight != i * (offset + 1)) {
                sameWeight = false;
            }
        }

        if (totalWeight > 0 && !sameWeight) {
            offset = ThreadLocalRandom.current().nextInt(totalWeight);

            for(i = 0; i < length; ++i) {
                if (offset < weights[i]) {
                    return serviceInstances.get(i);
                }
            }
        }

        return serviceInstances.get(ThreadLocalRandom.current().nextInt(length));
    }
}
