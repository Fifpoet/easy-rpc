package cn.colins.rpc.core.cluster.route;

import cn.colins.rpc.common.entiy.EasyRpcInvokeInfo;
import cn.colins.rpc.core.domain.ServiceInstance;

import java.util.List;

/**
 * @program: easy-rpc
 * @description:
 * @author: colins
 * @create: 2023-06-19 23:04
 **/
public interface EasyRpcRouter {

    List<ServiceInstance> route(List<ServiceInstance> serviceInstances, EasyRpcInvokeInfo rpcInvokeInfo);
}