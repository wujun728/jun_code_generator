package cn.yt4j.layuirbac.aspectj;

import cn.yt4j.layuirbac.annotation.SysLog;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author gyv12345@163.com
 * @date 2020/3/3 21:06
 */
@Slf4j
@Aspect
@Component
public class LogAspectj {
    /**
     * 增强方法
     *
     * @param point 连接点，能够获取切面的一些信息
     * @return
     */
    @Around("@annotation(sysLog)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, SysLog sysLog) {
        String strClassName = point.getTarget().getClass().getName();
        String strMethodName = point.getSignature().getName();
        log.debug("[类名]:{},[方法]:{},日志：{}", strClassName, strMethodName,sysLog.value());
        //得到日志信息
        Object obj = point.proceed();
        return obj;
    }
}
