package com.jeasy.validate.handler;

import com.jeasy.validate.AnnotationValidable;
import com.jeasy.validate.GetFiledValue;
import com.jeasy.validate.Handler;
import com.jeasy.validate.ValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * Validate the @ValidateBeforeThan annotation
 *
 * @author taomk
 * @version 1.0
 * @since 2014/10/20 15:44
 */
@Slf4j
@Service("validateNotLaterThanHandler")
public class ValidateNotLaterThanHandler implements Handler {

    @Override
    public void validate(Annotation annotation, Object value) {
        // TODO
    }

    @Override
    public void validate(AnnotationValidable validatedObj, Field field) {
        log.info(this.getClass().getName(), "validate()");

        if (field.isAnnotationPresent(ValidateNotLaterThan.class)) {
            checkTime(validatedObj, field);
        }

        log.info(this.getClass().getName(), "validate()");
    }

    /**
     * validate if the start time is early than the end time
     *
     * @param filter
     *            validated object
     * @param field
     *            validated field or property
     * @throws ValidateException
     */
    private void checkTime(AnnotationValidable filter, Field field) {
        log.info(this.getClass().getName(), "checkTime()");

        ValidateNotLaterThan annotation = field.getAnnotation(ValidateNotLaterThan.class);
        String sTime = field.getName();
        String eTime = annotation.laterTime();
        String message = annotation.message();
        Date startTime;
        Date endTime;
        try {
            startTime = (Date) GetFiledValue.getField(filter, sTime);
            endTime = (Date) GetFiledValue.getField(filter, eTime);
        } catch (Exception ex) {
            log.info("Get field value or cast value error. Error message: " + ex.getMessage(), ex);
            throw new ValidateException(ex.getMessage(), ex);
        }

        if (startTime != null && endTime != null) {
            if (startTime.after(endTime)) {
                log.info("Validate fail. Error message: startTime is: " + startTime + ", endTime is:" + endTime);
                throw new ValidateException(message + "The startTime is: " + startTime + ",the endTime is:" + endTime);
            }
        }else{
            log.info("Validate fail. Error message: startTime or endTime is null,startTime is: " + startTime + ", endTime is:" + endTime);
            throw new ValidateException(message + "StartTime or endTime is null.The startTime is: " + startTime + ",the endTime is:" + endTime);
        }

        log.info(this.getClass().getName(), "checkTime()");
    }
}
