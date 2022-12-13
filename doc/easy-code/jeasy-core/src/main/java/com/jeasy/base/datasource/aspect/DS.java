package com.jeasy.base.datasource.aspect;

import com.jeasy.common.str.StrKit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author taomk
 * @version 1.0
 * @since 15-8-25 上午11:58
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DS {

    String value() default StrKit.S_EMPTY;
}
