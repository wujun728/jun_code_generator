package com.jeasy.validate.handler;

import com.jeasy.validate.AnnotationValidable;
import com.jeasy.validate.GetFiledValue;
import com.jeasy.validate.Handler;
import com.jeasy.validate.ValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Validate the @ValidateNotNull annotation
 *
 * @author taomk
 * @version 1.0
 * @since 2014/10/20 15:44
 */
@Slf4j
@Service("validateNotEmptyHandler")
public class ValidateNotEmptyHandler implements Handler {

    @Override
    public void validate(Annotation annotation, Object value) {
        log.info(this.getClass().getName(), "validate()");

        if (ValidateNotEmpty.class.isInstance(annotation)) {
            checkNotEmpty(annotation, value);
        }

        log.info(this.getClass().getName(), "validate()");
    }

    @SuppressWarnings("unchecked")
    private void checkNotEmpty(Annotation annotation, Object value) {
        log.info(this.getClass().getName(), "checkNotNull()");

        String message = ((ValidateNotEmpty)annotation).message();
        try {
            if (value.getClass().isArray()) {
                Object[] dest = (Object[]) value;
                if (dest.length == 0) {
                    log.info("The collection is empty.");
                    throw new ValidateException(message);
                }
            } else {
                Collection dest = (Collection) value;
                if (dest.size() == 0) {
                    log.info("The collection is empty.");
                    throw new ValidateException(message);
                }
            }
        } catch (Exception ex) {
            log.info("Get field value or cast value error. Error message: " + ex.getMessage(), ex);
            throw new ValidateException(ex.getMessage(), ex);
        }
    }

    @Override
    public void validate(AnnotationValidable validatedObj, Field field) {
        log.info(this.getClass().getName(), "validate()");

        if (field.isAnnotationPresent(ValidateNotEmpty.class)) {
            checkNotEmpty(validatedObj, field);
        }

        log.info(this.getClass().getName(), "validate()");
    }

    @SuppressWarnings("unchecked")
    private void checkNotEmpty(AnnotationValidable filter, Field field) {
        log.info(this.getClass().getName(), "checkNotNull()");

        String message = field.getAnnotation(ValidateNotEmpty.class).message();
        try {
            if (field.getType().isArray()) {
                Object [] dest = (Object []) GetFiledValue.getField(filter, field.getName());
                if (dest == null || dest.length == 0) {
                    log.info("The collection "+ field.getName() +" is empty.");
                    throw new ValidateException(message);
                }
            } else {
                Collection dest = (Collection) GetFiledValue.getField(filter, field.getName());
                if (dest == null || dest.size() == 0) {
                    log.info("The collection "+ field.getName() +" is empty.");
                    throw new ValidateException(message);
                }
            }
        } catch (Exception ex) {
            log.info("Get field value or cast value error. Error message: " + ex.getMessage(), ex);
            throw new ValidateException(ex.getMessage(), ex);
        }
    }
}
