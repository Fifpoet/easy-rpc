package cn.colins.rpc.core.proxy;

import cn.colins.rpc.common.entiy.EasyRpcInvokeInfo;
import cn.colins.rpc.common.exception.EasyRpcException;
import cn.colins.rpc.core.cache.EasyRpcInstanceCache;
import cn.colins.rpc.core.domain.ServiceInstance;

import cn.colins.rpc.core.session.EasyRpcSession;
import cn.colins.rpc.core.session.EasyRpcSessionFactory;
import cn.colins.rpc.common.entiy.EasyRpcRequest;
import cn.hutool.core.collection.CollectionUtil;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

/**
 * @Description 代理处理类
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/13
 */
public class CglibInvocationHandler implements InvocationHandler {


    private final EasyRpcInvokeInfo invokeInfo;

    private final String interfaces;

    public CglibInvocationHandler(EasyRpcInvokeInfo invokeInfo, String interfaces) {
        this.invokeInfo = invokeInfo;
        this.interfaces = interfaces;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        List<ServiceInstance> serviceInstanceList = EasyRpcInstanceCache.getServiceInstanceList(invokeInfo.getServiceId());
        if (CollectionUtil.isEmpty(serviceInstanceList)) {
            throw new EasyRpcException(String.format("[ %s ] No corresponding service found ", invokeInfo.getServiceId()));
        }

        // 构建请求参数
        EasyRpcRequest easyRpcRequest = new EasyRpcRequest(UUID.randomUUID().toString(), invokeInfo.getBeanRef(), interfaces, method.getName(), method.getParameterTypes(), objects);
        // 获取会话
        EasyRpcSession easyRpcSession = EasyRpcSessionFactory.getInstance().openSession(easyRpcRequest, serviceInstanceList,invokeInfo);
        // 会话执行调用
        return easyRpcSession.exec();
    }
}
