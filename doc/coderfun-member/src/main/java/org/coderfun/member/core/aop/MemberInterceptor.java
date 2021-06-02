package org.coderfun.member.core.aop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.beanutils.PropertyUtils;
import org.coderfun.common.exception.AppException;
import org.coderfun.common.exception.ErrorCodeEnum;
import org.coderfun.member.core.MemberHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;

import klg.common.utils.BeanTools;
import klg.common.utils.ReflectionTools;


/**
 * 
 * 校验用户的数据权限，或者注入memberId。
 * 
 * @author klguang[klguang@foxmail.com]
 * @date 2018年12月14日
 */
public class MemberInterceptor implements MethodInterceptor {

	private MemberHolder memberHolder;

	@Autowired
	EntityManager entityManager;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		if (memberHolder.getMemberId() == null) {
			throw new AppException(ErrorCodeEnum.UNAUTHENTICATED);
		}

		Object[] args = invocation.getArguments();
		Method method = invocation.getMethod();

		if (!validDataAuth(method, args)) {
			throw new AppException(ErrorCodeEnum.UNAUTHORIZED);
		}

		injectDataAuth(method, args);

		return null;
	}

	private void injectDataAuth(Method method, Object[] args) {
		List<MethodParameter> parameters = ReflectionTools.getParams(method, MemberInject.class, false);
		for (MethodParameter parameter : parameters) {
			MemberInject memberInject = parameter.getParameterAnnotation(MemberInject.class);
			if (memberInject.value().equals("") && args[parameter.getParameterIndex()].getClass().isInstance(Long.class)) {
				args[parameter.getParameterIndex()] = memberHolder.getMemberId();
			} else {
				BeanTools.setField(args[parameter.getParameterIndex()], memberInject.value(), memberHolder.getMemberId());
			}
		}
	}

	private boolean validDataAuth(Method method, Object[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		MemberValid memberValid = method.getAnnotation(MemberValid.class);
		if (null != memberValid) {
			String entityIdValueArg = memberValid.entityIdValueArg();
			Object entityId = ReflectionTools.getArgValue(method, entityIdValueArg, args);
			return validDataAuth(memberValid.entityClass(), entityId, memberValid.entityAuthField());
		} else {
			return true;
		}
	}

	private boolean validDataAuth(Class<?> entityClass, Object entityId, String entityAuthField) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Object entity = entityManager.find(entityClass, entityId);
		Long memberId = (Long) PropertyUtils.getProperty(entity, entityAuthField);
		return memberId.equals(memberHolder.getMemberId());
	}

	public MemberHolder getMemberHolder() {
		return memberHolder;
	}

	public void setMemberHolder(MemberHolder memberHolder) {
		this.memberHolder = memberHolder;
	}

}
