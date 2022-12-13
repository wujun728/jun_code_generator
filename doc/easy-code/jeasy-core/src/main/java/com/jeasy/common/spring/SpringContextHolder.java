package com.jeasy.common.spring;

import com.jeasy.common.Func;
import com.jeasy.common.str.StrKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    private SpringContextHolder() {
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (Func.isEmpty(SpringContextHolder.context)) {
            SpringContextHolder.context = context;
        }
    }

    public static <T> T getBean(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        return context.getBean(clazz);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (null == beanName || StrKit.S_EMPTY.equals(beanName.trim())) {
            return null;
        }
        if (clazz == null) {
            return null;
        }
        return context.getBean(beanName, clazz);
    }

    public static ApplicationContext getContext() {
        if (context == null) {
            return null;
        }
        return context;
    }

    public static void publishEvent(ApplicationEvent event) {
        if (context == null) {
            return;
        }
        context.publishEvent(event);
    }

}
