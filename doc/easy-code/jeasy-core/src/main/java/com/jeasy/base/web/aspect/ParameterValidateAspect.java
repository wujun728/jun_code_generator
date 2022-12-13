package com.jeasy.base.web.aspect;

import com.jeasy.validate.AnnotationValidable;
import com.jeasy.validate.Validator;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Controller请求方法入参校验Aspect
 *
 * @author taomk
 * @version 1.0
 * @since 15-5-22 下午7:57
 */
@Slf4j
@Aspect
public class ParameterValidateAspect {

    @Autowired
    private Validator validator;

    /**
     * Pointcut
     * 定义Pointcut，Pointcut的名称为aspectjMethod()，此方法没有返回值和参数
     * 该方法就是一个标识，不进行调用
     */
    @Pointcut("execution(public * com.jeasy..*Controller.*(..))")
    private void aspectjMethod() {}

    @Around(value = "aspectjMethod()")
    public Object aroundAdvice(final ProceedingJoinPoint pjp) throws Throwable {
        // 获取目标对象
        //  Object target = pjp.getTarget();
        // 获取目标对象对应的类名
        // String className = target.getClass().getName();
        // 获取目标对象正在执行的方法
        Signature signature = pjp.getSignature();
        // 获取目标对象上正在执行的方法名
        // String methodName = signature.getName();
        // 获取目标对象上正在执行的方法名参数
        Object[] args = pjp.getArgs();
        // 获取目标对象上正在执行的Methods
        Method targetMethod = ((MethodSignature) signature).getMethod();
        // 获取方法参数注解数组
        Annotation[][] parameterAnnotations = targetMethod.getParameterAnnotations();

        for (int i = 0; i < parameterAnnotations.length; i++) {
            if (args[i] instanceof AnnotationValidable) {
                validator.validate((AnnotationValidable) args[i]);
            } else {
                for (int j = 0; j < parameterAnnotations[i].length; j++) {
                    Annotation annotation = parameterAnnotations[i][j];
                    if (annotation.annotationType().getSimpleName().startsWith(Validator.PREFIX)) {
                        validator.validate(annotation, args[i]);
                    }
                }
            }
        }

        return pjp.proceed();
    }
}
