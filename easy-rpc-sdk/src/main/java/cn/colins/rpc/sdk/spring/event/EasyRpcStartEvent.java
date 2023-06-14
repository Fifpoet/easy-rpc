package cn.colins.rpc.sdk.spring.event;

import cn.colins.rpc.core.center.EasyRpcCenter;
import cn.colins.rpc.core.constant.EasyRpcConstant;
import cn.colins.rpc.core.domain.ServiceInstance;

import cn.colins.rpc.remote.EasyRpcServer;
import cn.colins.rpc.remote.context.EasyRpcRemoteContext;
import cn.colins.rpc.remote.handler.EasyRpcServerHandlerInit;
import cn.colins.rpc.remote.utils.ThreadPoolUtils;
import cn.colins.rpc.sdk.config.EasyRpcConfig;
import cn.colins.rpc.sdk.spring.constant.EasyRpcSpringConstant;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/14
 */
public class EasyRpcStartEvent implements ApplicationListener<ContextRefreshedEvent> {

    private EasyRpcConfig rpcConfig;

    private EasyRpcCenter rpcCenter;

    public EasyRpcStartEvent(EasyRpcConfig rpcConfig, EasyRpcCenter rpcCenter) {
        this.rpcConfig = rpcConfig;
        this.rpcCenter = rpcCenter;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 启动NettyServer
        ThreadPoolUtils.startNettyPool.execute(new EasyRpcServer(rpcConfig.getProtocol().getPort(), new EasyRpcServerHandlerInit()));
        // 发布服务
        rpcCenter.registerInstance(new ServiceInstance(rpcConfig.getProtocol().getPort(), EasyRpcSpringConstant.serviceMetaDataList));
        // 订阅服务
        EasyRpcSpringConstant.serviceIdList.forEach(item->{
            rpcCenter.subscribeInstance(item);
        });
    }
}
