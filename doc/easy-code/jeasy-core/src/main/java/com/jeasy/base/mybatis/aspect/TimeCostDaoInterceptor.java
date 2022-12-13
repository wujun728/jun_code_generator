package com.jeasy.base.mybatis.aspect;

import com.jeasy.common.date.DateKit;
import com.jeasy.common.json.JsonKit;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Arrays;

/**
 * 对拦截的方法进行计数和计时统计，
 * 通过java.util.concurrent包减少对拦截方法本身的性能影响。
 *
 * @author taomk
 * @version 1.0
 * @since 2014/9/17 10:26
 */
@Slf4j
public class TimeCostDaoInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        String className = invocation.getMethod().getDeclaringClass().getName();
        String methodName = invocation.getMethod().getName();
        Object[] args = invocation.getArguments();

        StringBuilder logMsg = new StringBuilder("\nDAO execute report -------- " + DateKit.currentDatetime() + " ----------------------------------------");
        logMsg.append("\nDAO       : ").append(className);
        logMsg.append("\nMethod    : ").append(methodName);
        logMsg.append("\nParameter : ").append(Joiner.on(",").join(Lists.transform(Arrays.asList(args), new Function<Object, String>() {
            @Override
            public String apply(Object input) {
                return JsonKit.toJson(input);
            }
        })));

        long startTime = System.currentTimeMillis();
        Object retVal = null;
        try {
            retVal = invocation.proceed();

            logMsg.append("\nResult    : ").append(JsonKit.toJson(retVal));
            logMsg.append("\nCost Time : ").append(System.currentTimeMillis() - startTime).append(" ms");
            logMsg.append("\n--------------------------------------------------------------------------------------------");
            log.info(logMsg.toString());
            return retVal;
        } catch (Exception e) {
            // MonitorUtils.incCountForException(className, methodName);
            log.error(className + "." + methodName + " Occur Exception : ", e);
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            long timeCost = endTime - startTime;
            //  MonitorUtils.incTimeForDao(className, methodName, timeCost);
        }
    }
}
