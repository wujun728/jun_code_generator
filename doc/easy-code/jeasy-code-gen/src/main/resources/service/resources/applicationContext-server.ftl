<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context-3.0.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd
       http://www.springframework.org/schema/tx
       http://www.springframework.org/schema/tx/spring-tx.xsd">

    <!-- 开启组件扫描 -->
    <!-- 对包中的所有类进行扫描，以完成Bean创建和自动依赖注入的功能 -->
    <context:component-scan base-package="${conf.basePackage}.*.service,${conf.basePackage}.*.manager,${conf.basePackage}.*.biz"/>

    <!-- 启用AspectJ对Annotation的支持 -->
    <!--<aop:aspectj-autoproxy/>-->

    <!-- 启用AspectJ对Annotation的支持 -->
    <aop:aspectj-autoproxy proxy-target-class="true"/>

    <!-- Transaction Support -->
    <tx:advice id="useTxAdvice" transaction-manager="txManager">
        <tx:attributes>
            <tx:method name="*remove*" rollback-for="java.lang.Exception"
                       no-rollback-for="${conf.basePackage}.exception.MessageException"/>
            <tx:method name="*save*" rollback-for="java.lang.Exception"
                       no-rollback-for="${conf.basePackage}.exception.MessageException"/>
            <tx:method name="*modify*" rollback-for="java.lang.Exception"
                       no-rollback-for="${conf.basePackage}.exception.MessageException"/>
            <tx:method name="*create*" rollback-for="java.lang.Exception"
                       no-rollback-for="${conf.basePackage}.exception.MessageException"/>
            <tx:method name="*delete*" rollback-for="java.lang.Exception"
                       no-rollback-for="${conf.basePackage}.exception.MessageException"/>
            <tx:method name="*update*" rollback-for="java.lang.Exception"
                       no-rollback-for="${conf.basePackage}.exception.MessageException"/>
            <tx:method name="*add*" rollback-for="java.lang.Exception"
                       no-rollback-for="${conf.basePackage}.exception.MessageException"/>

            <tx:method name="*find*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="*get*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="*page*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="*count*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="*select*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="*query*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="*list*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="*show*" propagation="SUPPORTS" read-only="true"/>
        </tx:attributes>
    </tx:advice>

    <!-- 把事务控制在Manager层 -->
    <aop:config>
        <aop:advisor pointcut="execution(public * ${conf.basePackage}..manager.*.*(..))" advice-ref="useTxAdvice"/>
    </aop:config>

    <!-- 切面配置: Manager层多数据源切换支持,在事务之前执行 -->
    <bean class="${conf.basePackage}.base.datasource.aspect.DataSourceRoutingAspectProcessor"/>
    <!-- 切面配置：Service层方法执行日志 -->
    <bean class="${conf.basePackage}.base.service.aspect.ServiceCostLogAspect"/>
    <!-- 切面配置：Manager层方法执行日志 -->
    <bean class="${conf.basePackage}.base.manager.aspect.ManagerCostLogAspect"/>
    <!-- 切面配置：Business层方法执行日志 -->
    <bean class="${conf.basePackage}.base.biz.aspect.BizCostLogAspect"/>

    <!-- 任务配置 -->
    <import resource="classpath*:task/spring-task.xml"/>
</beans>
