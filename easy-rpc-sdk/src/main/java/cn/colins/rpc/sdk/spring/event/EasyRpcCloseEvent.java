package cn.colins.rpc.sdk.spring.event;

import cn.colins.rpc.core.center.EasyRpcCenter;
import cn.colins.rpc.core.domain.ServiceInstance;

import cn.colins.rpc.remote.EasyRpcServer;
import cn.colins.rpc.remote.handler.EasyRpcServerHandlerInit;
import cn.colins.rpc.sdk.config.EasyRpcConfig;
import cn.colins.rpc.sdk.spring.constant.EasyRpcSpringConstant;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/14
 */
public class EasyRpcCloseEvent implements ApplicationListener<ContextClosedEvent> {

    private EasyRpcConfig rpcConfig;

    private EasyRpcCenter rpcCenter;

    public EasyRpcCloseEvent(EasyRpcConfig rpcConfig, EasyRpcCenter rpcCenter) {
        this.rpcConfig = rpcConfig;
        this.rpcCenter = rpcCenter;
    }


    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        // 注销服务即可 其他中断无所谓
        // netty server下线 client会断开重连
        rpcCenter.deregisterInstance(new ServiceInstance(rpcConfig.getProtocol().getPort()));
    }
}
