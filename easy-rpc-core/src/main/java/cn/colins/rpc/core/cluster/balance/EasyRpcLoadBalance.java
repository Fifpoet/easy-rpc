package cn.colins.rpc.core.cluster.balance;

import cn.colins.rpc.common.entiy.EasyRpcInvokeInfo;
import cn.colins.rpc.core.domain.ServiceInstance;

import java.util.List;

/**
 * @program: easy-rpc
 * @description:
 * @author: colins
 * @create: 2023-06-18 14:46
 **/
public interface EasyRpcLoadBalance {


    ServiceInstance balance(List<ServiceInstance> serviceInstances, EasyRpcInvokeInfo rpcInvokeInfo);
}