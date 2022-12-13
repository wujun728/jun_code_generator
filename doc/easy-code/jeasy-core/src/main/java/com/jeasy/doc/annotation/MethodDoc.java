package com.jeasy.doc.annotation;

import com.jeasy.common.str.StrKit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodDoc {

    String name() default StrKit.S_EMPTY;

    String[] desc() default {};

    String input() default StrKit.S_EMPTY;

    StatusEnum status() default StatusEnum.TODO;

    String author();

    String finishTime() default StrKit.S_EMPTY;

    // 响应单个实体
    Class entity() default Object.class;

    // 响应集合
    Class lists() default Object.class;

    // 响应分页
    Class pages() default Object.class;
}
