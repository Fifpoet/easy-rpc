package cn.colins.rpc.sdk.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface EasyRpcServicePublish {

    String beanRefName() default "";

}
