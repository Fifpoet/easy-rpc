package cn.colins.rpc.sdk.spring.processor;

import cn.colins.rpc.core.proxy.CglibInvokeBeanProxyFactory;
import cn.colins.rpc.remote.EasyRpcClient;
import cn.colins.rpc.sdk.annotation.EasyRpcServiceInvoke;
import cn.colins.rpc.sdk.exception.EasyRpcSpringException;
import cn.colins.rpc.sdk.spring.constant.EasyRpcSpringConstant;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Modifier;

/**
 * @Description 需要对 EasyRpcServiceInvoke 注解进行属性注入，注入自定义的代理类
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/14
 */
public class EasyRpcInvokeBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    private final static Logger log = LoggerFactory.getLogger(EasyRpcInvokeBeanPostProcessor.class);

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        easyRpcServiceInvokeAnnotationHandler(bean.getClass(), bean);
        return pvs;
    }

    private void easyRpcServiceInvokeAnnotationHandler(Class<?> aClass, Object bean) {
        ReflectionUtils.doWithLocalFields(aClass, (field) -> {
            if (field.isAnnotationPresent(EasyRpcServiceInvoke.class)) {
                if (Modifier.isStatic(field.getModifiers())) {
                    throw new IllegalStateException("@EasyRpcServiceInvoke annotation is not supported on static fields");
                }
                EasyRpcServiceInvoke annotation = AnnotationUtils.getAnnotation(field, EasyRpcServiceInvoke.class);
                String[] split = field.getType().toString().split("\\.");
                String beanRef = StrUtil.isEmpty(annotation.beanRefName()) ? StrUtil.lowerFirst(split[split.length-1]):annotation.beanRefName();
                String serviceId = annotation.serviceId();
                String interfaces = field.getType().toString().split(" ")[1];
                try {
                    field.setAccessible(true);
                    // 注入一个动态代理对象
                    field.set(bean, CglibInvokeBeanProxyFactory.getClientInvokeProxy(field.getType(), serviceId, beanRef,interfaces));
                    // 添加需要订阅的服务
                    EasyRpcSpringConstant.serviceIdList.add(serviceId);
                } catch (Exception e) {
                    log.error("Easy-Rpc bean:[{}] set field:[{}] error:{}", bean.getClass(), field.getName(), e.getMessage(), e);
                    throw new EasyRpcSpringException(e.getMessage());
                }
            }
        });
    }
}
