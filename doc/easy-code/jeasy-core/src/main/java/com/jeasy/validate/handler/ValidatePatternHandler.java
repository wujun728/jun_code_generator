package com.jeasy.validate.handler;

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
 * Validate the @ValidatePattern annotation
 *
 * @author taomk
 * @version 1.0
 * @since 2014/10/20 15:44
 */
@Slf4j
@Service("validatePatternHandler")
public class ValidatePatternHandler implements Handler {

    @Override
    public void validate(Annotation annotation, Object value) {
        log.info(this.getClass().getName(), "validate()");

        if (ValidatePattern.class.isInstance(annotation)) {
            checkString(annotation, value);
        }

        log.info(this.getClass().getName(), "validate()");
    }

    /**
     * validate the String format
     *
     * @param annotation
     *            validated object
     * @param value
     *            validated field or property
     * @throws ValidateException
     */
    private void checkString(Annotation annotation, Object value) {
        log.info(this.getClass().getName(), "checkString()");

        ValidatePattern validatePattern = (ValidatePattern) annotation;
        String patternStr = validatePattern.pattern();
        String message = validatePattern.message();

        checkStringFormat(patternStr, value, message);

        log.info(this.getClass().getName(), "checkString()");
    }

    @Override
    public void validate(AnnotationValidable validatedObj, Field field) {
        log.info(this.getClass().getName(), "validate()");

        if (field.isAnnotationPresent(ValidatePattern.class)) {
            checkString(validatedObj, field);
        }

        log.info(this.getClass().getName(), "validate()");
    }

    /**
     * validate the String format
     *
     * @param filter
     *            validated object
     * @param field
     *            validated field or property
     * @throws ValidateException
     */
    private void checkString(AnnotationValidable filter, Field field) {
        log.info(this.getClass().getName(), "checkString()");

        ValidatePattern annotation = field.getAnnotation(ValidatePattern.class);
        String patternStr = annotation.pattern();
        String message = annotation.message();
        Object value;

        try {
            value = GetFiledValue.getField(filter, field.getName());
        } catch (Exception ex) {
            log.info("Get field value or cast value error. Error message: " + ex.getMessage(), ex);
            throw new ValidateException(ex.getMessage(), ex);
        }
        checkStringFormat(patternStr, value, message);

        log.info(this.getClass().getName(), "checkString()");
    }

    /**
     * validate the String format
     *
     * @param patternStr
     *            validate regex pattern
     * @param value
     *            validated value
     * @param message
     *            exception message
     * @throws ValidateException
     */
    private void checkStringFormat(String patternStr, Object value, String message) {
        log.info(this.getClass().getName(), "checkStringFormat()");

        String checkStr = (String) value;
        Pattern pattern = Pattern.compile(patternStr);
        if (checkStr != null) {
            String[] str = checkStr.split(",");
            for (int i = 0; i < str.length; i++) {
                if (!pattern.matcher(str[i]).matches()) {
                    log.info("Validate fail. Error message: validate value is:" + str[i]);
                    throw new ValidateException(message);
                }
            }
        }

        log.info(this.getClass().getName(), "checkStringFormat()");
    }
}
