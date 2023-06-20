package cn.colins.rpc.core.proxy;

import cn.colins.rpc.core.executor.EasyRpcExecutor;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

/**
 * @program: easy-rpc
 * @description:
 * @author: colins
 * @create: 2023-06-20 22:49
 **/
public class CglibClusterInvocationHandler implements InvocationHandler {

    private final EasyRpcExecutor easyRpcExecutor;


    public CglibClusterInvocationHandler(EasyRpcExecutor easyRpcExecutor) {
        this.easyRpcExecutor = easyRpcExecutor;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        try{
            return method.invoke(easyRpcExecutor, objects);
        }catch (Exception e){

        }
        return method.invoke(easyRpcExecutor, objects);
    }

}
