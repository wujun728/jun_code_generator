package cn.yt4j.layuirbac.annotation;

import java.lang.annotation.*;

/**
 * @author gyv12345@163.com
 * @date 2020/3/3 20:42
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";
}
