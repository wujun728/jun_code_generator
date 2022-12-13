package com.jeasy.base.web.annotation;

import com.jeasy.common.str.StrKit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClientRequestMapping {

    String value() default StrKit.S_EMPTY;

    String[] headers() default {};

    String[] methods() default {};
}
