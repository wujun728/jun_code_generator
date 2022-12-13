package com.jeasy.validate.handler;

import com.jeasy.validate.AnnotationValidable;
import com.jeasy.validate.GetFiledValue;
import com.jeasy.validate.Handler;
import com.jeasy.validate.ValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Validate the @int annotation
 *
 * @author taomk
 * @version 1.0
 * @since 2014/10/20 15:44
 */
@Slf4j
@Service("validateIntHandler")
public class ValidateIntHandler implements Handler {


    @Override
    public void validate(Annotation annotation, Object value) {
        log.info(this.getClass().getName(), "validate()");

        if (ValidateInt.class.isInstance(annotation)) {
            checkInt(annotation, value);
        }

        log.info(this.getClass().getName(), "validate()");
    }

    /**
     * validate the int type
     *
     * @param annotation
     *            validated annotation
     * @param value
     *            validated value
     * @throws ValidateException
     */
    private void checkInt(Annotation annotation, Object value) {
        log.info(this.getClass().getName(), "checkInt()");

        ValidateInt validateInt = (ValidateInt)annotation;
        int min = validateInt.min();
        int max = validateInt.max();
        String message = validateInt.message();

        Integer destValue;
        try {
            destValue = (Integer) value;
        } catch (Exception ex) {
            log.info("Get field value or cast value error. Error message: " + ex.getMessage(), ex);
            throw new ValidateException(ex.getMessage(), ex);
        }

        if(destValue == null) {
            return; //S_NULL value is allowed.
        }

        if (destValue < min) {
            log.info("Validate fail. Error message: validate value is:{0},min value is:{1}", new Integer[]{destValue, min});
            throw new ValidateException(message);
        }

        if (destValue > max) {
            log.info("Validate fail. Error message: validate value is:{0},max value is:{1}", new Integer[]{destValue, max});
            throw new ValidateException(message);
        }

        log.info(this.getClass().getName(), "checkInt()");
    }

    @Override
    public void validate(AnnotationValidable validatedObj, Field field) {
        log.info(this.getClass().getName(), "validate()");

        if (field.isAnnotationPresent(ValidateInt.class)) {
            checkInt(validatedObj, field);
        }

        log.info(this.getClass().getName(), "validate()");
    }

    /**
     * validate the int type
     *
     * @param filter
     *            validated object
     * @param field
     *            validated field or property
     * @throws ValidateException
     */
    private void checkInt(AnnotationValidable filter, Field field) {
        log.info(this.getClass().getName(), "checkInt()");

        ValidateInt annotation = field.getAnnotation(ValidateInt.class);
        int min = annotation.min();
        int max = annotation.max();
        String message = annotation.message();

        Integer destValue;
        try {
            destValue = (Integer) GetFiledValue.getField(filter, field.getName());
        } catch (Exception ex) {
            log.info("Get field value or cast value error. Error message: " + ex.getMessage(), ex);
            throw new ValidateException(ex.getMessage(), ex);
        }

        if(destValue == null) {
            return; //S_NULL value is allowed.
        }

        int value = destValue.intValue();

        if (value < min) {
            log.info("Validate fail. Error message: validate value is:{0},min value is:{1}", new Integer[]{value, min});
            throw new ValidateException(message);
        }

        if (value > max) {
            log.info("Validate fail. Error message: validate value is:{0},max value is:{1}", new Integer[]{value, max});
            throw new ValidateException(message);
        }

        log.info(this.getClass().getName(), "checkInt()");
    }

}
