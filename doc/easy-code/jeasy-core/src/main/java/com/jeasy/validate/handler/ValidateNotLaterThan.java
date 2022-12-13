package com.jeasy.validate.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to validate start time not later than end time
 *
 * @author taomk
 * @version 1.0
 * @since 2014/10/20 15:44
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidateNotLaterThan {

    String laterTime();

    String message() default "Start time should not later than end time.";
}
