package cn.yt4j.layuirbac.interceptor;


import cn.yt4j.layuirbac.annotation.CreateTime;
import cn.yt4j.layuirbac.annotation.UpdateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;

/**
 * mybatis拦截器
 *
 * @author gyv12345@163.com
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
@Component
public class DbInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Object parameter = invocation.getArgs()[1];
        if (null != parameter) {
            Field[] declaredFields = parameter.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                if (field.getAnnotation(CreateTime.class) != null) {
                    if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                        field.setAccessible(true);
                        field.set(parameter, new Date());
                    }
                }
                if (field.getAnnotation(UpdateTime.class) != null) {
                    if (SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType)) {
                        field.setAccessible(true);
                        field.set(parameter, new Date());
                    }
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
