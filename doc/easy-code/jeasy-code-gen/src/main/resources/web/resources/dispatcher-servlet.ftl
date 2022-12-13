<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context-3.0.xsd
       http://www.springframework.org/schema/mvc
       http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- 自定义的参数解析器放在第一位置 -->
    <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter">
        <!-- 自定义参数解析器 -->
        <property name="customArgumentResolvers">
            <list>
                <bean class="com.jeasy.base.web.resolver.ArgumentFromJsonResolver"/>
            </list>
        </property>
    </bean>

    <!-- 开启组件扫描 -->
    <!-- 对包中的所有类进行扫描，以完成Bean创建和自动依赖注入的功能 -->
    <context:component-scan base-package="com.jeasy.*.controller"/>

    <!-- 开启注解 -->
    <mvc:annotation-driven/>

    <!-- 启用AspectJ对Annotation的支持 -->
    <!--<aop:aspectj-autoproxy proxy-target-class="true"/>-->

    <!-- 静态资源路径 -->
    <mvc:resources mapping="/js/**" location="/js/" cache-period="31556926"/>
    <mvc:resources mapping="/html/**" location="/html/" cache-period="31556926"/>
    <mvc:resources mapping="/static/**" location="/static/" cache-period="31556926"/>
    <mvc:resources mapping="/front/**" location="/front/" cache-period="31556926"/>
    <mvc:resources mapping="/upload/**" location="/upload/" cache-period="31556926"/>
    <mvc:resources mapping="/favicon.ico" location="/static/style/images/favicon.ico" cache-period="31556926"/>

    <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
        <property name="prefix" value="/WEB-INF/views/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

    <!-- Bean解析器,级别高于默认解析器,寻找bean对象进行二次处理 -->
    <bean id="beanNameViewResolver" class="org.springframework.web.servlet.view.BeanNameViewResolver" p:order="0"/>

    <!-- Excel 处理 根据用户输入进行对象处理 -->
    <bean id="jeecgExcelView" class="org.jeecgframework.poi.excel.view.JeecgSingleExcelView"/>
    <bean id="jeecgTemplateExcelView" class="org.jeecgframework.poi.excel.view.JeecgTemplateExcelView"/>
    <bean id="jeecgTemplateWordView" class="org.jeecgframework.poi.excel.view.JeecgTemplateWordView"/>
    <bean id="jeecgMapExcelView" class="org.jeecgframework.poi.excel.view.JeecgMapExcelView"/>

    <!-- ueditor -->
    <bean class="com.jeasy.common.ueditor.UeditorManager"/>
    <bean class="com.jeasy.common.ueditor.UeditorService"/>

    <!-- 文件上传 -->
    <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
        <!-- 默认编码 -->
        <property name="defaultEncoding" value="UTF-8"/>
        <!-- 设置上传文件总大小限制 10G -->
        <property name="maxUploadSize" value="10485760000"/>
        <!-- 内存中的最大值 -->
        <property name="maxInMemorySize" value="40960"/>
    </bean>

    <!-- 配置Controller拦截器 -->
    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/**"/>
            <mvc:exclude-mapping path="/easyui/**"/>
            <mvc:exclude-mapping path="/js/**"/>
            <mvc:exclude-mapping path="/html/**"/>
            <mvc:exclude-mapping path="/diagram-viewer/**"/>
            <mvc:exclude-mapping path="/editor-app/**"/>
            <mvc:exclude-mapping path="/static/**"/>
            <mvc:exclude-mapping path="/front/**"/>
            <mvc:exclude-mapping path="/upload/**"/>
            <mvc:exclude-mapping path="/rpc/**"/>
            <mvc:exclude-mapping path="/error/**"/>
            <bean class="com.jeasy.base.web.interceptor.ControllerCostLogInterceptor"/>
        </mvc:interceptor>
        <mvc:interceptor>
            <mvc:mapping path="/**"/>
            <mvc:exclude-mapping path="/easyui/**"/>
            <mvc:exclude-mapping path="/js/**"/>
            <mvc:exclude-mapping path="/html/**"/>
            <mvc:exclude-mapping path="/diagram-viewer/**"/>
            <mvc:exclude-mapping path="/editor-app/**"/>
            <mvc:exclude-mapping path="/static/**"/>
            <mvc:exclude-mapping path="/front/**"/>
            <mvc:exclude-mapping path="/upload/**"/>
            <mvc:exclude-mapping path="/rpc/**"/>
            <mvc:exclude-mapping path="/error/**"/>
            <bean class="com.jeasy.doc.interceptor.TransferDocInterceptor"/>
        </mvc:interceptor>
        <mvc:interceptor>
            <mvc:mapping path="/**"/>
            <mvc:exclude-mapping path="/"/>
            <mvc:exclude-mapping path="/easyui/**"/>
            <mvc:exclude-mapping path="/js/**"/>
            <mvc:exclude-mapping path="/html/**"/>
            <mvc:exclude-mapping path="/diagram-viewer/**"/>
            <mvc:exclude-mapping path="/editor-app/**"/>
            <mvc:exclude-mapping path="/static/**"/>
            <mvc:exclude-mapping path="/front/**"/>
            <mvc:exclude-mapping path="/upload/**"/>
            <mvc:exclude-mapping path="/rpc/**"/>
            <mvc:exclude-mapping path="/login"/>
            <mvc:exclude-mapping path="/captcha.jpg"/>
            <mvc:exclude-mapping path="/error/**"/>
            <bean class="com.jeasy.security.TransferSecurityInterceptor">
                <property name="checkReferer" value="false"/>
            </bean>
        </mvc:interceptor>
        <!--Spring csrf 拦截器-->
        <mvc:interceptor>
            <mvc:mapping path="/login"/>
            <mvc:mapping path="/captcha.jpg"/>
            <bean class="com.jeasy.base.web.csrf.CsrfInterceptor"/>
        </mvc:interceptor>
    </mvc:interceptors>

    <!-- csrf CookieCsrfTokenRepository -->
    <bean class="com.jeasy.base.web.csrf.CookieCsrfTokenRepository">
        <property name="cacheManager" ref="shiroSpringCacheManager"/>
        <property name="csrfTokenCacheName" value="oneMinute"/>
    </bean>

    <!-- 切面配置：Controller方法参数校验 -->
    <bean class="com.jeasy.base.web.aspect.ParameterValidateAspect"/>

    <!-- 启用shrio 控制器授权注解拦截方式 -->
    <bean class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
        <property name="securityManager" ref="securityManager"/>
    </bean>

    <!-- 保证实现了Shiro内部lifecycle函数的bean执行 -->
    <bean id="lifecycleBeanPostProcessor" class="org.apache.shiro.spring.LifecycleBeanPostProcessor"/>

    <!--
        AOP式方法级权限检查 注掉aop:aspectj-autoproxy配置，改为手动配置AnnotationAwareAspectJAutoProxyCreator
        解决当aop:aspectj-autoproxy与DefaultAdvisorAutoProxyCreator同时配置，导致AuthorizationAttributeSourceAdvisor被代理2次问题。
     -->
    <bean class="org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator" depends-on="lifecycleBeanPostProcessor">
        <property name="proxyTargetClass" value="true"/>
    </bean>
</beans>
