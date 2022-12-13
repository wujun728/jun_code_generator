package com.jeasy.base.datasource.aspect;

import com.jeasy.base.datasource.DataSourceTypeManager;
import com.jeasy.common.Func;
import com.jeasy.common.date.DateKit;
import com.jeasy.common.str.StrKit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
@Aspect
@Order(0)
public class DataSourceRoutingAspectProcessor implements BeanPostProcessor {

    private Map<String, Boolean> readMethodMap = new HashMap<>();

    private List<String> lookupMasterKeys = new ArrayList<>();

    private List<String> lookupSlaveKeys = new ArrayList<>();

    private Boolean forceChoiceReadWhenWrite = Boolean.TRUE;

    public void setForceChoiceReadWhenWrite(final boolean forceChoiceReadWhenWrite) {
        this.forceChoiceReadWhenWrite = forceChoiceReadWhenWrite;
    }

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {

//        if (bean instanceof NameMatchTransactionAttributeSource) {
//            try {
//                NameMatchTransactionAttributeSource transactionAttributeSource = (NameMatchTransactionAttributeSource) bean;
//                Field nameMapField = ReflectionUtils.findField(NameMatchTransactionAttributeSource.class, "nameMap");
//                nameMapField.setAccessible(true);
//                Map<String, TransactionAttribute> nameMap = (Map<String, TransactionAttribute>) nameMapField.get(transactionAttributeSource);
//
//                for (Map.Entry<String, TransactionAttribute> entry : nameMap.entrySet()) {
//                    RuleBasedTransactionAttribute attr = (RuleBasedTransactionAttribute) entry.getValue();
//
//                    //仅对read-only的处理
//                    if (!attr.isReadOnly()) {
//                        continue;
//                    }
//
//                    String methodName = entry.getKey();
//                    Boolean isForceChoiceRead = Boolean.FALSE;
//                    if (forceChoiceReadWhenWrite) {
//                        //不管之前操作是写，默认强制从读库读 （设置为NOT_SUPPORTED即可）
//                        //NOT_SUPPORTED会挂起之前的事务
//                        attr.setPropagationBehavior(Propagation.NOT_SUPPORTED.value());
//                        isForceChoiceRead = Boolean.TRUE;
//                    } else {
//                        //否则 设置为SUPPORTS（这样可以参与到写事务）
//                        attr.setPropagationBehavior(Propagation.SUPPORTS.value());
//                    }
//                    readMethodMap.put(methodName, isForceChoiceRead);
//                }
//            } catch (Exception e) {
//                log.error("Init readMethodMap occur exception : ", e);
//            }
//        }

//        if (bean instanceof ThreadLocalRoutingDataSource) {
//            try {
//                ThreadLocalRoutingDataSource routingDataSource = (ThreadLocalRoutingDataSource) bean;
//                Field targetDataSourcesField = ReflectionUtils.findField(ThreadLocalRoutingDataSource.class, "targetDataSources");
//                targetDataSourcesField.setAccessible(true);
//                Map<Object, Object> targetDataSources = (Map<Object, Object>) targetDataSourcesField.get(routingDataSource);
//
//                for (Map.Entry<Object, Object> entry : targetDataSources.entrySet()) {
//                    if (entry.getKey().toString().contains(DataSourceTypeManager.MASTER)) {
//                        lookupMasterKeys.add(entry.getKey().toString());
//                    } else if (entry.getKey().toString().contains(DataSourceTypeManager.SLAVE)) {
//                        lookupSlaveKeys.add(entry.getKey().toString());
//                    }
//                }
//            } catch (Exception e) {
//                log.error("Init lookupMasterKeys or lookupSlaveKeys occur exception : ", e);
//            }
//
//        }
        return bean;
    }

//    @Pointcut("execution(public * com.jeasy..service.*.*(..))")
//    public void aspectjMethod() {
//    }

//    @Around(value = "aspectjMethod()")
//    public Object aroundAdvice(final ProceedingJoinPoint pjp) throws Throwable {
//        String className = pjp.getTarget().getClass().getName();
//        String methodName = pjp.getSignature().getName();
//
//        StringBuilder logMsg = new StringBuilder("\nDataSource routing report -------- " + DateKit.currentDatetime() + " -------------------------------------");
//        logMsg.append("\nService   : ").append(className);
//        logMsg.append("\nMethod    : ").append(methodName);
//        logMsg.append("\nDataSource: ").append(DataSourceTypeManager.get()).append(" ====> ");
//
//        if (isChoiceReadDB(pjp.getSignature().getName())) {
//            Collections.shuffle(lookupSlaveKeys);
//            DataSourceTypeManager.markRead(lookupSlaveKeys.get(0));
//        } else {
//            Collections.shuffle(lookupMasterKeys);
//            DataSourceTypeManager.markWrite(lookupMasterKeys.get(0));
//        }
//        logMsg.append(DataSourceTypeManager.get());
//        logMsg.append("\n--------------------------------------------------------------------------------------------");
//        log.info(logMsg.toString());
//
//        try {
//            return pjp.proceed();
//        } finally {
//            DataSourceTypeManager.reset();
//        }
//    }

    @Pointcut("execution(public * com.jeasy..manager.*.*(..))")
    public void aspectjMethod() {
    }

    @Around(value = "aspectjMethod()")
    public Object aroundAdvice(final ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();

        StringBuilder logMsg = new StringBuilder("\nDataSource routing report -------- " + DateKit.currentDatetime() + " -------------------------------------");
        logMsg.append("\nClass     : ").append(className);
        logMsg.append("\nMethod    : ").append(methodName);
        logMsg.append("\nDataSource: ").append(DataSourceTypeManager.get()).append(" ====> ");

        Signature signature = pjp.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = pjp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
            DS ds = method.getAnnotation(DS.class);
            if (Func.isNotEmpty(ds) && StrKit.isNotBlank(ds.value())) {
                DataSourceTypeManager.set(ds.value());
            }
        }

        logMsg.append(DataSourceTypeManager.get());
        logMsg.append("\n--------------------------------------------------------------------------------------------");
        log.info(logMsg.toString());

        try {
            return pjp.proceed();
        } finally {
            DataSourceTypeManager.reset();
        }
    }

//    private boolean isChoiceReadDB(final String methodName) {
//
//        String bestNameMatch = null;
//        for (String mappedName : this.readMethodMap.keySet()) {
//            if (PatternMatchUtils.simpleMatch(mappedName, methodName)) {
//                bestNameMatch = mappedName;
//                break;
//            }
//        }
//
//        Boolean isForceChoiceRead = readMethodMap.get(bestNameMatch);
//        //表示强制选择读库
//        if (ConvertKit.toBool(isForceChoiceRead, false)) {
//            return true;
//        }
//
//        //如果之前选择了写库 现在还选择 写库
//        if (DataSourceTypeManager.isChoiceWrite()) {
//            return false;
//        }
//
//        //表示应该选择读库
//        if (Func.isNotEmpty(isForceChoiceRead)) {
//            return true;
//        }
//
//        //默认选择 写库
//        return false;
//    }
}
