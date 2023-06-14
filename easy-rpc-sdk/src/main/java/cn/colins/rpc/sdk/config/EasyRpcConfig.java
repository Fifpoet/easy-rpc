package cn.colins.rpc.sdk.config;

import cn.colins.rpc.core.config.EasyRpcApplicationConfig;
import cn.colins.rpc.core.config.EasyRpcCenterConfig;
import cn.colins.rpc.remote.config.EasyRpcServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/13
 */
@ConfigurationProperties(prefix = "easy-rpc")
public class EasyRpcConfig {

    @NestedConfigurationProperty
    private EasyRpcServerConfig protocol = new EasyRpcServerConfig();
    @NestedConfigurationProperty
    private EasyRpcApplicationConfig application = new EasyRpcApplicationConfig();
    @NestedConfigurationProperty
    private EasyRpcCenterConfig center = new EasyRpcCenterConfig();

    public EasyRpcApplicationConfig getApplication() {
        return application;
    }

    public void setApplication(EasyRpcApplicationConfig application) {
        this.application = application;
    }

    public EasyRpcCenterConfig getCenter() {
        return center;
    }

    public void setCenter(EasyRpcCenterConfig center) {
        this.center = center;
    }

    public EasyRpcServerConfig getProtocol() {
        return protocol;
    }

    public void setProtocol(EasyRpcServerConfig protocol) {
        this.protocol = protocol;
    }
}
