package org.coderfun.member.core.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 校验数据权限
 * 
 * @author klguang[klguang@foxmail.com]
 * @date 2018年12月14日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MemberValid {
	Class<?> entityClass();
	String entityAuthField()default "memberId";
	/**
	 * 支持嵌套字段
	 * @return
	 */
	String entityIdValueArg() default "id";
}
