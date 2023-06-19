package cn.colins.rpc.core.proxy;


import cn.colins.rpc.common.entiy.EasyRpcInvokeInfo;
import net.sf.cglib.proxy.Enhancer;

/**
 * @Author czl
 * @Description 代理工厂
 * @Date 2023/6/13 18:11
 * @Param
 * @return
 **/
public class CglibInvokeBeanProxyFactory {

    /**
     * @Author colins
     * @Description  获取客户端远程调用代理对象
     * @return T
     **/
    public static <T> T getClientInvokeProxy(Class<T> interfaceClass, EasyRpcInvokeInfo invokeInfo, String interfaces) throws Exception {
        return (T) Enhancer.create(interfaceClass, new CglibInvocationHandler(invokeInfo,interfaces));
    }

}
