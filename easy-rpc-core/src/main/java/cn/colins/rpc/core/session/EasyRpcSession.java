package cn.colins.rpc.core.session;

import cn.colins.rpc.core.domain.ServiceInstance;
import cn.colins.rpc.remote.entiy.EasyRpcRequest;
import cn.colins.rpc.remote.entiy.EasyRpcResponse;

/**
 * @program: easy-rpc
 * @description:
 * @author: colins
 * @create: 2023-06-13 22:42
 **/
public interface EasyRpcSession {

    EasyRpcResponse exec();

    EasyRpcRequest getRpcRequest();

    ServiceInstance getServiceInstance();
}
