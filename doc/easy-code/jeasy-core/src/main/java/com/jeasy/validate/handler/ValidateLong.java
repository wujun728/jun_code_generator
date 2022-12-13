package com.jeasy.validate.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to validate long type
 *
 * @author taomk
 * @version 1.0
 * @since 2014/10/20 15:44
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface ValidateLong {

    long min() default Long.MIN_VALUE;

    long max() default Long.MAX_VALUE;

    String message() default "Value of the long is not in expected scope.";
}
