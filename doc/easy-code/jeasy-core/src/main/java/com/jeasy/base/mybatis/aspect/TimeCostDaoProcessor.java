package com.jeasy.base.mybatis.aspect;

import com.jeasy.common.str.StrKit;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * BeanPostProcessor, 对所有符合rose框架的DAO织入一段统计代码
 * 用于分析SQL热点、耗时等信息
 *
 * @author taomk
 * @version 1.0
 * @since 2014/9/17 10:26
 */
@Slf4j
public class TimeCostDaoProcessor implements BeanPostProcessor {

    public static final String DAO = "DAO";
    @Setter
    private TimeCostDaoInterceptor interceptor;

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        if (StrKit.endWith(beanName, DAO, true)) {
            Class<?> beanClass = bean.getClass();
            Class<?>[] interfaceClasses = beanClass.getInterfaces();
            if (interfaceClasses.length == 1 && interfaceClasses[0].getName().endsWith(DAO)) {
                ProxyFactory weaver = new ProxyFactory(interfaceClasses);
                NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
                advisor.addMethodName("*");
                advisor.setAdvice(interceptor);
                weaver.addAdvisor(advisor);
                weaver.setTarget(bean);
                return weaver.getProxy();
            }
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        return bean;
    }
}
