package com.jeasy.base.web.resolver;

import com.jeasy.common.str.StrKit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author taomk
 * @version 1.0
 * @since 2014/10/20 15:44
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface FromJson {

    // key
    String key() default StrKit.S_EMPTY;

    // 是否展示, 默认展示
    boolean show() default true;
}
