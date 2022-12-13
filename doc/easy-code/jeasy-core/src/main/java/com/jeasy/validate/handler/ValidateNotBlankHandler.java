package com.jeasy.validate.handler;

import com.jeasy.common.Func;
import com.jeasy.validate.AnnotationValidable;
import com.jeasy.validate.GetFiledValue;
import com.jeasy.validate.Handler;
import com.jeasy.validate.ValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Validate the @ValidateNotBlank annotation
 *
 * @author taomk
 * @version 1.0
 * @since 2014/10/20 15:44
 */
@Slf4j
@Service("validateNotBlankHandler")
public class ValidateNotBlankHandler implements Handler {

    @Override
    public void validate(Annotation annotation, Object value) {
        log.info(this.getClass().getName(), "validate()");

        if (ValidateNotBlank.class.isInstance(annotation)) {
            checkNotBlank(annotation, value);
        }

        log.info(this.getClass().getName(), "validate()");
    }

    private void checkNotBlank(Annotation annotation, Object value) {
        log.info(this.getClass().getName(), "checkNotBlank()");

        String message = ((ValidateNotBlank) annotation).message();
        Object dest;

        try {
            dest = value;
        } catch (Exception ex) {
            log.info("Get field value or cast value error. Error message: " + ex.getMessage(), ex);
            throw new ValidateException(ex.getMessage(), ex);
        }

        if (Func.isEmpty(dest)) {
            log.info("Validate fail. Error message: validate value is : blank");
            throw new ValidateException(message);
        }

        log.info(this.getClass().getName(), "checkNotBlank()");
    }

    @Override
    public void validate(AnnotationValidable validatedObj, Field field) {
        log.info(this.getClass().getName(), "validate()");

        if (field.isAnnotationPresent(ValidateNotBlank.class)) {
            checkNotBlank(validatedObj, field);
        }

        log.info(this.getClass().getName(), "validate()");
    }

    private void checkNotBlank(AnnotationValidable filter, Field field) {
        log.info(this.getClass().getName(), "checkNotBlank()");

        String message = field.getAnnotation(ValidateNotBlank.class).message();
        Object dest;

        try {
            dest = GetFiledValue.getField(filter, field.getName());
        } catch (Exception ex) {
            log.info("Get field value or cast value error. Error message: " + ex.getMessage(), ex);
            throw new ValidateException(ex.getMessage(), ex);
        }

        if (Func.isEmpty(dest)) {
            log.info("Validate fail. Error message: validate value is : blank");
            throw new ValidateException(message);
        }

        log.info(this.getClass().getName(), "checkNotBlank()");
    }
}
