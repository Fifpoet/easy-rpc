package cn.colins.rpc.core.proxy;

import cn.colins.rpc.common.entiy.EasyRpcInvokeInfo;
import cn.colins.rpc.common.entiy.EasyRpcRequest;
import cn.colins.rpc.common.entiy.ServiceInstance;
import cn.colins.rpc.common.exception.EasyRpcException;
import cn.colins.rpc.core.cache.EasyRpcInstanceCache;
import cn.colins.rpc.core.cluster.strategy.EasyRpcClusterStrategy;
import cn.colins.rpc.core.executor.impl.BaseEasyRpcExecutor;
import cn.colins.rpc.core.session.EasyRpcSession;
import cn.colins.rpc.core.session.EasyRpcSessionFactory;
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
public class CglibExecutorInvocationHandler implements InvocationHandler {


    private final EasyRpcClusterStrategy easyRpcClusterStrategy;


    public CglibExecutorInvocationHandler(EasyRpcClusterStrategy easyRpcClusterStrategy) {
        this.easyRpcClusterStrategy = easyRpcClusterStrategy;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        try{
            return method.invoke(new BaseEasyRpcExecutor(), objects);
        }catch (Exception e){
            this.easyRpcClusterStrategy.errorHandler(e,(EasyRpcRequest)objects[0]);
            return this.easyRpcClusterStrategy.invoker((EasyRpcRequest)objects[0],(List<ServiceInstance>)objects[1],(EasyRpcInvokeInfo)objects[2]);
        }
    }
}
