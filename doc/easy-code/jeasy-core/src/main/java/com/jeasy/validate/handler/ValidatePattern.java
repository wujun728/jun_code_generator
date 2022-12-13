package com.jeasy.validate.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to validate String,it support regex.
 *
 * @author taomk
 * @version 1.0
 * @since 2014/10/20 15:44
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface ValidatePattern {

    String pattern();

    String message() default "This value is not valid format.";
}
