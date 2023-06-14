package cn.colins.rpc.sdk.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Component
public @interface EasyRpcServicePublish {

    String beanRefName() default "";

}
