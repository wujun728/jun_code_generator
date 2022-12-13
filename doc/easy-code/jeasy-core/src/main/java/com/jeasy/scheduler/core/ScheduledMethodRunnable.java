package com.jeasy.scheduler.core;

import com.jeasy.common.Func;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
@Data
public class ScheduledMethodRunnable implements Runnable {

    private final Object target;

    private final Method method;

    private final String params;

    private final String extKeySuffix;

    public ScheduledMethodRunnable(final Object target, final Method method, final String params, final String extKeySuffix) {
        this.target = target;
        this.method = method;
        this.params = params;
        this.extKeySuffix = extKeySuffix;
    }

    public ScheduledMethodRunnable(final Object target, final String methodName, final String params, final String extKeySuffix) throws NoSuchMethodException {
        this.target = target;
        this.method = target.getClass().getMethod(methodName);
        this.params = params;
        this.extKeySuffix = extKeySuffix;
    }

    @Override
    public void run() {
        try {
            ReflectionUtils.makeAccessible(this.method);
            if (Func.isNotEmpty(this.getParams())) {
                this.method.invoke(this.target, this.getParams());
            } else {
                this.method.invoke(this.target);
            }
        } catch (InvocationTargetException ex) {
            ReflectionUtils.rethrowRuntimeException(ex.getTargetException());
        } catch (IllegalAccessException ex) {
            throw new UndeclaredThrowableException(ex);
        }
    }
}
