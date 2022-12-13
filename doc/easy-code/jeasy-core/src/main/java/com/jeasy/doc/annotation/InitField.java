package com.jeasy.doc.annotation;

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
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface InitField {

    // 与请求参数名一致
    String name() default StrKit.S_EMPTY;

    // 构造参数值
    String value() default StrKit.S_EMPTY;

    // 参数描述
    String desc() default StrKit.S_EMPTY;
}
