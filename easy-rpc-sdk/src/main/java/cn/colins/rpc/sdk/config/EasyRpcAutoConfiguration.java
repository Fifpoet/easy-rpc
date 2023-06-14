package cn.colins.rpc.sdk.config;

import cn.colins.rpc.core.center.EasyRpcCenter;
import cn.colins.rpc.core.center.nacos.NacosEasyRpcCenter;
import cn.colins.rpc.sdk.spring.event.EasyRpcCloseEvent;
import cn.colins.rpc.sdk.spring.event.EasyRpcStartEvent;
import cn.colins.rpc.sdk.spring.processor.EasyRpcInvokeBeanPostProcessor;
import cn.colins.rpc.sdk.spring.processor.EasyRpcPublishBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/13
 */
@Configuration
@EnableConfigurationProperties({EasyRpcConfig.class})
public class EasyRpcAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(EasyRpcCenter.class)
    public EasyRpcCenter easyRpcCenter(EasyRpcConfig easyRpcConfig){
        return new NacosEasyRpcCenter(easyRpcConfig.getCenter(),easyRpcConfig.getApplication());
    }


    @Bean
    @ConditionalOnBean(EasyRpcCenter.class)
    public ApplicationListener easyRpcCloseEvent(EasyRpcConfig easyRpcConfig,EasyRpcCenter easyRpcCenter){
        return new EasyRpcCloseEvent(easyRpcConfig,easyRpcCenter);
    }

    @Bean
    @ConditionalOnBean(EasyRpcCenter.class)
    public ApplicationListener easyRpcStartEvent(EasyRpcConfig easyRpcConfig,EasyRpcCenter easyRpcCenter){
        return new EasyRpcStartEvent(easyRpcConfig,easyRpcCenter);
    }


    @Bean
    @ConditionalOnBean(EasyRpcCenter.class)
    public EasyRpcPublishBeanPostProcessor easyRpcPublishBeanPostProcessor(){
        return new EasyRpcPublishBeanPostProcessor();
    }

    @Bean
    @ConditionalOnBean(EasyRpcCenter.class)
    public EasyRpcInvokeBeanPostProcessor easyRpcInvokeBeanPostProcessor(){
        return new EasyRpcInvokeBeanPostProcessor();
    }


}
