package com.jeasy.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;


/**
 * This is the validate process interface
 *
 * @author taomk
 * @version 1.0
 * @since 2014/10/20 15:44
 */
public interface Handler {

    /**
     * validate
     *
     * @param annotation
     * @param value
     * @throws ValidateException
     */
    void validate(Annotation annotation, Object value) throws ValidateException;

    /**
     * validate
     *
     * @param validatedObj
     * @param field
     * @throws ValidateException
     */
    void validate(AnnotationValidable validatedObj, Field field) throws ValidateException;
}
