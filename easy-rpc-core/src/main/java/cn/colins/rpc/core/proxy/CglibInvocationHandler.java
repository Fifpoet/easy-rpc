package cn.colins.rpc.core.proxy;

import cn.colins.rpc.core.cache.EasyRpcInstanceCache;
import cn.colins.rpc.core.domain.ServiceInstance;
import cn.colins.rpc.core.exception.EasyRpcException;
import cn.colins.rpc.remote.entiy.EasyRpcRequest;
import cn.hutool.core.collection.CollectionUtil;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Description 代理处理类
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/13
 */
public class CglibInvocationHandler implements InvocationHandler {


    private final String serviceId;

    public CglibInvocationHandler(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        List<ServiceInstance> serviceInstanceList = EasyRpcInstanceCache.getServiceInstanceList(serviceId);
        if (CollectionUtil.isEmpty(serviceInstanceList)) {
            throw new EasyRpcException(String.format("[%d] No corresponding service found ", serviceId));
        }

        // 构建请求参数
//        new EasyRpcRequest();
        return null;
    }
}
