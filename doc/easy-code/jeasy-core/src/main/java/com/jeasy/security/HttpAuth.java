package com.jeasy.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口授权注解
 *
 * @author TaoBangren
 * @version 1.0
 * @since 16/8/29 下午11:56
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpAuth {

    /**
     * 是否需要接口认证
     *
     * @return
     */
    boolean isRequireAuth() default false;

    /**
     * 认证过期时 是否需要重新登录
     *
     * @return
     */
    boolean isNeedReLoginWhenExpire() default false;
}
