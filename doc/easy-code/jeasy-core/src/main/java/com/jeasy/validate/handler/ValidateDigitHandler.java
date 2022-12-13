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
import java.util.regex.Pattern;

/**
 * Validate the @ValidateDigit annotation
 *
 * @author taomk
 * @version 1.0
 * @since 2014/10/20 15:44
 */
@Slf4j
@Service("validateDigitHandler")
public class ValidateDigitHandler implements Handler {


    @Override
    public void validate(Annotation annotation, Object value) {
        log.info(this.getClass().getName(), "validate()");

        if (ValidateDigit.class.isInstance(annotation)) {
            checkDigit(annotation, value);
        }

        log.info(this.getClass().getName(), "validate()");
    }

    /**
     * validate the String is digit only
     *
     * @param annotation
     *            filter validated annotation
     * @param value
     *            validated field or property
     * @throws ValidateException
     */
    private void checkDigit(Annotation annotation, Object value) {
        log.info(this.getClass().getName(), "checkDigit()");

        ValidateDigit validateDigit = (ValidateDigit) annotation;
        String message = validateDigit.message();
        String destValue;
        try {
            destValue = (String) value;
        } catch (Exception ex) {
            log.info("Get field value or cast value error. Error message: " + ex.getMessage(), ex);
            throw new ValidateException(ex.getMessage(), ex);
        }
        String patternStr = "\\d*";
        Pattern pattern = Pattern.compile(patternStr);
        if (value != null && !StrKit.S_EMPTY.equals(value)) {
            if (!pattern.matcher(destValue).matches()) {
                log.info("Validate fail. Error message: validate value is:" + value);
                throw new ValidateException(message);
            }
        }

        log.info(this.getClass().getName(), "checkDigit()");
    }

    @Override
    public void validate(AnnotationValidable validatedObj, Field field) {
        log.info(this.getClass().getName(), "validate()");

        if (field.isAnnotationPresent(ValidateDigit.class)) {
            checkDigit(validatedObj, field);
        }

        log.info(this.getClass().getName(), "validate()");
    }

    /**
     * validate the String is digit only
     *
     * @param filter
     *            filter validated object
     * @param field
     *            validated field or property
     * @throws ValidateException
     */
    private void checkDigit(AnnotationValidable filter, Field field) {

        log.info(this.getClass().getName(), "checkDigit()");

        ValidateDigit annotation = field.getAnnotation(ValidateDigit.class);
        String message = annotation.message();
        String value;
        try {
            value = (String) GetFiledValue.getField(filter, field.getName());
        } catch (Exception ex) {
            log.info("Get field value or cast value error. Error message: " + ex.getMessage(), ex);
            throw new ValidateException(ex.getMessage(), ex);
        }
        String patternStr = "\\d*";
        Pattern pattern = Pattern.compile(patternStr);
        if (value != null && !StrKit.S_EMPTY.equals(value)) {
            if (!pattern.matcher(value).matches()) {
                log.info("Validate fail. Error message: validate value is:" + value);
                throw new ValidateException(message);
            }
        }

        log.info(this.getClass().getName(), "checkDigit()");
    }
}
