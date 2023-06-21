package cn.colins.rpc.core.session;

import cn.colins.rpc.common.entiy.ServiceInstance;
import cn.colins.rpc.common.entiy.EasyRpcRequest;

/**
 * @program: easy-rpc
 * @description:
 * @author: colins
 * @create: 2023-06-13 22:42
 **/
public interface EasyRpcSession {

    Object exec();

    EasyRpcRequest getRpcRequest();

    ServiceInstance getServiceInstance();
}
