package com.jeasy.validate.handler;

import com.jeasy.common.str.StrKit;
import com.jeasy.validate.AnnotationValidable;
import com.jeasy.validate.GetFiledValue;
import com.jeasy.validate.Handler;
import com.jeasy.validate.ValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * validate the @ValidateStringIn annotation
 *
 * @author taomk
 * @version 1.0
 * @since 2014/10/20 15:44
 */
@Slf4j
@Service("validateStringInHandler")
public class ValidateStringInHandler implements Handler {

    @Override
    public void validate(Annotation annotation, Object value) {
        log.info(this.getClass().getName(), "validate()");

        if (ValidateStringIn.class.isInstance(annotation)) {
            checkStringIn(annotation, value);
        }

        log.info(this.getClass().getName(), "validate()");
    }

    /**
     * validate the String value
     *
     * @param annotation
     *            filter validated object
     * @param value
     *            validated field or property
     * @throws ValidateException
     */
    private void checkStringIn(Annotation annotation, Object value) {
        log.info(this.getClass().getName(), "checkStringIn()");

        ValidateStringIn validateStringIn = (ValidateStringIn) annotation ;
        String exceptValue = validateStringIn.value();
        String message = validateStringIn.message();
        String destValue;
        try {
            destValue = (String) value;
        } catch (Exception ex) {
            log.info("Get field value or cast value error. Error message: " + ex.getMessage(), ex);
            throw new ValidateException(ex.getMessage(), ex);
        }
        if (!StrKit.S_EMPTY.equals(destValue) && destValue != null) {
            String[] validateValues = exceptValue.split(",");
            List<String> list = Arrays.asList(validateValues);
            if (!list.contains(destValue)) {
                log.info("Validate fail. Error message: validate value is: " + destValue);
                throw new ValidateException(message);
            }
        }

        log.info(this.getClass().getName(), "checkStringIn()");
    }

    @Override
    public void validate(AnnotationValidable validatedObj, Field field) {
        log.info(this.getClass().getName(), "validate()");

        if (field.isAnnotationPresent(ValidateStringIn.class)) {
            checkStringIn(validatedObj, field);
        }

        log.info(this.getClass().getName(), "validate()");
    }

    /**
     * validate the String value
     *
     * @param filter
     *            filter validated object
     * @param field
     *            validated field or property
     * @throws ValidateException
     */
    private void checkStringIn(AnnotationValidable filter, Field field) {
        log.info(this.getClass().getName(), "checkStringIn()");

        ValidateStringIn annotation = field.getAnnotation(ValidateStringIn.class);
        String exceptValue = annotation.value();
        String message = annotation.message();
        String value;
        try {
            value = (String) GetFiledValue.getField(filter, field.getName());
        } catch (Exception ex) {
            log.info("Get field value or cast value error. Error message: " + ex.getMessage(), ex);
            throw new ValidateException(ex.getMessage(), ex);
        }
        if (!StrKit.S_EMPTY.equals(value) && value != null) {
            String[] validateValues = exceptValue.split(",");
            List<String> list = Arrays.asList(validateValues);
            if (!list.contains(value)) {
                log.info("Validate fail. Error message: validate value is: " + value);
                throw new ValidateException(message);
            }
        }

        log.info(this.getClass().getName(), "checkStringIn()");
    }
}
