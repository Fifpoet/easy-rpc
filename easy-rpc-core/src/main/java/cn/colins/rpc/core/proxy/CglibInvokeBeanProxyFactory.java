package cn.colins.rpc.core.proxy;


import net.sf.cglib.proxy.Enhancer;

/**
 * @Author czl
 * @Description 代理工厂
 * @Date 2023/6/13 18:11
 * @Param
 * @return
 **/
public class CglibInvokeBeanProxyFactory {

    public static <T> T getProxy(Class<T> interfaceClass,String serviceId) throws Exception {
        return (T)Enhancer.create(interfaceClass, new CglibInvocationHandler(serviceId));
    }

}
