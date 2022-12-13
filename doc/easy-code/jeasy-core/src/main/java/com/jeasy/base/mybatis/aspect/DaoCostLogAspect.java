package com.jeasy.base.mybatis.aspect;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.jeasy.common.date.DateKit;
import com.jeasy.common.json.JsonKit;
import com.jeasy.doc.util.MonitorUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

/**
 * DAO层日志拦截器
 *
 * @author taomk
 * @version 1.0
 * @since 15-5-22 下午7:57
 */
@Slf4j
@Aspect
public class DaoCostLogAspect {

    /**
     * Pointcut
     * 定义Pointcut，Pointcut的名称为aspectjMethod()，此方法没有返回值和参数
     * 该方法就是一个标识，不进行调用
     */
    @Pointcut("execution(public * com.jeasy..dao.*.*(..))")
    private void aspectjMethod() {
    }

    @Around(value = "aspectjMethod()")
    public Object aroundAdvice(final ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getTarget().getClass().getInterfaces()[0].getName();
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();

        StringBuilder logMsg = new StringBuilder("\nDAO execute report -------- " + DateKit.currentDatetime() + " ----------------------------------------");
        logMsg.append("\nDAO       : ").append(className);
        logMsg.append("\nMethod    : ").append(methodName);
        logMsg.append("\nParameter : ").append(Joiner.on(",").join(Lists.transform(Arrays.asList(args), new Function<Object, String>() {
            @Override
            public String apply(Object input) {
                if (input instanceof AbstractWrapper) {
                    return JsonKit.toJson(((AbstractWrapper) input).getParamNameValuePairs());
                }
                return JsonKit.toJson(input);
            }
        })));

        long startTime = System.currentTimeMillis();
        Object retVal;
        try {
            retVal = pjp.proceed();

            logMsg.append("\nResult    : ").append(JsonKit.toJson(retVal));
            logMsg.append("\nCost Time : ").append(System.currentTimeMillis() - startTime).append(" ms");
            logMsg.append("\n--------------------------------------------------------------------------------------------");
            log.info(logMsg.toString());
            return retVal;
        } catch (Throwable e) {
            MonitorUtils.incCountForException(className, methodName);
            log.error(className + "." + methodName + " Occur Exception : ", e);
            throw e;
        } finally {
            MonitorUtils.incTimeForDao(className, methodName, System.currentTimeMillis() - startTime);
        }
    }
}
