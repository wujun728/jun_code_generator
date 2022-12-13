package com.jeasy.validate;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/10/20 15:44
 */
@Slf4j
public class Validator {

    public static final String PREFIX = "Validate";

    @Setter
    private Map<String, Handler> handlerMap;

    @SuppressWarnings("unchecked")
    public void validate(Annotation annotation, Object value) throws ValidateException {
        log.info(this.getClass().getName(), "validate()");

        String annotationName = annotation.annotationType().getSimpleName();
        Handler handler = handlerMap.get(annotationName);

        if (handler != null) {
            handler.validate(annotation, value);
        }

        log.info(this.getClass().getName(), "validate()");
    }

    @SuppressWarnings("unchecked")
    public void validate(AnnotationValidable validatedObj) throws ValidateException {
        if (null == validatedObj) {
            log.warn("The input validatedObj is null.");
            return;
        }

        log.info(this.getClass().getName(), "validate()");

        Class currentClass = validatedObj.getClass();
        while (currentClass != null) {
            Field[] fields = currentClass.getDeclaredFields();
            for (Field elem : fields) {
                validateField(validatedObj, elem);
            }

            Class superClass = currentClass.getSuperclass();
            currentClass = AnnotationValidable.class.isAssignableFrom(superClass) ? superClass : null;
        }

        log.info(this.getClass().getName(), "validate()");
    }

    private void validateField(AnnotationValidable validatedObj, Field field) throws ValidateException {
        // check whether the field is also AnnotationValidable
        if(AnnotationValidable.class.isAssignableFrom(field.getType())) {
            Object destValue;
            try {
                destValue = GetFiledValue.getField(validatedObj, field.getName());
            } catch (Exception ex) {
                log.info("Get field value or cast value error for field " + field.getName() + " in Class " + validatedObj.getClass() + ". Error message: " + ex.getMessage(), ex);
                throw new ValidateException("Get field value or cast value error for field " + field.getName() + " in Class " + validatedObj.getClass() + ". Error message: " + ex.getMessage(), ex);
            }

            if(destValue == null) {
                return;
            } else {
                validate((AnnotationValidable)destValue);
            }
        }

        List<Annotation> annotations = getValidateAnnotations(field);
        if (annotations != null && annotations.size() > 0) {
            // loop each field annotations
            for (Annotation annotation : annotations) {
                String annotationName = annotation.annotationType().getSimpleName();
                Handler handler;
                try {
                    handler = handlerMap.get(annotationName);
                } catch (Exception ex) {
                    log.info("Get validate handler error. Error message: " + ex.getMessage(), ex);
                    throw new ValidateException("Can not get the handler for " + ex.getMessage(), ex);
                }
                if (handler == null ) {
                    log.info("Get validate handler is null.");
                    throw new ValidateException("Get validate handler is null.");
                } else {
                    handler.validate(validatedObj, field);
                }
            }
        }
    }

    private List<Annotation> getValidateAnnotations(Field field) {
        List<Annotation> annotations = new ArrayList<>();
        Annotation[] fieldAnnotations = field.getAnnotations();

        for (Annotation elem : fieldAnnotations) {
            if (elem.annotationType().getSimpleName().startsWith(PREFIX)) {
                annotations.add(elem);
            }
        }

        return annotations;
    }
}
