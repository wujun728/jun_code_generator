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

/**
 * Validate the @ValidateLength annotation
 *
 * @author taomk
 * @version 1.0
 * @since 2014/10/20 15:44
 */
@Slf4j
@Service("validateLengthHandler")
public class ValidateLengthHandler implements Handler {

    @Override
    public void validate(Annotation annotation, Object value) {
        log.info(this.getClass().getName(), "validate()");

        if (ValidateLength.class.isInstance(annotation)) {
            checkSize(annotation, value);
        }

        log.info(this.getClass().getName(), "validate()");
    }

    /**
     * validate the String length
     *
     * @param annotation
     *            filter validated object
     * @param value
     *            validated field or property
     * @throws ValidateException
     */
    private void checkSize(Annotation annotation, Object value){
        log.info(this.getClass().getName(), "checkSize()");

        ValidateLength validateLength = (ValidateLength) annotation;
        int min = validateLength.minLength();
        int max = validateLength.maxLength();
        String message = validateLength.message();
        String destValue;
        try {
            destValue = (String) value;
        } catch (Exception ex) {
            log.info("Get field value or cast value error. Error message: " + ex.getMessage(), ex);
            throw new ValidateException(ex.getMessage(), ex);
        }
        int size = 0;
        if (!StrKit.S_EMPTY.equals(value) && value != null) {
            size = destValue.length();
        }
        if (size < min || size > max) {
            log.info("Validate fail. Error message: validate size is:" + size + ",minSize value is:" + min + ",maxSize value is:" + max);
            throw new ValidateException(message);
        }
        log.info(this.getClass().getName(), "checkSize()");
    }

    @Override
    public void validate(AnnotationValidable validatedObj, Field field) {
        log.info(this.getClass().getName(), "validate()");

        if (field.isAnnotationPresent(ValidateLength.class)) {
            checkSize(validatedObj, field);
        }

        log.info(this.getClass().getName(), "validate()");
    }

    /**
     * validate the String length
     *
     * @param filter
     *            filter validated object
     * @param field
     *            validated field or property
     * @throws ValidateException
     */
    private void checkSize(AnnotationValidable filter, Field field) {
        log.info(this.getClass().getName(), "checkSize()");

        ValidateLength annotation = field.getAnnotation(ValidateLength.class);
        int min = annotation.minLength();
        int max = annotation.maxLength();
        String message = annotation.message();
        String value;
        try {
            value = (String) GetFiledValue.getField(filter, field.getName());
        } catch (Exception ex) {
            log.info("Get field value or cast value error. Error message: " + ex.getMessage(), ex);
            throw new ValidateException(ex.getMessage(), ex);
        }
        int size = 0;
        if (!StrKit.S_EMPTY.equals(value) && value != null) {
            size = value.length();
        }
        if (size < min || size > max) {
            log.info("Validate fail. Error message: validate size is:" + size + ",minSize value is:" + min + ",maxSize value is:" + max);
            throw new ValidateException(message);
        }
        log.info(this.getClass().getName(), "checkSize()");
    }
}
