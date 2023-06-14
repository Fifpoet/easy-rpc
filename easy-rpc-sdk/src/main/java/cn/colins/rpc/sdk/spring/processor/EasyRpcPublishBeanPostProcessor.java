package cn.colins.rpc.sdk.spring.processor;

import cn.colins.rpc.core.domain.ServiceMetaData;
import cn.colins.rpc.remote.context.EasyRpcRemoteContext;
import cn.colins.rpc.sdk.annotation.EasyRpcServicePublish;
import cn.colins.rpc.sdk.exception.EasyRpcSpringException;
import cn.colins.rpc.sdk.spring.constant.EasyRpcSpringConstant;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

/**
 * @Description 针对 EasyRpcServicePublish 注解 需要把该注解标识的Bean缓存
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/14
 */
public class EasyRpcPublishBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    private final static Logger log = LoggerFactory.getLogger(EasyRpcPublishBeanPostProcessor.class);

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        EasyRpcServicePublish annotation = bean.getClass().getAnnotation(EasyRpcServicePublish.class);
        if (annotation != null) {
            beanName= StrUtil.isEmpty(annotation.beanRefName()) ? beanName : annotation.beanRefName();
            Class<?>[] interfaces = bean.getClass().getInterfaces();
            if (interfaces == null || interfaces.length == 0 || interfaces.length > 1) {
                throw new EasyRpcSpringException(String.format("[ %s ] It is impossible not to implement or implement multiple interfaces ", beanName));
            }
            // 添加发布服务bean 缓存
            EasyRpcRemoteContext.registerProducerCache(beanName,bean);
            EasyRpcRemoteContext.registerProducerTypeCache(beanName,interfaces[0].getName());
            // 添加需要发布的元数据
            EasyRpcSpringConstant.serviceMetaDataList.add(new ServiceMetaData(interfaces[0],beanName));
        }
        return bean;
    }

}
