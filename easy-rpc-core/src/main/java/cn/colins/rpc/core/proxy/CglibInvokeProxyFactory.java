package cn.colins.rpc.core.proxy;


import cn.colins.rpc.common.entiy.EasyRpcInvokeInfo;
import cn.colins.rpc.core.cluster.strategy.EasyRpcClusterStrategy;
import net.sf.cglib.proxy.Enhancer;

/**
 * @Author czl
 * @Description 代理工厂
 * @Date 2023/6/13 18:11
 * @Param
 * @return
 **/
public class CglibInvokeProxyFactory {

    /**
     * @Author colins
     * @Description  获取客户端远程调用代理对象
     * @return T
     **/
    public static <T> T getClientInvokeProxy(Class<T> interfaceClass, EasyRpcInvokeInfo invokeInfo) throws Exception {
        return (T) Enhancer.create(interfaceClass, new CglibBeanInvocationHandler(invokeInfo));
    }


    /**
     * @Author colins
     * @Description  执行器代理对象
     * @return T
     **/
    public static <T> T getExecutorInvokeProxy(Class<T> interfaceClass, EasyRpcClusterStrategy clusterStrategy) throws Exception {
        return (T) Enhancer.create(interfaceClass, new CglibExecutorInvocationHandler(clusterStrategy));
    }
}
