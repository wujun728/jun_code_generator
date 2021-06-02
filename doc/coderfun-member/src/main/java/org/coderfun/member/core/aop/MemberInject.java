package org.coderfun.member.core.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 注入memberId
 * @author klguang[klguang@foxmail.com]
 * @date 2018年12月14日
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface MemberInject {
	/**
	 * 支持嵌套字段,member.id, "" 表示 参数本身
	 * @return
	 */
	String value() default "memberId";
}
